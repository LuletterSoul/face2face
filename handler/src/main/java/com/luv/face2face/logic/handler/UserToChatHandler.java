package com.luv.face2face.logic.handler;


import com.google.protobuf.Message;
import com.luv.face2face.protobuf.generate.cli2srv.chat.Chat;
import com.luv.face2face.service.ChatService;
import com.luv.face2face.service.UserService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.luv.face2face.protobuf.generate.cli2srv.chat.Chat.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:17 2018/1/5.
 * @since luv-face2face
 */

@Component
public class UserToChatHandler extends AbstractIMHandlerImpl
{
    private UserService userService;

    private ChatService chatService;

    @Autowired
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @Autowired
    public void setChatService(ChatService chatService)
    {
        this.chatService = chatService;
    }

    @Override
    public void execute(ChannelHandlerContext channelHandlerContext, Message message)
    {
        RequestChatToGroupMsg requestChatToGroupMsg = (RequestChatToGroupMsg)message;
        ResponseChatToUserMsg.Builder builder = ResponseChatToUserMsg.newBuilder();
        String content = requestChatToGroupMsg.getContent();

    }
}
