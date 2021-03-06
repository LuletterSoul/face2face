package com.luv.face2face.protobuf.code;


import com.google.protobuf.Message;
import com.luv.face2face.protobuf.analysis.ParseMap;
import com.luv.face2face.protobuf.analysis.ParserManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;


/**
 * Created by Administrator on 2016/1/29.
 */
public class PacketDecoder extends ByteToMessageDecoder
{
    private static final Logger logger = LoggerFactory.getLogger(PacketDecoder.class);

    private ParserManager parserManager;

    public void setParserManager(ParserManager parserManager)
    {
        this.parserManager = parserManager;
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
        throws Exception
    {

        in.markReaderIndex();

        if (in.readableBytes() < 4)
        {
            logger.info("readableBytes length less than 4 bytes, ignored");
            in.resetReaderIndex();
            return;
        }

        int length = in.readInt();

        if (length < 0)
        {
            ctx.close();
            logger.error("message length less than 0, channel closed");
            return;
        }

        if (length > in.readableBytes() - 4)
        {
            // 注意！编解码器加这种in.readInt()日志，在大并发的情况下很可能会抛数组越界异常！
            // logger.error("message received is incomplete,ptoNum:{}, length:{}, readable:{}",
            // in.readInt(), length, in.readableBytes());
            in.resetReaderIndex();
            return;
        }

        int ptoNum = in.readInt();

        ByteBuf byteBuf = Unpooled.buffer(length);

        in.readBytes(byteBuf);

        try
        {
            /*
             * 解密消息体 ThreeDES des = ctx.channel().attr(ClientAttr.ENCRYPT).get(); byte[] bareByte =
             * des.decrypt(inByte);
             */

            byte[] body = byteBuf.array();

            if (parserManager == null) {
                throw new NullPointerException();
            }
            Message msg = parserManager.getMessage(ptoNum, body);
            out.add(msg);
            logger.info("GateServer Received Message: content length {}, ptoNum: {}", length,
                ptoNum);

        }
        catch (Exception e)
        {
            logger.error(ctx.channel().remoteAddress() + ",decode failed.", e);
        }
    }

}
