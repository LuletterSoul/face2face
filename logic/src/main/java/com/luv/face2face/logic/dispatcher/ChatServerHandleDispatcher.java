package com.luv.face2face.logic.dispatcher;


import com.luv.face2face.logic.HandlerManager;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 12:33 2018/1/5.
 * @since luv-face2face
 */

@Component
public class ChatServerHandleDispatcher extends ChannelHandlerAdapter implements IChatServerDispatcher
{
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Override
    public void delegate(ChannelHandlerContext context, Object msg)
    {
        threadPoolTaskExecutor.execute();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }
}
