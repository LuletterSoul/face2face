package com.luv.face2face.logic.handler;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message;
import com.luv.face2face.logic.Worker;
import com.luv.face2face.protobuf.generate.internal.Internal;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by win7 on 2016/3/5.
 */
public class GreetHandler extends IMHandler {
    private static final Logger logger = LoggerFactory.getLogger(GreetHandler.class);

    public GreetHandler(String userid, long netid, Message msg, ChannelHandlerContext ctx) {
        super(userid, netid, msg, ctx);
    }

    @Override
    protected void excute(Worker worker) throws TException {
        Internal.Greet msg = (Internal.Greet)_msg;
        Internal.Greet.From from = msg.getFrom();

        if(from == Internal.Greet.From.Auth) {
            LogicServerHandler.setAuthLogicConnection(_ctx);
            logger.info("[Auth-Logic] connection is established");
        } else if(from == Internal.Greet.From.Gate){
            LogicServerHandler.setGateLogicConnection(_ctx);
            logger.info("[Gate-Logic] connection is established");

        }
    }
}
