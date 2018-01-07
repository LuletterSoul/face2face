package com.luv.face2face.service.impl;


import com.google.protobuf.Message;
import com.luv.face2face.domain.User;
import com.luv.face2face.protobuf.code.ResponseCode;
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

import static com.luv.face2face.protobuf.generate.cli2srv.login.Auth.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 21:27 2018/1/5.
 * @since luv-face2face
 */
@Service
@Slf4j
public class OnlineServiceImpl implements OnlineService
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

    public boolean registerSession(User user, Channel context)
    {
        UserConnectSession session = ChannelUtils.getSessionBy(context);
        session.setUser(user);
        onlineSessions.put(user.getUserId(), session);
//        onlineUserIds.put(session, user.getUserId());
        log.info("User:[{}]:[{}] session registered...", user.getNickname(), user.getUserId());
//        ResponseMsg.Builder builder = ResponseMsg.newBuilder();
//        builder.setDescription("会话注册成功");
//        builder.setCode(ResponseCode.SESSION_CREATED);
//        session.sendPacket(builder.build());
        return true;
    }

    @Override
    public boolean unregisterSession(Long userId,Channel channel, SessionCloseReason reason)
    {
        UserConnectSession session = ChannelUtils.getSessionBy(channel);
        onlineSessions.remove(userId);
        // 关闭当前会话;
        session.close(reason);
        return true;
    }

    @Override
    public boolean isOnline(Long userId)
    {
        return onlineSessions.get(userId) == null;
    }
}
