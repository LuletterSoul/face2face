package com.luv.face2face.logic.handler;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  19:14 2018/1/5.
 * @since luv-face2face
 */

import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;

/**
 * 该类实际为一个可执行线程,传递channel异步执行任务
 */
public class AbstractIMHandlerImpl implements IMHandler {


    @Override
    public void execute(ChannelHandlerContext channelHandlerContext, Message message) {

    }
}
