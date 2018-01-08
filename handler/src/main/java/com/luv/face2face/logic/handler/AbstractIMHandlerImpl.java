package com.luv.face2face.logic.handler;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:14 2018/1/5.
 * @since luv-face2face
 */

import com.google.protobuf.Message;
import com.luv.face2face.service.*;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 该类实际为一个可执行线程,传递channel异步执行任务
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AbstractIMHandlerImpl implements IMHandler
{

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private OnlineService onlineService;

    @Autowired
    private FriendService friendService;

    @Override
    public void execute(ChannelHandlerContext channelHandlerContext, Message message) {

    }
}
