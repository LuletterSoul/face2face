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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.luv.face2face.protobuf.generate.cli2srv.login.Auth.*;
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
//    private UserJpaDao userJpaDao;

    /** 在线用户列表 */
    private Set<Long> onlineUsers = new ConcurrentHashSet<>();

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
        this.onlineUsers.add(userId);
    }

    @Override
    public boolean isOnlineUser(long userId)
    {
        return this.onlineUsers.contains(userId);
    }

    @Override
    public void registerNewAccount(User user, Channel channel) {
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
}
