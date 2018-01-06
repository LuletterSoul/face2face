package com.luv.face2face.server;

import com.luv.face2face.config.LogicIMServerConfiguration;
import com.luv.face2face.config.NettyIMServerConfiguration;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  17:11 2018/1/6.
 * @since luv-face2face
 */

public interface IMBasicServer extends IMServer {
    /**
     * ServerBootstrap创建成功后会有一个ChannelInitializer(即pipeline factory), 本方法主要用于获取这个
     * ChannelInitializer
     *
     * @return
     */
    public ChannelInitializer<? extends Channel> getChannelInitializer();

    /**
     * 设置自己的ChannelInitializer
     *
     * @param initializer
     *            pipeline的工厂类，主要为每个新的链接创建一个pipeline
     */
    public void setChannelInitializer(ChannelInitializer<? extends Channel> initializer);

    /**
     * 获取netty server的configuration
     *
     * @return .
     */
    public NettyIMServerConfiguration getNettyConfig();
}
