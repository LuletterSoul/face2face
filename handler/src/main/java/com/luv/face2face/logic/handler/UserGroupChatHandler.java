package com.luv.face2face.logic.handler;


import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 12:04 2018/1/6.
 * @since luv-face2face
 */
@Slf4j
@Component
public class UserGroupChatHandler extends AbstractIMHandlerImpl
{
    @Override
    public void execute(ChannelHandlerContext channelHandlerContext, Message message)
    {
        super.execute(channelHandlerContext, message);
    }
}
