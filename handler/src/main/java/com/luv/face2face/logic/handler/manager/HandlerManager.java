package com.luv.face2face.logic.handler.manager;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.luv.face2face.logic.handler.IMHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message;
import com.luv.face2face.logic.handler.CPrivateChatHandler;
import com.luv.face2face.logic.handler.GreetHandler;
import com.luv.face2face.protobuf.analysis.ParseMap;
import com.luv.face2face.protobuf.generate.cli2srv.chat.Chat;
import com.luv.face2face.protobuf.generate.internal.Internal;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;


/**
 * Created by Dell on 2016/3/4.
 */
@Component
public class HandlerManager
{
    private static final Logger logger = LoggerFactory.getLogger(HandlerManager.class);

    private Map<Integer, Constructor<? extends IMHandler>> _handlers = new HashMap<>();

    private boolean maintainable = false;

    public HandlerManager()
    {
        initDefaultHandlers();
    }

    private void initDefaultHandlers()
    {
        register(Internal.Greet.class, GreetHandler.class);
        register(Chat.CPrivateChat.class, CPrivateChatHandler.class);
    }

    public void register(Class<? extends Message> msg, Class<? extends IMHandler> handler)
    {
        int num = ParseMap.getPtoNum(msg);
        try
        {
            Constructor<? extends IMHandler> constructor = handler.getConstructor(String.class,
                long.class, Message.class, ChannelHandlerContext.class);
            _handlers.put(num, constructor);
        }
        catch (NoSuchMethodException e)
        {
            throw new RuntimeException(e);
        }
    }

    public  IMHandler getHandler(int msgNum, String userId, long netId, Message msg,
                                       ChannelHandlerContext ctx)
        throws IllegalAccessException,
        InvocationTargetException,
        InstantiationException
    {
        Constructor<? extends IMHandler> constructor = _handlers.get(msgNum);
        if (constructor == null)
        {
            logger.error("handler not exist, Message Number: {}", msgNum);
            return null;
        }

        return constructor.newInstance(userId, netId, msg, ctx);
    }
}
