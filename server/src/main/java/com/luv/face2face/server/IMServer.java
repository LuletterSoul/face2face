package com.luv.face2face.server;

import java.net.InetSocketAddress;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  17:09 2018/1/6.
 * @since luv-face2face
 */

public interface IMServer {

    public interface TransmissionProtocol{

    }

    /**
     * 服务器使用的协议
     */
    public enum TRANSMISSION_PROTOCOL implements TransmissionProtocol {
        TCP,UDP
    }

    /**
     * 传输协议
     * @return
     */
    TransmissionProtocol getTransmissionProtocol();

    /**
     * 启动服务器
     * @throws Exception
     */
    void start() throws Exception;

    /**
     * 服务器传入端口
     * @param port
     * @throws Exception
     */
    void start(int port) throws Exception;;

    void start(InetSocketAddress socketAddress) throws Exception;

    // 关闭服务器
    void stop() throws Exception;

    InetSocketAddress getSocketAddress();
}
