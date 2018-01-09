package com.luv.face2face.logic.handler.download;


import com.google.protobuf.Message;
import com.luv.face2face.logic.handler.AbstractIMHandlerImpl;
import com.luv.face2face.protobuf.generate.ser2cli.file.Server;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.luv.face2face.protobuf.generate.ser2cli.file.Server.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 11:23 2018/1/9.
 * @since face2face
 */

@Slf4j
@Component
public class ResFileDownloadCompleteHandler extends AbstractIMHandlerImpl
{
    @Override
    public void execute(ChannelHandlerContext channelHandlerContext, Message message)
    {
        releaseChunkedHandler(channelHandlerContext);
        ResFileDownloadComplete complete = (ResFileDownloadComplete)message;
        ReqFileDownloadMsg downloadMsg = complete.getFileDownloadMsg();
        Long sourceUserId = downloadMsg.getSourceUserId();
        getUserService().notifyFileReceived(sourceUserId, complete);
        log.info("notify file uploader [{}] that file downloaded by user[{}]", sourceUserId,
            complete, downloadMsg.getFormUserId());
    }

    private void releaseChunkedHandler(ChannelHandlerContext channelHandlerContext)
    {
        ChannelPipeline pipeline = channelHandlerContext.channel().pipeline();
        ChannelHandler handler = pipeline.get("chunkedWriteHandler");
        if (handler != null)
        {
            pipeline.remove(handler);
        }
    }
}
