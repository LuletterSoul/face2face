package com.luv.face2face.logic.dispatcher;

import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  12:33 2018/1/5.
 * @since luv-face2face
 */

public interface LogicServerDispatcher {
    /**
     * 分发器，按协议类型将消息分发给不同的处理器进行业务消费
     * @param context
     * @param message
     * @param protocolNum
     */
    void dispatch(ChannelHandlerContext context, Message message,Integer protocolNum);
}
