package com.luv.face2face.service;


import com.google.protobuf.Message;
import com.luv.face2face.domain.User;
import io.netty.channel.Channel;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:25 2018/1/5.
 * @since luv-face2face
 */

public interface UserService extends BaseService
{

    void addUser2Online(long userId);

    void removeFromOnline(long userId);

    boolean isOnlineUser(long userId);

    void registerNewAccount(User user, Channel channel);

    User createNewUser(User user);

    void refreshUserProfile(User user);

}
