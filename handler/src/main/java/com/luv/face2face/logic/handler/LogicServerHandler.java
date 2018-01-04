package com.luv.face2face.logic.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message;
import com.luv.face2face.logic.HandlerManager;
import com.luv.face2face.logic.Worker;
import com.luv.face2face.protobuf.analysis.ParseMap;
import com.luv.face2face.protobuf.generate.internal.Internal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * Created by Dell on 2016/2/18.
 */

public class LogicServerHandler extends SimpleChannelInboundHandler<Message>
{
    private static final Logger logger = LoggerFactory.getLogger(LogicServerHandler.class);

    private static ChannelHandlerContext _gateLogicConnection;

    private static ChannelHandlerContext _authLogicConnection;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message)
        throws Exception
    {
        Internal.GTransfer gt = (Internal.GTransfer)message;
        int ptoNum = gt.getPtoNum();
        Message msg = ParseMap.getMessage(ptoNum, gt.getMsg().toByteArray());

        IMHandler handler;
        if(msg instanceof Internal.Greet) {
            handler = HandlerManager.getHandler(ptoNum, gt.getUserId(), gt.getNetId(), msg, channelHandlerContext);
        } else {
            handler = HandlerManager.getHandler(ptoNum, gt.getUserId(), gt.getNetId(), msg, getGateLogicConnection());
        }

        Worker.dispatch(gt.getUserId(), handler);

    }

    public static void setGateLogicConnection(ChannelHandlerContext ctx)
    {
        _gateLogicConnection = ctx;
    }

    public static ChannelHandlerContext getGateLogicConnection()
    {
        if (_gateLogicConnection != null)
        {
            return _gateLogicConnection;
        }
        else
        {
            return null;
        }
    }

    public static void setAuthLogicConnection(ChannelHandlerContext ctx)
    {
        _authLogicConnection = ctx;
    }

    public static ChannelHandlerContext getAuthLogicConnection()
    {
        if (_authLogicConnection != null)
        {
            return _authLogicConnection;
        }
        else
        {
            return null;
        }
    }
}
