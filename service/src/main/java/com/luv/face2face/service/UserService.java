package com.luv.face2face.service;


import com.google.protobuf.Message;
import com.luv.face2face.domain.User;
import com.luv.face2face.protobuf.generate.ser2cli.file.Server;
import io.netty.channel.Channel;

import static com.luv.face2face.protobuf.generate.ser2cli.file.Server.*;


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

    void cacheUserUploadMsg(long userId, ReqFileUploadMsg msg);

    ReqFileUploadMsg getUserUploadMsg(long userId);

    void sendUploadFilePromise(Long userId,String path);

}
