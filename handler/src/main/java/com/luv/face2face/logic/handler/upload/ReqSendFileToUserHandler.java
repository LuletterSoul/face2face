package com.luv.face2face.logic.handler.upload;


import com.google.protobuf.Message;
import com.luv.face2face.logic.handler.AbstractIMHandlerImpl;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

import static com.luv.face2face.protobuf.generate.ser2cli.file.Server.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:33 2018/1/8.
 * @since face2face
 */

@Component
@Slf4j
public class ReqSendFileToUserHandler extends AbstractIMHandlerImpl
{
    @Override
    public void execute(ChannelHandlerContext channelHandlerContext, Message message)
    {
        ReqFileUploadMsg msg = (ReqFileUploadMsg)message;
        getUserService().cacheUserUploadMsg(msg.getFormUserId(), msg);
        try
        {
            channelHandlerContext.channel().pipeline().addFirst(new ChunkedServerReadHandler(msg, getUserService()));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        getUserService().sendUploadFilePromise(msg.getFormUserId(), msg.getLocalPath());
        log.info("Promise upload request.Prepare file receive operation.");
    }
}
