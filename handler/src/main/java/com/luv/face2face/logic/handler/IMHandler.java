package com.luv.face2face.logic.handler;


import org.apache.thrift.TException;

import com.google.protobuf.Message;

import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;
import java.util.concurrent.Callable;


/**
 * Created by Dell on 2016/3/2.
 */
public interface IMHandler
{
    void execute(ChannelHandlerContext channelHandlerContext, Message message);
}
