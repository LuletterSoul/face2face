package com.luv.face2face.logic.net;


import java.net.InetSocketAddress;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;


/**
 * channel的工具类
 * 生成一个具有唯一用户会话
 * 解析channel的ip地址
 * 根据channel获取会话
 *
 */
public final class ChannelUtils
{

    public static AttributeKey<UserConnectSession> SESSION_KEY = AttributeKey.valueOf("session");

    /**
     * 添加新的会话
     * @param channel
     * @param session
     * @return
     */
    public static boolean addChannelSession(Channel channel, UserConnectSession session)
    {
        Attribute<UserConnectSession> sessionAttr = channel.attr(SESSION_KEY);
        return sessionAttr.compareAndSet(null, session);
    }

    /**
     * 根据channel 获取会话
     *
     * @param channel
     * @return
     */
    public static UserConnectSession getSessionBy(Channel channel)
    {
        Attribute<UserConnectSession> sessionAttr = channel.attr(SESSION_KEY);
        return sessionAttr.get();
    }

    /**
     * 解析channel的ip地址
     * @param channel
     * @return
     */
    public static String getIp(Channel channel)
    {
        return ((InetSocketAddress)channel.remoteAddress()).getAddress().toString().substring(1);
    }

}
