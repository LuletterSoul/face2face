package com.luv.face2face.logic.net;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message;
import com.luv.face2face.auth.domain.User;

import io.netty.channel.Channel;


/**
 * 用户连接的回话
 */
public class UserConnectSession
{

    private static final Logger logger = LoggerFactory.getLogger(UserConnectSession.class);

    /** 网络连接channel */
    private Channel channel;

    private User user;

    /** ip地址 */
    private String ipAddress;

    private boolean reconnected;

    /** 拓展用，保存一些个人数据 */
    private Map<String, Object> attrs = new HashMap<>();

    public UserConnectSession()
    {

    }

    public UserConnectSession(Channel channel)
    {
        this.channel = channel;
        this.ipAddress = ChannelUtils.getIp(channel);
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    /**
     * 向客户端发送消息
     * 
     * @param packet
     */
    public void sendPacket(Message packet)
    {
        if (packet == null)
        {
            return;
        }
        if (channel != null)
        {
            channel.writeAndFlush(packet);
        }
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public boolean isReconnected()
    {
        return reconnected;
    }

    public void setReconnected(boolean reconnected)
    {
        this.reconnected = reconnected;
    }

    public User getUser()
    {
        return user;
    }

    public boolean isClose()
    {
        if (channel == null)
        {
            return true;
        }
        return !channel.isActive() || !channel.isOpen();
    }

    /**
     * 关闭session
     * 
     * @param reason
     *            {@link SessionCloseReason}
     */
    public void close(SessionCloseReason reason)
    {
        try
        {
            if (this.channel == null)
            {
                return;
            }
            if (channel.isOpen())
            {
                channel.close();
                logger.info("close session[{}], reason is {}", getUser().getUserId(), reason);
            }
            else
            {
                logger.info("session[{}] already close, reason is {}", getUser().getUserId(),
                    reason);
            }
        }
        catch (Exception e)
        {}
    }

}
