package com.luv.face2face.service;

import com.luv.face2face.service.session.UserConnectSession;
import io.netty.channel.Channel;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 20:00 2018/1/5.
 * @since luv-face2face
 */

public interface ChatService
{
    void chatToGroupUser(Long desGroupId, String content);

    void chatToSingleUser(Channel channel,Long fromUserId, Long desUserId, String content);
}
