package com.luv.face2face.service;


import com.luv.face2face.auth.domain.User;
import com.luv.face2face.protobuf.generate.cli2srv.login.Auth;
import com.luv.face2face.service.session.SessionCloseReason;
import io.netty.channel.Channel;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:25 2018/1/5.
 * @since luv-face2face
 */

public interface UserService
{

    void addUser2Online(long userId);

    void removeFromOnline(long userId);

    boolean isOnlineUser(long userId);

    void registerNewAccount(Auth.RequestUserRegisterMsg requestUserRegisterMsg);

    User createNewUser(User user);

    void refreshUserProfile(User user);

    void userLogout(Channel channel, SessionCloseReason reason);

}
