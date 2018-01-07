package com.luv.face2face.logic.handler;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:14 2018/1/5.
 * @since luv-face2face
 */

import com.google.protobuf.Message;
import com.luv.face2face.service.ChatService;
import com.luv.face2face.service.LoginService;
import com.luv.face2face.service.UserService;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 该类实际为一个可执行线程,传递channel异步执行任务
 */
@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AbstractIMHandlerImpl implements IMHandler
{

    private ChatService chatService;

    private UserService userService;

    private LoginService loginService;

    @Autowired
    public void setChatService(ChatService chatService)
    {
        this.chatService = chatService;
    }

    @Autowired
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @Autowired
    public void setLoginService(LoginService loginService)
    {
        this.loginService = loginService;
    }

    @Override
    public void execute(ChannelHandlerContext channelHandlerContext, Message message)
    {

    }
}
