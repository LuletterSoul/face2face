package com.luv.face2face.logic.dispatcher;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  12:33 2018/1/5.
 * @since luv-face2face
 */

public interface IChatServerDispatcher {
    /**
     * 处理分发器，按协议类型将消息分发给不同的处理器进行业务的处理
     * @param context
     * @param msg
     */
    public void delegate(ChannelHandlerContext context, Object msg);
}
