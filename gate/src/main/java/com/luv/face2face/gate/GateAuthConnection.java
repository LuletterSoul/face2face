package com.luv.face2face.gate;

import com.luv.face2face.gate.handler.GateAuthConnectionHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.luv.face2face.protobuf.code.PacketDecoder;
import com.luv.face2face.protobuf.code.PacketEncoder;

/**
 * Created by Qzy on 2016/1/28.
 */
public class GateAuthConnection {
    private static final Logger logger = LoggerFactory.getLogger(GateAuthConnection.class);

    public static void startGateAuthConnection(String ip, int port) {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel)
                            throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();

                        pipeline.addLast("MessageDecoder", new PacketDecoder());
                        pipeline.addLast("MessageEncoder", new PacketEncoder());
                        pipeline.addLast("GateAuthConnectionHandler", new GateAuthConnectionHandler());  //Auth -> gate
                    }
                });

        bootstrap.connect(ip, port);
    }
}
