package com.luv.face2face.config;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:49 2018/1/5.
 * @since luv-face2face
 */

@Configuration
@EnableAsync
public class ServerBeanConfiguration
{
    @Bean
    public ThreadPoolTaskExecutor getExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.initialize();
        return executor;
    }

    @Bean
    public EventLoopGroup bossGroup()
    {
        return new NioEventLoopGroup(1);
    }

    @Bean
    public EventLoopGroup workerGroup()
    {
        return new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    @Autowired
    public ServerBootstrap logicServerBootstrap(ChannelInitializer<SocketChannel> logicChannelInitializer)
    {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup()).group(workerGroup()).childHandler(
            logicChannelInitializer);
        bindConnectionOptions(serverBootstrap);
        return serverBootstrap;
    }

    private void bindConnectionOptions(ServerBootstrap bootstrap)
    {
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.childOption(ChannelOption.SO_LINGER, 0);
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_REUSEADDR, true); // 调试用
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); // 心跳机制暂时使用TCP选项，之后再自己实现
    }
}
