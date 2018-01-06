package com.luv.face2face.config;


import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;
import java.util.Map;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:15 2018/1/6.
 * @since luv-face2face
 */

public class NettyIMServerConfiguration
{
    private Map<ChannelOption<?>, Object> channelOptions;

    // reactor多线程模型中的acceptor
    private NioEventLoopGroup bossGroup;

    // reactor多线程模型中的threadPool
    private NioEventLoopGroup workerGroup;

    // bossGroup的线程数
    private int bossThreadCount;

    // workerGroup的线程数
    private int workerThreadCount;

    private InetSocketAddress socketAddress;

    private int portNumber = 18090;

    public Map<ChannelOption<?>, Object> getChannelOptions()
    {
        return channelOptions;
    }

    public void setChannelOptions(Map<ChannelOption<?>, Object> channelOptions)
    {
        this.channelOptions = channelOptions;
    }

    public int getBossThreadCount()
    {
        return bossThreadCount;
    }

    public void setBossThreadCount(int bossThreadCount)
    {
        this.bossThreadCount = bossThreadCount;
    }

    public int getWorkerThreadCount()
    {
        return workerThreadCount;
    }

    public void setWorkerThreadCount(int workerThreadCount)
    {
        this.workerThreadCount = workerThreadCount;
    }

    public synchronized InetSocketAddress getSocketAddress()
    {
        if (null == socketAddress)
        {
            socketAddress = new InetSocketAddress(portNumber);
        }
        return socketAddress;
    }

    public void setSocketAddress(InetSocketAddress socketAddress)
    {
        this.socketAddress = socketAddress;
    }

    public int getPortNumber()
    {
        return portNumber;
    }

    public void setPortNumber(int portNumber)
    {
        this.portNumber = portNumber;
    }
}
