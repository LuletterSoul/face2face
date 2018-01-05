package com.luv.face2face.service;


import com.luv.face2face.auth.domain.User;
import com.luv.face2face.service.session.SessionCloseReason;
import com.luv.face2face.service.session.UserConnectSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import sun.plugin2.message.Message;

import java.util.Map;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 21:16 2018/1/5.
 * @since luv-face2face
 */

public interface OnlineService
{

    void pushMessageToUser(UserConnectSession session, Message message);

    void pushMessageToUser(Long userId, Message message);

    void pushMessageToUser(Channel channel, Message message);

     User getOnlineUserById(Long userId);

    UserConnectSession getOnlineUserSessionById(Long userId);

    void pushMessageToOnlineUsers();

    boolean registerSession(User user, Channel context);

    boolean unregisterSession(User user, SessionCloseReason reason);

    boolean isOnline(Long userId);

}
