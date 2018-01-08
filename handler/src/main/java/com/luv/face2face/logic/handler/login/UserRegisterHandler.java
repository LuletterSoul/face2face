package com.luv.face2face.logic.handler.login;


import com.google.protobuf.Message;
import com.luv.face2face.domain.User;
import com.luv.face2face.logic.handler.AbstractIMHandlerImpl;
import com.luv.face2face.protobuf.generate.cli2srv.login.Auth;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:36 2018/1/6.
 * @since luv-face2face
 */

@Component
@Slf4j
public class UserRegisterHandler extends AbstractIMHandlerImpl
{
    @Override
    public void execute(ChannelHandlerContext channelHandlerContext, Message message)
    {

        Auth.RequestUserRegisterMsg msg = (Auth.RequestUserRegisterMsg)message;
        User newUser = new User();
        newUser.setNickname(msg.getNickname());
        newUser.setSex(msg.getSex());
        newUser.setSignature(msg.getSignature());
        newUser.setPassword(msg.getPassword());
        log.info("user register request:[{}]", newUser.toString());
        getUserService().registerNewAccount(newUser, channelHandlerContext.channel());
    }
}
