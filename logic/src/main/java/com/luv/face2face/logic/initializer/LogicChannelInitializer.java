package com.luv.face2face.logic.initializer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.luv.face2face.protobuf.analysis.ParserManager;
import com.luv.face2face.protobuf.code.PacketDecoder;
import com.luv.face2face.protobuf.code.PacketEncoder;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:49 2018/1/6.
 * @since luv-face2face
 */

@Component
public class LogicChannelInitializer extends ChannelInitializer<SocketChannel>
{
    private ChannelHandler dispatcher;

    private ApplicationContext context;

    private ParserManager parserManager;

    // @Autowired
    // @Qualifier("packetDecoder")
    // public void setDecoder(PacketDecoder decoder)
    // {
    // this.decoder = decoder;
    // }
    //
    // @Autowired
    // @Qualifier("packetEncoder")
    // public void setEncoder(PacketEncoder encoder)
    // {
    // this.encoder = encoder;
    // }

    @Autowired
    @Qualifier("logicDispatcher")
    public void setDispatcher(ChannelHandler dispatcher)
    {
        this.dispatcher = dispatcher;
    }

    @Autowired
    public void setParserManager(ParserManager parserManager)
    {
        this.parserManager = parserManager;
    }

    @Override
    protected void initChannel(SocketChannel ch)
        throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        PacketEncoder encoder = new PacketEncoder();
        PacketDecoder decoder = new PacketDecoder();
        decoder.setParserManager(parserManager);
        encoder.setParserManager(parserManager);
        pipeline.addLast("MessageDecoder", decoder);
        pipeline.addLast("MessageEncoder", encoder);
        pipeline.addLast("LogicServerDispatcher", dispatcher);
    }

    // @Override
    // public void setApplicationContext(ApplicationContext applicationContext) throws
    // BeansException {
    // this.context = applicationContext;
    // }
}
