package com.luv.face2face.config;


import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;
import java.util.Map;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:50 2018/1/8.
 * @since face2face
 */

public interface IMServerConfiguration
{
    Map<ChannelOption<?>, Object> getChannelOptions();

    void setChannelOptions(Map<ChannelOption<?>, Object> channelOptions);

    int getBossThreadCount();

    void setBossThreadCount(int bossThreadCount);

    int getWorkerThreadCount();

    void setWorkerThreadCount(int workerThreadCount);

    InetSocketAddress getSocketAddress();

    void setSocketAddress(InetSocketAddress socketAddress);

    int getPortNumber();

    void setPortNumber(int portNumber);

    void setBossGroup(NioEventLoopGroup bossGroup);

    void setWorkerGroup(NioEventLoopGroup workerGroup);

    void setIp(String ip);

    NioEventLoopGroup getBossGroup();

    NioEventLoopGroup getWorkerGroup();

    String getIp();
}
