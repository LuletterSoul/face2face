package com.luv.face2face.server;


import com.luv.face2face.config.NettyIMServerConfiguration;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.net.InetSocketAddress;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:13 2018/1/6.
 * @since luv-face2face
 */

@Slf4j
@Getter
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class AbstractNettyServerImpl implements IMBasicServer
{
    // 用于管理所有的channel
    public static final ChannelGroup ALL_CHANNELS = new DefaultChannelGroup("NADRON-CHANNELS",
        GlobalEventExecutor.INSTANCE);

    protected NettyIMServerConfiguration configuration;

    protected ChannelInitializer<? extends Channel> channelInitializer;

    @Qualifier("bossGroup")
    protected EventLoopGroup bossGroup;

    @Qualifier("workerGroup")
    protected EventLoopGroup workerGroup;

    @Override
    public ChannelInitializer<? extends Channel> getChannelInitializer()
    {
        return this.channelInitializer;
    }

    @Override
    public void setChannelInitializer(ChannelInitializer<? extends Channel> initializer)
    {
        this.channelInitializer = initializer;
    }

    @Override
    public NettyIMServerConfiguration getNettyConfig()
    {
        return this.configuration;
    }

    @Override
    public TransmissionProtocol getTransmissionProtocol()
    {
        return null;
    }

    @Override
    public void start()
        throws Exception
    {

    }

    @Override
    public void start(int port)
        throws Exception
    {
        configuration.setPortNumber(port);
        configuration.setSocketAddress(new InetSocketAddress(port));
        start();
    }

    @Override
    public void start(InetSocketAddress socketAddress)
        throws Exception
    {
        configuration.setSocketAddress(socketAddress);
        start();
    }

    @Override
    public void stop()
        throws Exception
    {
        log.debug("In stop method of class: {}", this.getClass().getName());
        ChannelGroupFuture future = ALL_CHANNELS.close();
        try
        {
            future.await();
        }
        catch (InterruptedException e)
        {
            log.error("Exception occurred while waiting for channels to close: {}", e);
        }
        finally
        {
            getBossGroup().shutdownGracefully();
            getWorkerGroup().shutdownGracefully();
        }
    }

    @Override
    public InetSocketAddress getSocketAddress()
    {
        return configuration.getSocketAddress();
    }
}
