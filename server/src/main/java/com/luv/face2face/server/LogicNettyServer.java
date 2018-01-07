package com.luv.face2face.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:39 2018/1/6.
 * @since luv-face2face
 */
@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LogicNettyServer extends AbstractNettyServerImpl
{
    @Qualifier("logicServerBootstrap")
    private ServerBootstrap serverBootstrap;

    @Override
    public void start()
        throws Exception
    {
        Channel channel = serverBootstrap.bind(
            new InetSocketAddress("127.0.0.1",8088)).sync().channel();
        ALL_CHANNELS.add(channel);
    }

    @Override
    public TransmissionProtocol getTransmissionProtocol()
    {
        return TRANSMISSION_PROTOCOL.TCP;
    }
}
