package com.luv.face2face.service.impl;


import com.luv.face2face.domain.User;
import com.luv.face2face.protobuf.Protocol;
import com.luv.face2face.protobuf.code.ResponseCode;
import com.luv.face2face.protobuf.generate.ser2cli.login.Server;
import com.luv.face2face.repository.UserJpaDao;
import com.luv.face2face.service.BaseService;
import com.luv.face2face.service.UserService;
import com.luv.face2face.service.session.ChannelUtils;
import com.luv.face2face.service.session.UserConnectSession;
import com.luv.face2face.service.util.ConcurrentHashSet;
import io.netty.channel.Channel;
import io.netty.handler.stream.ChunkedFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.luv.face2face.protobuf.generate.cli2srv.login.Auth.*;
import static com.luv.face2face.protobuf.generate.ser2cli.file.Server.*;
import static com.luv.face2face.protobuf.generate.ser2cli.login.Server.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:44 2018/1/5.
 * @since luv-face2face
 */

@Service
@Slf4j
public class UserServiceImpl extends AbstractService implements UserService
{
    // private UserJpaDao userJpaDao;

    /** 在线用户列表 */
    private Set<Long> onlineUsers = new ConcurrentHashSet<>();

    private Map<Long, ReqFileUploadMsg> uploadMsgMap = new ConcurrentHashMap<>();

    @Autowired
    public void setUserJpaDao(UserJpaDao userJpaDao)
    {
        this.userJpaDao = userJpaDao;
    }

    @Override
    public void addUser2Online(long userId)
    {
        this.onlineUsers.add(userId);
    }

    @Override
    public void removeFromOnline(long userId)
    {
        this.onlineUsers.remove(userId);
    }

    @Override
    public boolean isOnlineUser(long userId)
    {
        return this.onlineUsers.contains(userId);
    }

    @Override
    public void registerNewAccount(User user, Channel channel)
    {
        UserConnectSession session = ChannelUtils.getSessionBy(channel);
        userJpaDao.save(user);
        ResServerRegisterSucc.Builder builder = ResServerRegisterSucc.newBuilder();
        builder.setDescription("注册成功！您的用户Id为：" + user.getUserId() + ".");
        builder.setUserId(user.getUserId());
        session.sendPacket(builder.build());
    }

    @Override
    public User createNewUser(User user)
    {
        return null;
    }

    @Override
    public void refreshUserProfile(User user)
    {
        UserConnectSession session = onlineService.getOnlineUserSessionById(user.getUserId());
        ResServerRefreshProfile.Builder builder = ResServerRefreshProfile.newBuilder();
        builder.setNickname(user.getNickname());
        builder.setSex(user.getSex());
        builder.setUserId(user.getUserId());
        builder.setSignature(user.getSignature());
        session.sendPacket(builder.build());
    }

    @Override
    public void cacheUserUploadMsg(long userId, ReqFileUploadMsg msg)
    {
        uploadMsgMap.put(userId, msg);
    }

    @Override
    public ReqFileUploadMsg getUserUploadMsg(long userId)
    {
        ReqFileUploadMsg msg = uploadMsgMap.get(userId);
        if (msg == null)
        {
            log.info("Empty file info.------------------->");
            try
            {
                throw new IllegalAccessException("Could not find file upload cache.");
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            return null;
        }
        return msg;
    }

    @Override
    public void sendUploadFilePromise(Long userId, String path)
    {
        log.debug("send upload file promise.");
        if (isOnlineUser(userId))
        {
            UserConnectSession session = onlineService.getOnlineUserSessionById(userId);
            ResFileUploadPromise.Builder builder = ResFileUploadPromise.newBuilder();
            builder.setDescription("可以上传");
            builder.setPromise("可以上传");
            builder.setYourFilePath(path);
            session.sendPacket(builder.build());
        }
        else
        {
            log.debug("User disconnect.");
        }
    }

    @Override
    public void notifyFileReady(ResFileUploadComplete message)
    {
        ResFileUploadComplete msg = message;
        ReqFileUploadMsg uploadMsg = msg.getFileUploadMsg();
        Long toUserId = uploadMsg.getToUserId();
        Long fromUserId = uploadMsg.getFormUserId();
        if (this.isOnlineUser(toUserId))
        {
            onlineService.getOnlineUserSessionById(toUserId).sendPacket(msg);
        }
        if (this.isOnlineUser(fromUserId))
        {
            onlineService.getOnlineUserSessionById(fromUserId).sendPacket(msg);
        }
    }

    @Override
    public void sendDownloadPromise(ResFileDownloadMsg resFileDownloadMsg, Long userId)
    {
        UserConnectSession session = onlineService.getOnlineUserSessionById(userId);
        session.sendPacket(resFileDownloadMsg);
    }

    @Override
    public void sendFile(File file, Long toUserId)
    {
        UserConnectSession connectSession = onlineService.getOnlineUserSessionById(toUserId);
        Channel channel = connectSession.getChannel();
        try
        {
            channel.write(new ChunkedFile(file));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyFileReceived(Long userId, ResFileDownloadComplete complete)
    {
        UserConnectSession formUserSession = onlineService.getOnlineUserSessionById(userId);
        UserConnectSession toUserSession = onlineService.getOnlineUserSessionById(
            complete.getFileDownloadMsg().getFormUserId());
        formUserSession.sendPacket(complete);
        toUserSession.sendPacket(complete);
    }

}
