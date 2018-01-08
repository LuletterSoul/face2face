package com.luv.face2face.config;


import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.net.*;
import java.util.Enumeration;
import java.util.Map;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:15 2018/1/6.
 * @since luv-face2face
 */

@Setter
@Getter
public class NettyIMServerConfiguration implements IMServerConfiguration
{
    protected Map<ChannelOption<?>, Object> channelOptions;

    // reactor多线程模型中的acceptor
    protected NioEventLoopGroup bossGroup;

    // reactor多线程模型中的threadPool
    protected NioEventLoopGroup workerGroup;

    // bossGroup的线程数
    protected int bossThreadCount;

    // workerGroup的线程数
    protected int workerThreadCount;

    protected InetSocketAddress socketAddress;

    protected String ip;

    protected int portNumber;

    @Override
    public Map<ChannelOption<?>, Object> getChannelOptions()
    {
        return channelOptions;
    }

    @Override
    public void setChannelOptions(Map<ChannelOption<?>, Object> channelOptions)
    {
        this.channelOptions = channelOptions;
    }

    protected void configureIdAddress()
        throws SocketException
    {

        Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        while (allNetInterfaces.hasMoreElements())
        {
            NetworkInterface netInterface = (NetworkInterface)allNetInterfaces.nextElement();
            System.out.println(netInterface.getName());
            Enumeration addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements())
            {
                ip = (InetAddress)addresses.nextElement();
                if (ip != null && ip instanceof Inet4Address)
                {
                    System.out.println("本机的IP = " + ip.getHostAddress());
                }
            }
        }
        if (ip != null)
        {
            this.socketAddress = new InetSocketAddress(ip.getHostAddress(), portNumber);
        }
    }

    @Override
    public int getBossThreadCount()
    {
        return bossThreadCount;
    }

    @Override
    public void setBossThreadCount(int bossThreadCount)
    {
        this.bossThreadCount = bossThreadCount;
    }

    @Override
    public int getWorkerThreadCount()
    {
        return workerThreadCount;
    }

    @Override
    public void setWorkerThreadCount(int workerThreadCount)
    {
        this.workerThreadCount = workerThreadCount;
    }

    @Override
    public synchronized InetSocketAddress getSocketAddress()
    {
        if (null == socketAddress)
        {
            socketAddress = new InetSocketAddress(portNumber);
        }
        return socketAddress;
    }

    @Override
    public void setSocketAddress(InetSocketAddress socketAddress)
    {
        this.socketAddress = socketAddress;
    }

    @Override
    public int getPortNumber()
    {
        return portNumber;
    }

    @Override
    public void setPortNumber(int portNumber)
    {
        this.portNumber = portNumber;
    }
}
