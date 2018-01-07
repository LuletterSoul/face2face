package com.luv.face2face.logic.dispatcher;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.google.protobuf.Message;
import com.luv.face2face.logic.handler.IMHandler;
import com.luv.face2face.logic.handler.manager.HandlerManager;
import com.luv.face2face.logic.task.ChannelTask;
import com.luv.face2face.logic.task.ChannelTaskImpl;
import com.luv.face2face.protobuf.analysis.ParserManager;
import com.luv.face2face.service.session.ChannelUtils;
import com.luv.face2face.service.session.UserConnectSession;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 12:33 2018/1/5.
 * @since luv-face2face
 */

@Component(value = "logicDispatcher")
@Slf4j
@Getter
@Setter
@ChannelHandler.Sharable
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultServerLogicDispatcher extends ChannelHandlerAdapter implements LogicServerDispatcher
{
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private ParserManager parserManager;

    private HandlerManager handlerManager;

    /**
     * 信道激活出发的时间 加入“每个信道生成一个会话自定义逻辑”
     * 
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx)
        throws Exception
    {
        if (!ChannelUtils.addChannelSession(ctx.channel(), new UserConnectSession(ctx.channel())))
        {
            ctx.channel().close();
            log.error("Duplicate session,IP=[{}]", ChannelUtils.getIp(ctx.channel()));
        }
        // 将信号继续往后传递
        super.channelActive(ctx);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise)
        throws Exception
    {
        super.close(ctx, promise);
    }

    @Override
    public void dispatch(ChannelHandlerContext context, Message message, Integer protocolNum)
    {
        // 获取对应的处理器
        IMHandler handler = handlerManager.getHandler(message);
        // 封装一个简单的线程任务
        ChannelTask task = new ChannelTaskImpl(handler, message, context);
        // 将业务分配给线程池的一个线程执行
        threadPoolTaskExecutor.execute(task);
    }

    /**
     * 信道读触发的事件
     * 
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
        throws Exception
    {
//        Internal.GTransfer gt = (Internal.GTransfer)msg;
//        // 提取协议号
//        Integer protocolNum = gt.getPtoNum();
//        // 解析和转化任务交给对应的解析器
//        Message message = parserManager.getMessage(protocolNum, gt.getMsg().toByteArray());
        // 由分发器分发给对应的消息处理器
        this.dispatch(ctx, (Message) msg, 2);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }


}
