package com.luv.face2face.logic.handler.upload;


import com.google.protobuf.Message;
import com.luv.face2face.logic.handler.AbstractIMHandlerImpl;
import com.luv.face2face.protobuf.generate.ser2cli.file.Server;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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
        log.info("Promise upload request.Prepare file receive operation.");
        getUserService().sendUploadFilePromise(msg.getFormUserId(), msg.getLocalPath());
        try
        {
            channelHandlerContext.channel().pipeline().addFirst(new ChunkedReadHandler(msg));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
