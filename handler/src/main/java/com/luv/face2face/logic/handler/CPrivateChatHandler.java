package com.luv.face2face.logic.handler;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message;
import com.luv.face2face.logic.Worker;
import com.luv.face2face.protobuf.ParseRegistryMap;
import com.luv.face2face.protobuf.Utils;
import com.luv.face2face.protobuf.generate.cli2srv.chat.Chat;
import com.luv.face2face.protobuf.generate.cli2srv.login.Auth;
import com.luv.face2face.protobuf.generate.internal.Internal;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by win7 on 2016/3/5.
 */
public class CPrivateChatHandler extends IMHandler{
    private static final Logger logger = LoggerFactory.getLogger(CPrivateChatHandler.class);

    public CPrivateChatHandler(String userid, long netid, Message msg, ChannelHandlerContext ctx) {
        super(userid, netid, msg, ctx);
    }

    @Override
    protected void excute(Worker worker) throws TException {
        Chat.CPrivateChat msg = (Chat.CPrivateChat) _msg;
        ByteBuf byteBuf = null;

        //转发给auth
        byteBuf = Utils.pack2Server(_msg, ParseRegistryMap.CPRIVATECHAT, Internal.Dest.Auth, msg.getDest());
        LogicServerHandler.getAuthLogicConnection().writeAndFlush(byteBuf);

        //给发消息的人回应
        Auth.SResponse.Builder sr = Auth.SResponse.newBuilder();
        sr.setCode(300);
        sr.setDesc("Server received message successed");
        byteBuf = Utils.pack2Client(sr.build());
        _ctx.writeAndFlush(byteBuf);
    }
}
