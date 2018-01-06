package com.luv.face2face.logic.initializer;


import com.luv.face2face.logic.dispatcher.LogicServerDispatcher;
import com.luv.face2face.protobuf.code.PacketDecoder;
import com.luv.face2face.protobuf.code.PacketEncoder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:49 2018/1/6.
 * @since luv-face2face
 */

@Component
public class LogicChannelInitializer extends ChannelInitializer<SocketChannel>
{

    private PacketDecoder decoder;

    private PacketEncoder encoder;

    private ChannelHandler dispatcher;

    @Autowired
    @Qualifier("packetDecoder")
    public void setDecoder(PacketDecoder decoder)
    {
        this.decoder = decoder;
    }

    @Autowired
    @Qualifier("packetEncoder")
    public void setEncoder(PacketEncoder encoder)
    {
        this.encoder = encoder;
    }

    @Autowired
    @Qualifier("logicDispatcher")
    public void setDispatcher(ChannelHandler dispatcher)
    {
        this.dispatcher = dispatcher;
    }

    @Override
    protected void initChannel(SocketChannel ch)
        throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("MessageDecoder", decoder);
        pipeline.addLast("MessageEncoder", encoder);
        pipeline.addLast("LogicServerDispatcher", dispatcher);
    }
}
