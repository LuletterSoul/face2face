package com.luv.face2face.logic.handler.download;


import com.google.protobuf.Message;
import com.luv.face2face.logic.handler.AbstractIMHandlerImpl;
import com.luv.face2face.protobuf.generate.ser2cli.file.Server;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.luv.face2face.protobuf.generate.ser2cli.file.Server.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 10:55 2018/1/9.
 * @since face2face
 */

@Slf4j
@Component
public class ReqFileDownloadHandler extends AbstractIMHandlerImpl
{
    @Override
    public void execute(ChannelHandlerContext channelHandlerContext, Message message)
    {
        ReqFileDownloadMsg msg = (ReqFileDownloadMsg) message;
        Long sourceUserId = msg.getSourceUserId();
        ReqFileUploadMsg uploadMsg = getUserService().getUserUploadMsg(sourceUserId);
        if (uploadMsg.getFormUserId() != sourceUserId) {
            log.info("could match file upload info.");
            return;
        }
        //服务器的文件地址
        String absolutePath = msg.getFilePath();
        ChunkedWriteHandler chunkedWriteHandler = new ChunkedWriteHandler();
        ChannelPipeline pipeline = channelHandlerContext.channel().pipeline();
        pipeline.addFirst("chunkedWriteHandler", chunkedWriteHandler);
        getUserService().sendFile(new File(absolutePath), msg.getFormUserId());
        chunkedWriteHandler.resumeTransfer();
    }
}
