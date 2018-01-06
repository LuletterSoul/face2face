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

    /**
     * 根据用户会话，推送服务消息
     * @param session 用户会话
     * @param message 推送的消息
     */
    void pushMessageToUser(UserConnectSession session, Message message);

    /**
     * 根据用户id，推送服务消息
     * @param userId
     * @param message
     */
    void pushMessageToUser(Long userId, Message message);

    /**
     * 根据用户信道,推送服务消息
     * @param channel
     * @param message
     */
    void pushMessageToUser(Channel channel, Message message);

    /**
     * 根据用户id获取在线用户信息
     * @param userId
     * @return
     */
     User getOnlineUserById(Long userId);

    /**
     * 根据用户id获取用户会话
     * @param userId
     * @return
     */
    UserConnectSession getOnlineUserSessionById(Long userId);

    /**
     * 推送服务消息给全部用户
     */
    void pushMessageToOnlineUsers(Message message);

    /**
     * 注册用户会话
     * @param user 用户信息
     * @param context 用户信道
     * @return
     */
    boolean registerSession(User user, Channel context);

    /**
     * 注销用户会话
     * @param user
     * @param reason
     * @return
     */
    boolean unregisterSession(User user, SessionCloseReason reason);

    /**
     * 判断在线状态
     * @param userId
     * @return
     */
    boolean isOnline(Long userId);

}
