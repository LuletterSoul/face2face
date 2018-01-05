package com.luv.face2face.logic.task;


import com.google.protobuf.Message;
import com.luv.face2face.logic.handler.IMHandler;
import io.netty.channel.ChannelHandlerContext;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 20:25 2018/1/5.
 * @since luv-face2face
 */

public class ChannelTaskImpl implements ChannelTask
{
    private IMHandler handler;

    private Message message;

    private ChannelHandlerContext channelHandlerContext;

    public ChannelTaskImpl(IMHandler handler, Message message,
                           ChannelHandlerContext channelHandlerContext)
    {
        this.handler = handler;
        this.message = message;
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public void run()
    {
        handler.execute(channelHandlerContext, message);
    }
}
