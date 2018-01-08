package com.luv.face2face.service.impl;


import com.luv.face2face.domain.User;
import com.luv.face2face.service.LoginService;
import com.luv.face2face.service.session.ChannelUtils;
import com.luv.face2face.service.session.SessionCloseReason;
import com.luv.face2face.service.session.UserConnectSession;
import com.luv.face2face.service.util.LruHashMap;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.luv.face2face.protobuf.generate.ser2cli.login.Server.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 11:00 2018/1/6.
 * @since luv-face2face
 */
@Service
@Slf4j
public class LoginServiceImpl extends AbstractService implements LoginService
{
    // @Autowired
    // private UserJpaDao userJpaDao;
    //
    // @Autowired
    // private OnlineService onlineService;
    //
    // @Autowired
    // private UserService userService;
    //
    // @Autowired
    // private FriendService friendService;

    /** 缓存最近登录的所有用户 */
    private Map<Long, User> lruUsers = new LruHashMap<>(1000);

    @Override
    public void loginUser(Channel channel, Long userId, String password)
    {
        User user = validateUser(userId, password);
        if (user == null)
        {
            onLoginFailed(channel);
            return;
        }
        if (onlineService.registerSession(user, channel))
        {
            onLoginSuccess(user);
        }
        lruUsers.put(userId, user);
    }

    private void onLoginSuccess(User user)
    {
        // 添加在线列表
        userService.addUser2Online(user.getUserId());

        // 刷新用户信息

        userService.refreshUserProfile(user);

        // 刷新好友列表
        friendService.refreshUserFriends(user);
    }

    private void onLoginFailed(Channel channel)
    {
        UserConnectSession userConnectSession = ChannelUtils.getSessionBy(channel);
        ResServerLoginFailed.Builder builder = ResServerLoginFailed.newBuilder();
        builder.setDescription("账号或密码不合法.");
        userConnectSession.sendPacket(builder.build());
    }

    @Override
    public void logoutUser(Long userId, Channel channel, SessionCloseReason reason)
    {
        onlineService.unregisterSession(userId, channel, reason);
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
