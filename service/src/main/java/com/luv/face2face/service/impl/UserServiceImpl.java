package com.luv.face2face.service.impl;


import com.luv.face2face.auth.domain.User;
import com.luv.face2face.protobuf.generate.cli2srv.login.Auth;
import com.luv.face2face.repository.UserJpaDao;
import com.luv.face2face.service.UserService;
import com.luv.face2face.service.session.SessionCloseReason;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:44 2018/1/5.
 * @since luv-face2face
 */

@Service
public class UserServiceImpl implements UserService
{
    private UserJpaDao userJpaDao;

    @Autowired
    public void setUserJpaDao(UserJpaDao userJpaDao) {
        this.userJpaDao = userJpaDao;
    }

    @Override
    public void addUser2Online(long userId)
    {

    }

    @Override
    public void removeFromOnline(long userId)
    {

    }

    @Override
    public boolean isOnlineUser(long userId)
    {
        return false;
    }

    @Override
    public void registerNewAccount(Auth.RequestUserRegisterMsg requestUserRegisterMsg)
    {

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
