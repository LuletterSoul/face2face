package com.luv.face2face.logic.handler.manager;


import com.luv.face2face.logic.handler.IMHandler;

import com.google.protobuf.Message;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:49 2018/1/5.
 * @since luv-face2face
 */

public interface HandlerManager
{
    /**
     * @param msg
     *            消息包的类型
     * @param handler
     *            对应的处理器
     */
    void registerHandler(Class<? extends Message> msg, IMHandler handler);

    /**
     * 根据协议号获得处理器
     * 
     * @param protocolNum
     *            协议号
     * @return
     */
    IMHandler getHandler(int protocolNum);

    /**
     * 根据消息包的类型获得处理器
     * @param msg
     * @return
     */
    IMHandler getHandler(Message msg);

}
