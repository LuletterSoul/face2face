package com.luv.face2face.service.impl;


import com.google.protobuf.Message;
import com.luv.face2face.auth.domain.User;
import com.luv.face2face.repository.UserJpaDao;
import com.luv.face2face.service.UserService;
import com.luv.face2face.service.session.ChannelUtils;
import com.luv.face2face.service.session.SessionCloseReason;
import com.luv.face2face.service.session.UserConnectSession;
import com.luv.face2face.service.util.ConcurrentHashSet;
import com.luv.face2face.service.util.LruHashMap;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

import static com.luv.face2face.protobuf.generate.cli2srv.login.Auth.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:44 2018/1/5.
 * @since luv-face2face
 */

@Service
public class UserServiceImpl implements UserService
{
    private UserJpaDao userJpaDao;

    /** 缓存最近登录的所有用户 */
    private Map<Long, User> lruUsers = new LruHashMap<>(1000);
    /** 在线用户列表　*/
    private Set<Long> onlineUsers = new ConcurrentHashSet<>();

    @Autowired
    public void setUserJpaDao(UserJpaDao userJpaDao) {
        this.userJpaDao = userJpaDao;
    }

    @Override
    public void addUser2Online(long userId)
    {
        this.onlineUsers.add(userId);
    }

    @Override
    public void removeFromOnline(long userId)
    {
        this.onlineUsers.add(userId);
    }

    @Override
    public boolean isOnlineUser(long userId)
    {
        return this.onlineUsers.contains(userId);
    }

    @Override
    public void registerNewAccount(Message registerMessage, Channel channel) {
        RequestUserRegisterMsg msg = (RequestUserRegisterMsg) registerMessage;
        UserConnectSession session = ChannelUtils.getSessionBy(channel);
        User newUser = new User();
        newUser.setNickname(msg.getNickname());
        newUser.setSex(msg.getSex());
        newUser.setSignature(msg.getSignature());
        newUser.setPassword(msg.getPassword());
        userJpaDao.save(newUser);
        ResponseUserStatusChangeMsg.Builder builder = ResponseUserStatusChangeMsg.newBuilder();
        builder.setCode();
    }




    @Override
    public User createNewUser(User user)
    {
        return null;
    }

    @Override
    public void refreshUserProfile(User user)
    {

    }

    @Override
    public void userLogout(Channel channel, SessionCloseReason reason)
    {

    }
}
