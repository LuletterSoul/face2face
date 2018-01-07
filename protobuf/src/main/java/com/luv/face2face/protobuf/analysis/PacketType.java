package com.luv.face2face.protobuf.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.luv.face2face.protobuf.Protocol;
import com.luv.face2face.protobuf.generate.cli2srv.chat.Chat;
import com.luv.face2face.protobuf.generate.cli2srv.login.Auth;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  13:18 2018/1/5.
 * @since luv-face2face
 */

public enum PacketType {

    //业务上行数据包

    //----------------------模块号申明------------------------------
    //----------------请求协议id格式为 模块号_000 起--------------------
    //----------------推送协议id格式为 模块号_200 起--------------------
    //------------------基础服务1-----------------------------------
    //------------------http协议2----------------------------------
    //------------------用户3----------------------------------
    //------------------聊天4----------------------------------
    //------------------好友5----------------------------------


//    /** 请求--链接心跳包 */
//    ReqHeartBeat(1_000, ReqHeartBeatPacket.class),
//    /** 推送--新用户注册  */
//    RespHeartBeat(1_200, ResHeartBeatPacket.class),
//
    /** 请求--新用户注册  */
    ReqUserRegister(Protocol.User.USER_REGISTER, Auth.RequestUserRegisterMsg.class),
    /** 请求--请求--用户登陆  */
    ReqUserLogin(Protocol.User.USER_LOGIN, Auth.RequestLoginMsg.class),
    /** 请求--请求--用户登出  */
    ReqUserLogout(Protocol.User.USER_LOGOUT, Auth.RequestLogoutMsg.class),
//
//    /** 推送--新用户注册  */
//    ResUserRegister(3_200, ResUserRegisterPacket.class),
//    /** 推送--用户登录  */
//    ResUserLogin(3_201, ResUserLoginPacket.class),
//    /** 推送--玩家信息 */
//    ResUserInfo(3_202, ResUserInfoPacket.class),

    /** 请求--单聊  */
    RequestChatToGroupMsg(Protocol.Chat.CHAT_SINGLE, Chat.RequestChatToUserMsg.class),
    /** 请求--群聊  */
    ReqChatToGroup(Protocol.Chat.CHAT_GROUP, Chat.RequestChatToGroupMsg.class),

//    /** 推送--单聊 */
//    ResChatToUser(4_200, ResChatToUserPacket.class),
//    /** 推送--群聊 */
//    ResChatToGroup(4_201, ResChatToGroupPacket.class),
//
//    /** 推送--好友列表 */
//    ResFriendList(5_200, ResFriendListPacket.class),
//    /** 推送--好友登录 */
//    ResFriendLogin(5_201, ResFriendLoginPacket.class),
//    /** 推送--好友下线 */
//    ResFriendLogout(5_202, ResFriendLogoutPacket.class),

    ;

    private int type;
    private Class<?> packetClass;
    private static Map<Integer,Class<?>> PACKET_CLASS_MAP = new HashMap<>();

    public static void initPackets() {
        Set<Integer> typeSet = new HashSet<>();
        Set<Class<?>> packets = new HashSet<>();
        for(PacketType p: PacketType.values()){
            int type = p.getType();
            if(typeSet.contains(type)){
                throw new IllegalStateException("packet type 协议类型重复"+type);
            }
            Class<?> packet = p.getPacketClass();
            if (packets.contains(packet)) {
                throw new IllegalStateException("packet定义重复"+p);
            }
            PACKET_CLASS_MAP.put(type,p.getPacketClass());
            typeSet.add(type);
            packets.add(packet);
        }
    }

    PacketType(int type, Class<?> packetClass){
        this.setType(type);
        this.packetClass = packetClass;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Class<?> getPacketClass() {
        return packetClass;
    }

    public void setPacketClass(Class<?> packetClass) {
        this.packetClass = packetClass;
    }


    public static  Class<?> getPacketClassBy(int packetType){
        return PACKET_CLASS_MAP.get(packetType);
    }

    public static void main(String[] args) {
        for(PacketType p: PacketType.values()){
            System.err.println(p.getPacketClass().getSimpleName());
        }
    }

}
