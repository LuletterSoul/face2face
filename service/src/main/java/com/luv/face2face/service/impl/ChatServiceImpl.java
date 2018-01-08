package com.luv.face2face.service.impl;


import com.luv.face2face.protobuf.generate.cli2srv.chat.Chat;
import com.luv.face2face.repository.UserJpaDao;
import com.luv.face2face.service.ChatService;
import com.luv.face2face.service.OnlineService;
import com.luv.face2face.service.session.UserConnectSession;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.luv.face2face.protobuf.generate.cli2srv.chat.Chat.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 20:07 2018/1/5.
 * @since luv-face2face
 */

@Service
@Slf4j
public class ChatServiceImpl extends AbstractService implements ChatService
{
    // private static final Logger LOGGER = LoggerFactory.getLogger(ChatServiceImpl.class);

    // private UserJpaDao userJpaDao;
    //
    // private OnlineService onlineService;

    // @Autowired
    // public void setUserJpaDao(UserJpaDao userJpaDao)
    // {
    // this.userJpaDao = userJpaDao;
    // }
    //
    // @Autowired
    // public OnlineService getOnlineService()
    // {
    // return onlineService;
    // }
    //
    // @Override
    // public void chatToGroupUser(Long desGroupId, String content)
    // {
    //
    // }

    @Override
    public void chatToGroupUser(Long desGroupId, String content)
    {

    }

    @Override
    public void chatToSingleUser(Channel channel, Long fromUserId, Long desUserId, String content)
    {
        UserConnectSession fromUserSession = onlineService.getOnlineUserSessionById(fromUserId);
        ResponseChatToUserMsg.Builder builder = ResponseChatToUserMsg.newBuilder();
        UserConnectSession toUserSession = onlineService.getOnlineUserSessionById(desUserId);
        if (fromUserSession == null || toUserSession == null)
        {
            log.info(
                "Bilateral user relationship build failed.One or more friend isn't online.check or cache message when user online.");
            return;
        }
        builder.setContent(content);
        // 发送者收到的消息包中源用户ID跟目的用户ID应该一致
        builder.setPushToUserId(fromUserSession.getUser().getUserId());
        builder.setFromToUserId(fromUserSession.getUser().getUserId());
        // 双方都需要推送消息
        ResponseChatToUserMsg msgFromUser = builder.build();
        fromUserSession.sendPacket(msgFromUser);
        builder.setPushToUserId(desUserId);
        ResponseChatToUserMsg msgToUser = builder.build();
        toUserSession.sendPacket(msgToUser);
    }
}
