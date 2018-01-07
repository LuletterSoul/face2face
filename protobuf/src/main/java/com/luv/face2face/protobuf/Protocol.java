package com.luv.face2face.protobuf;


import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 10:06 2018/1/6.
 * @since luv-face2face
 */

public class Protocol
{
    public static class User
    {
        public static final Integer USER_REGISTER = 3000;

        public static final Integer USER_LOGIN = 3001;

        public static final Integer USER_LOGOUT = 3002;
    }

    public static class Chat
    {
        public static final Integer CHAT_SINGLE = 4000;

        public static final Integer CHAT_GROUP = 4001;
    }

    public static class Server
    {
        public static final Integer SERVER_RESPONSE = 4002;
        public static final Integer LOGIN_SUCCESS = 4003;
        public static final Integer LOGIN_FAILD  = 4004;
        public static final Integer REGISTER_SUCCESS =4005;
        public static final Integer REGISTER_FAILD = 4006;
    }
}
