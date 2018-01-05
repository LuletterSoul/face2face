package com.luv.face2face.service.impl;


import com.luv.face2face.auth.domain.User;
import com.luv.face2face.service.OnlineService;
import com.luv.face2face.service.session.SessionCloseReason;
import com.luv.face2face.service.session.UserConnectSession;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.plugin2.message.Message;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 21:27 2018/1/5.
 * @since luv-face2face
 */

public class OnlineServiceImpl implements OnlineService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineServiceImpl.class);

    private ConcurrentMap<UserConnectSession, Long> onlineUserIds = new ConcurrentHashMap<>();

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
        return null;
    }

    @Override
    public void pushMessageToOnlineUsers()
    {

    }

    @Override

    public boolean registerSession(User user, Channel context)
    {
        return false;
    }

    @Override
    public boolean unregisterSession(User user, SessionCloseReason reason)
    {
        return false;
    }

    @Override
    public boolean isOnline(Long userId)
    {
        return onlineSessions.get(userId) == null;
    }
}
