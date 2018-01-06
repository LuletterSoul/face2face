package com.luv.face2face.message;

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
}
