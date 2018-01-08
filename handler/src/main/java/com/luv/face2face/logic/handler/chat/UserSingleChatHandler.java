package com.luv.face2face.logic.handler.chat;


import com.google.protobuf.Message;
import com.luv.face2face.logic.handler.AbstractIMHandlerImpl;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.luv.face2face.protobuf.generate.cli2srv.chat.Chat.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:17 2018/1/5.
 * @since luv-face2face
 */
@Slf4j
@Component
public class UserSingleChatHandler extends AbstractIMHandlerImpl
{

    @Override
    public void execute(ChannelHandlerContext channelHandlerContext, Message message)
    {
        RequestChatToUserMsg msg = (RequestChatToUserMsg)message;
        log.info("[{}] consume single chat service.From user :[{}] to user:[{}]",
            UserSingleChatHandler.class.getSimpleName(), msg.getChatFromUserId(),
            msg.getChatToUserId());
        getChatService().chatToSingleUser(channelHandlerContext.channel(), msg.getChatFromUserId(),
            msg.getChatToUserId(), msg.getContent());
    }
}
