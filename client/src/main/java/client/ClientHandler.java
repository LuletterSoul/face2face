package client;


import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message;
import com.luv.face2face.message.ResponseCode;
import com.luv.face2face.protobuf.Utils;
import com.luv.face2face.protobuf.generate.cli2srv.chat.Chat;
import com.luv.face2face.protobuf.generate.cli2srv.login.Auth;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;


/**
 * Created by Dell on 2016/2/15. 模拟客户端聊天：自己给自己发消息
 */
public class ClientHandler extends SimpleChannelInboundHandler<Message>
{
    public static ChannelHandlerContext _gateClientConnection;

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    String nickname = "";

    Long userId;

    boolean _verify = false;

    private static int count = 0;

    public static AtomicLong increased = new AtomicLong(1);

    @Override
    public void channelActive(ChannelHandlerContext ctx)
        throws IOException
    {
        _gateClientConnection = ctx;
        String password = "123";
        nickname = Long.toString(increased.getAndIncrement());
        sendCRegister(ctx, nickname, password);
        sendCLogin(ctx, userId, password);
    }

    void sendCRegister(ChannelHandlerContext ctx, String nickname, String password)
    {
//        Auth.CRegister.Builder cb = Auth.CRegister.newBuilder();
        Auth.RequestUserRegisterMsg.Builder builder = null;
        try {
            builder = Auth.RequestUserRegisterMsg.newBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.setNickname(nickname);
        builder.setPassword(password);
        builder.setSex("男");
        builder.setSignature("曾静沧海难为水");
        // cb.setUserid(userid);
        // cb.setPasswd(passwd);

        ByteBuf byteBuf = Utils.pack2Client(builder.build());
        ctx.writeAndFlush(byteBuf);
        logger.info("send CRegister nickname:{}", nickname);
    }

    void sendCLogin(ChannelHandlerContext ctx, Long userId, String password)
    {
        // Auth.CLogin.Builder loginInfo = Auth.CLogin.newBuilder();
        // loginInfo.setUserid(userid);
        // loginInfo.setPasswd(passwd);
        // loginInfo.setPlatform("ios");
        // loginInfo.setAppVersion("1.0.0");

        Auth.RequestLoginMsg.Builder builder = Auth.RequestLoginMsg.newBuilder();
        builder.setUserId(userId);
        builder.setPassword(password);
        ByteBuf byteBuf = Utils.pack2Client(builder.build());
        ctx.writeAndFlush(byteBuf);
        logger.info("send CLogin userid:{}", nickname);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
        throws Exception
    {
        logger.info("received message: {}", msg.getClass());
        if (msg instanceof Auth.ResponseMsg)
        {
            Auth.ResponseMsg sp = (Auth.ResponseMsg)msg;
            int code = sp.getCode();
            String desc = sp.getDescription();
            switch (code)
            {
                // 登录成功
                case ResponseCode.LOGIN_SUCCESS:
                    logger.info("Login succeed, description: {}", desc);
                    _verify = true;
                    break;
                // //登录账号不存在
                // case Common.ACCOUNT_INEXIST:
                // logger.info("Account inexsit, description: {}", desc);
                // break;
                // 登录失败
                case ResponseCode.LOGIN_FAILED:
                    logger.info("Account or passwd Error, description: {}", desc);
                    break;
                // //账号已被注册
                // case Common.ACCOUNT_DUMPLICATED:
                // logger.info("Dumplicated registry, description: {}", desc);
                // break;
                // 注册成功
                case ResponseCode.REGISTER_SUCCESS:
                    logger.info("User registerd successd, description: {}", desc);
                    userId = Long.valueOf(sp.getOptionalContent());
                    break;
                // case ResponseCode.S:
                // logger.info("Chat Message Send Successed, description: {}", desc);
                default:
                    logger.info("Unknown code: {}", code);
            }
        }
        // else if(msg instanceof Chat.SPrivateChat) {
        // logger.info("{} receiced chat message: {}.Total:{}", nickname, ((Chat.SPrivateChat)
        // msg).getContent(), ++count);
        // }

        // 这样设置的原因是，防止两方都阻塞在输入上
        if (_verify)
        {
            sendMessage();
            Thread.sleep(Client.frequency);
        }
    }

    void sendMessage()
    {
        // logger.info("WelCome To Face2face Chat Room, You Can Say Something Now: ");
        // Scanner sc = new Scanner(System.in);
        // String content = sc.nextLine();
        String content = "Hello, I am Tom!";
        // logger.info("{} Send Message: {} to {}", nickname, content, _friend);

        // Chat.CPrivateChat.Builder cp = Chat.CPrivateChat.newBuilder();
        Chat.RequestChatToUserMsg.Builder builder = Chat.RequestChatToUserMsg.newBuilder();
        builder.setChatFromUserId(userId);
        builder.setChatToUserId(userId);
        builder.setContent(content);
        // cp.setContent(content);
        // cp.setSelf(nickname);
        // cp.setDest(nickname);

        ByteBuf byteBuf = Utils.pack2Client(builder.build());
        _gateClientConnection.writeAndFlush(byteBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
    {
        // ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Message msg)
        throws Exception
    {

    }
}
