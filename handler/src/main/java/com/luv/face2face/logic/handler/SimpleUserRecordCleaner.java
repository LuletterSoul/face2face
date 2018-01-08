package com.luv.face2face.logic.handler;


import com.google.protobuf.Message;
import com.luv.face2face.service.UserService;
import com.luv.face2face.service.session.ChannelUtils;
import com.luv.face2face.service.session.SessionCloseReason;
import com.luv.face2face.service.session.UserConnectSession;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 12:08 2018/1/8.
 * @since face2face
 */

/**
 * 负责远程终端的资源回收
 */
@Component("userRecordCleaner")
@Slf4j
public class SimpleUserRecordCleaner extends AbstractIMHandlerImpl implements UserRecordCleaner
{

    @Override
    public void execute(ChannelHandlerContext channelHandlerContext, Message message)
    {
        super.execute(channelHandlerContext, message);
        this.clean(channelHandlerContext);
    }

    @Override
    public void clean(ChannelHandlerContext channelHandlerContext) {
        UserConnectSession session = ChannelUtils.getSessionBy(channelHandlerContext.channel());
//        getOnlineService().unregisterSession(session.getUser().getUserId(),
//                                                channelHandlerContext.channel() ,
//                                                SessionCloseReason.FORCE_DISCONNECT);
        getLoginService().logoutUser(session.getUser().getUserId(),
                channelHandlerContext.channel() ,
                SessionCloseReason.FORCE_DISCONNECT);
        log.info("Clean session record.session:[{}]............", session);

    }
}
