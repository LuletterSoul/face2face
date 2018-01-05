package com.luv.face2face.protobuf.analysis;


import com.google.protobuf.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:01 2018/1/5.
 * @since luv-face2face
 */

public class ParserManager
{
    private static final Logger logger = LoggerFactory.getLogger(ParserManager.class);

    @FunctionalInterface
    public interface Parsing
    {
        Message process(byte[] bytes)
            throws IOException;
    }

    // 协议号--消息解析器
    private HashMap<Integer, ParserManager.Parsing> protocolNumToParser = new HashMap<>();

    // 消息类型--协议号
    private HashMap<Class<?>, Integer> packetTypeToProtocolNum = new HashMap<>();


    /**
     * 注册
     * @param ptoNum 协议号
     * @param parse 解析器
     * @param cla 数据包类型
     */
    public void register(int ptoNum, ParserManager.Parsing parse, Class<?> cla)
    {
        registerProtocolNum2ParserMap(ptoNum, parse);

        registerPktTypeProtocolNumMap(ptoNum, cla);
    }

    /**
     * 注册消息类型--协议号器映射关系
     * @param ptoNum 协议号
     * @param cla 消息数据包类型
     */
    public void registerPktTypeProtocolNumMap(int ptoNum, Class<?> cla) {
        if (packetTypeToProtocolNum.get(cla) == null)
            packetTypeToProtocolNum.put(cla, ptoNum);
        else
        {
            logger.error("pto has been registered in msg2ptoNum, ptoNum: {}", ptoNum);
        }
    }

    /**
     * 注册协议--消息解析器映射关系
     * @param ptoNum 协议号
     * @param parse 解析器
     */
    public void registerProtocolNum2ParserMap(int ptoNum, ParserManager.Parsing parse) {
        if (protocolNumToParser.get(ptoNum) == null)
            protocolNumToParser.put(ptoNum, parse);
        else
        {
            logger.error("pto has been registered in parseMap, ptoNum: {}", ptoNum);
        }
    }

    /**
     *
     * @param ptoNum
     * @param bytes
     * @return
     * @throws IOException
     */
    public  Message getMessage(int ptoNum, byte[] bytes)
        throws IOException
    {
        Parsing parser =  protocolNumToParser.get(ptoNum);
        if (parser == null)
        {
            logger.error("Miss match parser.UnKnown Protocol Num: {}", ptoNum);
        }
        return parser.process(bytes);
    }

    /**
     * 根据消息包类型得到协议号
     * @param msg 消息数据包类型
     * @return 协议号
     */
    public  Integer getPtoNum(Message msg) {
        return getPtoNum(msg.getClass());
    }

    public Integer getPtoNum(Class<?> clz) {
        return packetTypeToProtocolNum.get(clz);
    }
}
