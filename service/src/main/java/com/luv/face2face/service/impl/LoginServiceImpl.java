package com.luv.face2face.service.impl;


import com.luv.face2face.domain.User;
import com.luv.face2face.message.ResponseCode;
import com.luv.face2face.repository.UserJpaDao;
import com.luv.face2face.service.LoginService;
import com.luv.face2face.service.OnlineService;
import com.luv.face2face.service.session.ChannelUtils;
import com.luv.face2face.service.session.SessionCloseReason;
import com.luv.face2face.service.session.UserConnectSession;
import com.luv.face2face.service.util.LruHashMap;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.luv.face2face.protobuf.generate.cli2srv.login.Auth.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 11:00 2018/1/6.
 * @since luv-face2face
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService
{
    private UserJpaDao userJpaDao;

    private OnlineService onlineService;

    /** 缓存最近登录的所有用户 */
    private Map<Long, User> lruUsers = new LruHashMap<>(1000);

    @Autowired
    public void setUserJpaDao(UserJpaDao userJpaDao)
    {
        this.userJpaDao = userJpaDao;
    }

    @Autowired
    public void setOnlineService(OnlineService onlineService)
    {
        this.onlineService = onlineService;
    }

    @Override
    public void loginUser(Channel channel, Long userId, String password)
    {
        User user = validateUser(userId, password);
        UserConnectSession userConnectSession = ChannelUtils.getSessionBy(channel);
        ResponseUserStatusChangeMsg.Builder builder = ResponseUserStatusChangeMsg.newBuilder();
        if (user == null)
        {
            builder.setCode(ResponseCode.LOGOUT_FAILED);
            builder.setDescription("账号或密码不合法.");
            userConnectSession.sendPacket(builder.build());
            return;
        }
        if (onlineService.registerSession(user, channel))
        {
            builder.setCode(ResponseCode.LOGIN_SUCCESS);
            builder.setDescription("登陆成功");
            onlineService.getOnlineUserSessionById(userId).sendPacket(builder.build());
        }
        lruUsers.put(userId, user);
    }

    @Override
    public void logoutUser(Long userId,Channel channel, SessionCloseReason reason)
    {
        onlineService.unregisterSession(userId,channel, reason);
    }

    /**
     * 验证用户信息是否一致
     * 
     * @param userId
     *            用户id
     * @param password
     *            密码
     * @return
     */
    private User validateUser(Long userId, String password)
    {
        if (userId == null || StringUtils.isEmpty(password))
        {
            return null;
        }
        User loginUser = userJpaDao.findOne(userId);
        if (loginUser != null && loginUser.getPassword().equals(password))
        {
            return loginUser;
        }
        return null;
    }
}
