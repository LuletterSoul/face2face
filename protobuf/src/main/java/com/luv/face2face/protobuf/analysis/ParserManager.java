package com.luv.face2face.protobuf.analysis;


import com.google.protobuf.Message;
import com.luv.face2face.protobuf.Protocol;
import com.luv.face2face.protobuf.generate.ser2cli.login.Server;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;

import static com.luv.face2face.protobuf.generate.cli2srv.chat.Chat.*;
import static com.luv.face2face.protobuf.generate.cli2srv.login.Auth.*;
import static com.luv.face2face.protobuf.generate.ser2cli.login.Server.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:01 2018/1/5.
 * @since luv-face2face
 */

/**
 * 维护一组解析器 可根据消息包中的协议号、消息类型获得对应的处理器
 */
@Slf4j
public class ParserManager
{
    @FunctionalInterface
    public interface Parsing
    {
        Message process(byte[] bytes)
            throws IOException;
    }

    public ParserManager()
    {
        initDefaultProtocol();
    }

    // 协议号--消息解析器
    private HashMap<Integer, ParserManager.Parsing> protocolNumToParser = new HashMap<>();

    // 消息类型--协议号
    private HashMap<Class<?>, Integer> packetTypeToProtocolNum = new HashMap<>();

    private void initDefaultProtocol()
    {
        this.register(Protocol.User.USER_LOGIN, RequestLoginMsg::parseFrom, RequestLoginMsg.class);
        this.register(Protocol.User.USER_LOGIN, RequestLoginMsg::parseFrom, RequestLoginMsg.class);
        this.register(Protocol.User.USER_LOGOUT, RequestLogoutMsg::parseFrom,
            RequestLogoutMsg.class);
        this.register(Protocol.User.USER_REGISTER, RequestUserRegisterMsg::parseFrom,
            RequestUserRegisterMsg.class);
        this.register(Protocol.Chat.CHAT_SINGLE, RequestChatToUserMsg::parseFrom,
            RequestChatToUserMsg.class);
        this.register(Protocol.Chat.CHAT_GROUP, RequestChatToGroupMsg::parseFrom,
            RequestChatToGroupMsg.class);
        this.register(Protocol.Server.SERVER_RESPONSE, ResponseMsg::parseFrom, ResponseMsg.class);
        this.register(Protocol.Server.LOGIN_SUCCESS, ResServerLoginSucc::parseFrom, ResServerLoginSucc.class);
        this.register(Protocol.Server.LOGIN_FAILD, ResServerLoginFailed::parseFrom, ResServerLoginFailed.class);
        this.register(Protocol.Server.REGISTER_SUCCESS, ResServerRegisterSucc::parseFrom, ResServerRegisterSucc.class);
        this.register(Protocol.Server.REGISTER_FAILD, ResServerRegisterFailed::parseFrom, ResServerRegisterFailed.class);
    }

    /**
     * 注册
     * 
     * @param ptoNum
     *            协议号
     * @param parse
     *            解析器
     * @param cla
     *            数据包类型
     */
    public void register(int ptoNum, ParserManager.Parsing parse, Class<?> cla)
    {
        registerProtocolNum2ParserMap(ptoNum, parse);

        registerPktTypeProtocolNumMap(ptoNum, cla);
    }

    /**
     * 注册消息类型--协议号器映射关系
     * 
     * @param ptoNum
     *            协议号
     * @param cla
     *            消息数据包类型
     */
    public void registerPktTypeProtocolNumMap(int ptoNum, Class<?> cla)
    {
        if (packetTypeToProtocolNum.get(cla) == null)
            packetTypeToProtocolNum.put(cla, ptoNum);
        else
        {
            log.error("pto has been registered in msg2ptoNum, ptoNum: {}", ptoNum);
        }
    }

    /**
     * 注册协议--消息解析器映射关系
     * 
     * @param ptoNum
     *            协议号
     * @param parse
     *            解析器
     */
    public void registerProtocolNum2ParserMap(int ptoNum, ParserManager.Parsing parse)
    {
        if (protocolNumToParser.get(ptoNum) == null)
            protocolNumToParser.put(ptoNum, parse);
        else
        {
            log.error("pto has been registered in parseMap, ptoNum: {}", ptoNum);
        }
    }

    /**
     * @param ptoNum
     * @param bytes
     * @return
     * @throws IOException
     */
    public Message getMessage(int ptoNum, byte[] bytes)
        throws IOException
    {
        Parsing parser = protocolNumToParser.get(ptoNum);
        if (parser == null)
        {
            log.error("Miss match parser.UnKnown Protocol Num: {}", ptoNum);
        }
        assert parser != null;
        return parser.process(bytes);
    }

    /**
     * 根据消息包类型得到协议号
     * 
     * @param msg
     *            消息数据包类型
     * @return 协议号
     */
    public Integer getPtoNum(Message msg)
    {
        return getPtoNum(msg.getClass());
    }

    public Integer getPtoNum(Class<?> clz)
    {
        Integer ptoNum = packetTypeToProtocolNum.get(clz);
        if (ptoNum == null)
        {
            log.error("Unknown message type.Protocol type hasn't been register.{}", clz);
            throw new IllegalArgumentException(
                "Unknown message type.Protocol type hasn't been registered");
        }
        return ptoNum;
    }

    public HashMap<Class<?>, Integer> getPacketTypeToProtocolNum()
    {
        return packetTypeToProtocolNum;
    }

    public HashMap<Integer, Parsing> getProtocolNumToParser()
    {
        return protocolNumToParser;
    }

    public void setProtocolNumToParser(HashMap<Integer, Parsing> protocolNumToParser)
    {
        this.protocolNumToParser = protocolNumToParser;
    }

    public void setPacketTypeToProtocolNum(HashMap<Class<?>, Integer> packetTypeToProtocolNum)
    {
        this.packetTypeToProtocolNum = packetTypeToProtocolNum;
    }
}
