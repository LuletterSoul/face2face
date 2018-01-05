package com.luv.face2face.gate.handler;

import com.google.protobuf.Message;
import com.luv.face2face.gate.utils.ClientConnectionMap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.luv.face2face.protobuf.ParseRegistryMap;
import com.luv.face2face.protobuf.Utils;
import com.luv.face2face.protobuf.analysis.ParseMap;
import com.luv.face2face.protobuf.generate.internal.Internal;

/**
 * Created by Dell on 2016/2/2.
 */
public class GateAuthConnectionHandler extends SimpleChannelInboundHandler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(GateAuthConnectionHandler.class);
    private static ChannelHandlerContext _gateAuthConnection;

    public static ChannelHandlerContext getGateAuthConnection() {
        return _gateAuthConnection;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        _gateAuthConnection = ctx;
        logger.info("[Gate-Auth] connection is established");

        sendGreet2Auth();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        Internal.GTransfer gtf = (Internal.GTransfer) message;

        Message cmd = ParseMap.getMessage(gtf.getPtoNum(), gtf.getMsg().toByteArray());

        ByteBuf out = Utils.pack2Client(cmd);

        ClientConnectionMap.getClientConnection(gtf.getNetId()).getCtx().writeAndFlush(out);
    }

    private void sendGreet2Auth() {
        //向auth送Greet协议
        Internal.Greet.Builder ig = Internal.Greet.newBuilder();
        ig.setFrom(Internal.Greet.From.Gate);
        ByteBuf out = Utils.pack2Server(ig.build(), ParseRegistryMap.GREET, -1, Internal.Dest.Auth, "admin");
        getGateAuthConnection().writeAndFlush(out);
        logger.info("Gate send Green to Auth.");
    }
}