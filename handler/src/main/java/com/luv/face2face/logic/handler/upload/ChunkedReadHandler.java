package com.luv.face2face.logic.handler.upload;


import com.luv.face2face.service.util.FileUtils;
import com.luv.face2face.service.util.ProgressBar;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static com.luv.face2face.protobuf.generate.ser2cli.file.Server.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:01 2018/1/8.
 * @since face2face
 */

@Slf4j
@AllArgsConstructor
public class ChunkedReadHandler extends ChannelHandlerAdapter
{
    private long fileSize;

    private ReqFileUploadMsg uploadMsg;

    private File uploadFile;

    private String serverFilePath;

    private FileOutputStream ofs;

    private long receivedSize = 0;

    private ProgressBar progressBar;

    public ChunkedReadHandler(ReqFileUploadMsg msg)
        throws FileNotFoundException
    {
        this.fileSize = msg.getFileLength();
        this.uploadMsg = msg;
        progressBar = new ProgressBar(fileSize, 100);
        initFile(msg);
    }

    private void initFile(ReqFileUploadMsg msg)
        throws FileNotFoundException
    {
        String fileName = msg.getFileName();
        String relativePath = "/users" + "/" + msg.getFormUserId();
        log.info("Relative path is ------------>[{}]", relativePath);
        serverFilePath =  FileUtils.getAbsolutePath(relativePath)+ "/" + fileName;
        ofs = new FileOutputStream(serverFilePath);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
        throws Exception
    {
        ByteBuf buf = (ByteBuf)msg;
        receivedSize += buf.readableBytes();
        if (buf.isReadable())
        {
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            ofs.write(bytes);
        }
//        log.info("Received size:[{}]/[{}]", receivedSize, fileSize);
        // 接收的字节数大于等于实际大小,说明接收完成
        progressBar.showBarByPoint(receivedSize);
        if (receivedSize >= fileSize)
        {
            ctx.pipeline().remove(this);
            ofs.close();
        }
        buf.release();
    }
}
