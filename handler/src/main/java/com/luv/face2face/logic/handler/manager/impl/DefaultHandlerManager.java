package com.luv.face2face.logic.handler.manager.impl;


import com.google.protobuf.Message;
import com.luv.face2face.logic.handler.IMHandler;
import com.luv.face2face.logic.handler.manager.HandlerManager;
import com.luv.face2face.protobuf.analysis.ParserManager;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:49 2018/1/5.
 * @since luv-face2face
 */

public abstract class DefaultHandlerManager implements HandlerManager
{
    private static final Logger logger = LoggerFactory.getLogger(HandlerManager.class);

    private Map<Integer, IMHandler> handlerMap = new HashMap<>();

    private ParserManager parserManager;

    public void setHandlerMap(Map<Integer, IMHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public void setManager(ParserManager manager) {
        this.parserManager = manager;
    }

//    @Override
//    public void registerHandler(Class<? extends Message> msg, IMHandler handler)
//    {
//        //根据消息包类型获得协议号
//        int ptoNum = parserManager.getPtoNum(msg);
//        if (handlerMap.get(ptoNum) == null) {
//            handlerMap.put(ptoNum, handler);
//        }
//        else{
//            logger.error("handler has been registered in handlerMap, ptoNum: {}", ptoNum);
//        }
////        try
////        {
////            Constructor<? extends IMHandler> constructor = handler.getConstructor(String.class,
////                    long.class, Message.class, ChannelHandlerContext.class);
////            _handlers.put(num, constructor);
////        }
////        catch (NoSuchMethodException e)
////        {
////            throw new RuntimeException(e);
////        }
//    }

    @Override
    public void registerHandler(Class<? extends Message> msg, IMHandler handler) {
        //根据消息包类型获得协议号
        int ptoNum = parserManager.getPtoNum(msg);
        if (handlerMap.get(ptoNum) == null) {
            handlerMap.put(ptoNum, handler);
        }
        else{
            logger.error("handler has been registered in handlerMap, ptoNum: {}", ptoNum);
        }
    }

    @Override
    public IMHandler getHandler(int protocolNum) {
        IMHandler handler = handlerMap.get(protocolNum);
        if (handler == null) {
            logger.error("handler not exist, Message Number: {}", protocolNum);
            return null;
        }
        return handler;
    }
}
