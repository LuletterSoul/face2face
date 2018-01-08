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
        public static final Integer SERVER_RESPONSE = 5002;
        public static final Integer LOGIN_SUCCESS = 5003;
        public static final Integer LOGIN_FAILED = 5004;
        public static final Integer REGISTER_SUCCESS =5005;
        public static final Integer REGISTER_FAILED = 5006;
        public static final Integer LIST_FRIENDS = 5007;
        public static final Integer FRIEND_LOGIN = 5008;
        public static final Integer FRIEND_LOGOUT = 5009;
        public static final Integer USER_REFRESH = 5010;
        public static final Integer USER_CHAT =5011;
    }
}
