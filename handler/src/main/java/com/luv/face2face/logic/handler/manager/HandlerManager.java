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
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  15:49 2018/1/5.
 * @since luv-face2face
 */

public interface HandlerManager
{
    /**
     *
     * @param msg 消息包的类型
     * @param handler 对应的处理器
     */
    void registerHandler(Class<? extends Message> msg, IMHandler handler);


    /**
     * 根据协议号获得消息包的处理器
     * @param protocolNum 协议号
     * @return
     */
    IMHandler getHandler(int protocolNum);

}
