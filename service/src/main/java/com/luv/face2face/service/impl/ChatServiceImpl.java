package com.luv.face2face.service.impl;


import com.luv.face2face.protobuf.generate.cli2srv.chat.Chat;
import com.luv.face2face.repository.UserJpaDao;
import com.luv.face2face.service.ChatService;
import com.luv.face2face.service.OnlineService;
import com.luv.face2face.service.session.UserConnectSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.luv.face2face.protobuf.generate.cli2srv.chat.Chat.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 20:07 2018/1/5.
 * @since luv-face2face
 */

@Service
public class ChatServiceImpl implements ChatService
{
    private UserJpaDao userJpaDao;

    private OnlineService onlineService;

    @Autowired
    public void setUserJpaDao(UserJpaDao userJpaDao)
    {
        this.userJpaDao = userJpaDao;
    }

    @Autowired
    public OnlineService getOnlineService()
    {
        return onlineService;
    }

    @Override
    public void chatToGroupUser(Long desGroupId, String content)
    {

    }

    @Override
    public void chatToSingleUser(UserConnectSession fromUserSession, Long desUserId,
                                 String content)
    {
        ResponseChatToUserMsg.Builder builder = ResponseChatToUserMsg.newBuilder();
        builder.setContent(content);
        builder.setPushToUserId(fromUserSession.getUser().getUserId());

        ResponseChatToUserMsg msgFromUser = builder.build();
        ResponseChatToUserMsg msgToUser = builder.build();

    }
}
