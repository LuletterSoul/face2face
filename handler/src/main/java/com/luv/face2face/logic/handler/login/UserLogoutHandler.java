package com.luv.face2face.logic.handler.login;


import com.google.protobuf.Message;
import com.luv.face2face.logic.handler.AbstractIMHandlerImpl;
import com.luv.face2face.protobuf.generate.cli2srv.login.Auth;
import com.luv.face2face.service.session.SessionCloseReason;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.luv.face2face.protobuf.generate.cli2srv.login.Auth.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:46 2018/1/6.
 * @since luv-face2face
 */

@Component
@Slf4j
public class UserLogoutHandler extends AbstractIMHandlerImpl
{
    @Override
    public void execute(ChannelHandlerContext channelHandlerContext, Message message)
    {
        RequestLogoutMsg msg = (RequestLogoutMsg)message;
        log.info("User:[{}] send logout request.", msg.getUserId());
        getLoginService().logoutUser(msg.getUserId(), channelHandlerContext.channel(),
            SessionCloseReason.NORMAL);
    }
}
