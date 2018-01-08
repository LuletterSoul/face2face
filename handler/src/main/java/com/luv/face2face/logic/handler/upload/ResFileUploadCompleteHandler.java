package com.luv.face2face.logic.handler.upload;

import com.google.protobuf.Message;
import com.luv.face2face.logic.handler.AbstractIMHandlerImpl;
import com.luv.face2face.protobuf.generate.ser2cli.file.Server;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.luv.face2face.protobuf.generate.ser2cli.file.Server.*;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  23:39 2018/1/8.
 * @since face2face
 */

@Component
@Slf4j
public class ResFileUploadCompleteHandler extends AbstractIMHandlerImpl{

    @Override
    public void execute(ChannelHandlerContext channelHandlerContext, Message message) {
        notifyFileReady((ResFileUploadComplete) message);
    }

    private void notifyFileReady(ResFileUploadComplete message) {
        getUserService().notifyFileReady(message);
        log.debug("Server send file ready message:[{}]", message.toString());
    }
}
