package com.luv.face2face.service.session;


import java.net.InetSocketAddress;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;


/**
 * channel的工具类
 * 
 * @author kingston
 */
public final class ChannelUtils
{

    public static AttributeKey<UserConnectSession> SESSION_KEY = AttributeKey.valueOf("session");

    /**
     * 添加新的会话
     * 
     * @param channel
     * @param session
     * @return
     */
    public static boolean addChannelSession(Channel channel, UserConnectSession session)
    {
        Attribute<UserConnectSession> sessionAttr = channel.attr(SESSION_KEY);
        return sessionAttr.compareAndSet(null, session);
    }

    public static UserConnectSession getSessionBy(Channel channel)
    {
        Attribute<UserConnectSession> sessionAttr = channel.attr(SESSION_KEY);
        return sessionAttr.get();
    }

    public static String getIp(Channel channel)
    {
        return ((InetSocketAddress)channel.remoteAddress()).getAddress().toString().substring(1);
    }

}
