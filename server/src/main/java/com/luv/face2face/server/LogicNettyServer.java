package com.luv.face2face.server;


import com.luv.face2face.config.IMServerConfiguration;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:39 2018/1/6.
 * @since luv-face2face
 */
@Slf4j
@Component
public class LogicNettyServer extends AbstractNettyServerImpl
{
    @Autowired
    @Qualifier("logicServerBootstrap")
    private ServerBootstrap serverBootstrap;

    @Value("${server.address}")
    private String ipAddress;

    @Value("${face2face.server.port}")
    private int portNum;

    @Override
    public void start()
        throws Exception
    {
        Channel channel = serverBootstrap.bind(ipAddress, portNum).sync().channel();
        ALL_CHANNELS.add(channel);
    }

    @Override
    public TransmissionProtocol getTransmissionProtocol()
    {
        return TRANSMISSION_PROTOCOL.TCP;
    }

}
