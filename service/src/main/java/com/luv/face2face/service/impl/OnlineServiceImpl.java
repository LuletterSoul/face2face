package com.luv.face2face.service.impl;


import com.google.protobuf.Message;
import com.luv.face2face.domain.User;
import com.luv.face2face.service.OnlineService;
import com.luv.face2face.service.session.ChannelUtils;
import com.luv.face2face.service.session.SessionCloseReason;
import com.luv.face2face.service.session.UserConnectSession;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.luv.face2face.protobuf.generate.ser2cli.login.Server.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 21:27 2018/1/5.
 * @since luv-face2face
 */
@Service
@Slf4j
public class OnlineServiceImpl extends AbstractService implements OnlineService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineServiceImpl.class);

//    private ConcurrentMap<UserConnectSession, Long> onlineUserIds = new ConcurrentHashMap<>();

    private ConcurrentMap<Long, UserConnectSession> onlineSessions = new ConcurrentHashMap<>();

    @Override
    public void pushMessageToUser(UserConnectSession session, Message message)
    {

    }

    @Override
    public void pushMessageToUser(Long userId, Message message)
    {

    }

    @Override
    public void pushMessageToUser(Channel channel, Message message)
    {

    }

    @Override
    public User getOnlineUserById(Long userId)
    {
        return null;
    }

    @Override
    public UserConnectSession getOnlineUserSessionById(Long userId)
    {
        return this.onlineSessions.get(userId);
    }

    @Override
    public void pushMessageToOnlineUsers(Message message)
    {

    }

    @Override

    public boolean registerSession(User user, Channel channel)
    {
        UserConnectSession session = ChannelUtils.getSessionBy(channel);
        //将user 加入session
        session.setUser(user);
        session.setIpAddress(ChannelUtils.getIp(channel));
        onlineSessions.put(user.getUserId(), session);
        ResServerLoginSucc.Builder builder = ResServerLoginSucc.newBuilder();
        builder.setDescription("登陆成功");
        //发送登录成功信息
        session.sendPacket(builder.build());
        log.info("User:[{}]:[{}] session registered...", user.getNickname(), user.getUserId());
        return true;
    }

    @Override
    public void unregisterSession(Long userId, Channel channel, SessionCloseReason reason)
    {
        UserConnectSession session = ChannelUtils.getSessionBy(channel);
        if (userId != null) {
            onlineSessions.remove(userId);
        }
        // 关闭当前会话;
        session.close(reason);
    }

    @Override
    public boolean isOnline(Long userId)
    {
        return onlineSessions.get(userId) == null;
    }
}
