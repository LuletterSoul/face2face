package com.luv.face2face.logic.config;


import com.luv.face2face.logic.handler.chat.UserGroupChatHandler;
import com.luv.face2face.logic.handler.chat.UserSingleChatHandler;
import com.luv.face2face.logic.handler.login.UserLoginHandler;
import com.luv.face2face.logic.handler.login.UserLogoutHandler;
import com.luv.face2face.logic.handler.login.UserRegisterHandler;
import com.luv.face2face.logic.handler.manager.HandlerManager;
import com.luv.face2face.logic.handler.manager.impl.DefaultHandlerManager;
import com.luv.face2face.logic.handler.upload.ReqSendFileToUserHandler;
import com.luv.face2face.logic.handler.upload.ResFileUploadCompleteHandler;
import com.luv.face2face.protobuf.analysis.ParserManager;
import com.luv.face2face.protobuf.generate.ser2cli.file.Server;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.luv.face2face.protobuf.generate.cli2srv.chat.Chat.*;
import static com.luv.face2face.protobuf.generate.cli2srv.login.Auth.*;
import static com.luv.face2face.protobuf.generate.ser2cli.file.Server.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:22 2018/1/5.
 * @since luv-face2face
 */

@Configuration
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class IMLogicConfiguration
{
    private UserSingleChatHandler singleChatHandler;

    private UserGroupChatHandler groupChatHandler;

    private UserLoginHandler loginHandler;

    private UserLogoutHandler logoutHandler;

    private UserRegisterHandler registerHandler;

    private ReqSendFileToUserHandler reqSendFileToUserHandler;

    private ResFileUploadCompleteHandler resFileUploadCompleteHandler;

    @Bean
    public HandlerManager handlerManager()
    {
        DefaultHandlerManager handlerManager = new DefaultHandlerManager();
        handlerManager.setManager(parserManager());
        handlerManager.registerHandler(RequestChatToUserMsg.class, singleChatHandler);
        handlerManager.registerHandler(RequestChatToGroupMsg.class, groupChatHandler);
        handlerManager.registerHandler(RequestLoginMsg.class, loginHandler);
        handlerManager.registerHandler(RequestLogoutMsg.class, logoutHandler);
        handlerManager.registerHandler(RequestUserRegisterMsg.class,registerHandler);
        handlerManager.registerHandler(ReqFileUploadMsg.class, reqSendFileToUserHandler);
        handlerManager.registerHandler(ResFileUploadComplete.class,resFileUploadCompleteHandler);
        return handlerManager;
    }

    // @Bean
    // public ChannelHandler chatServerHandleDispatcher()
    // {
    // ChatServerHandleDispatcher dispatcher = new ChatServerHandleDispatcher();
    // dispatcher.setThreadPoolTaskExecutor(threadPoolTaskExecutor);
    // dispatcher.se
    // return dispatcher;
    // }

    /**
     * 注册消息解析器
     * 
     * @return 解析消息的管理器
     */
    @Bean
    public ParserManager parserManager()
    {
        //        parserManager.register(Protocol.User.USER_LOGIN, RequestLoginMsg::parseFrom,
//            RequestLoginMsg.class);
//        parserManager.register(Protocol.User.USER_LOGOUT, RequestLogoutMsg::parseFrom,
//            RequestLogoutMsg.class);
//        parserManager.register(Protocol.User.USER_REGISTER, RequestUserRegisterMsg::parseFrom,
//            RequestUserRegisterMsg.class);
//        parserManager.register(Protocol.Chat.CHAT_SINGLE, RequestChatToUserMsg::parseFrom,
//            RequestChatToUserMsg.class);
//        parserManager.register(Protocol.Chat.CHAT_GROUP, RequestChatToGroupMsg::parseFrom,
//            RequestChatToGroupMsg.class);
        return new ParserManager();
    }
}
