package com.luv.face2face.logic.handler;


import org.apache.thrift.TException;

import com.google.protobuf.Message;

import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;
import java.util.concurrent.Callable;


/**
 * 消息处理器
 */
public interface IMHandler
{
    /**
     * 将业务逻辑交由Service 层处理
     * @param channelHandlerContext  管道上下文
     * @param message 消息
     */
    void execute(ChannelHandlerContext channelHandlerContext, Message message);
}
