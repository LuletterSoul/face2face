package com.luv.face2face.logic.handler;


import io.netty.channel.ChannelHandlerContext;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 12:07 2018/1/8.
 * @since face2face
 */

public interface UserRecordCleaner
{
    void clean(ChannelHandlerContext channelHandlerContext);
}
