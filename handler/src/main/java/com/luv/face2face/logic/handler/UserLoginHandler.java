package com.luv.face2face.logic.handler;


import com.google.protobuf.Message;
import com.luv.face2face.protobuf.generate.cli2srv.login.Auth;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.luv.face2face.protobuf.generate.cli2srv.login.Auth.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:27 2018/1/6.
 * @since luv-face2face
 */

@Slf4j
@Component
public class UserLoginHandler extends AbstractIMHandlerImpl
{
    @Override
    public void execute(ChannelHandlerContext channelHandlerContext, Message message)
    {
        RequestLoginMsg msg = (RequestLoginMsg)message;
        log.info("User:[{}] send login request.", ((RequestLoginMsg)message).getUserId());
        getLoginService().loginUser(channelHandlerContext.channel(), msg.getUserId(),
            msg.getPassword());
    }
}
