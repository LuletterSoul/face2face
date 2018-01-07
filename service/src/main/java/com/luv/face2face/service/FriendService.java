package com.luv.face2face.service;

import com.luv.face2face.domain.User;
import com.luv.face2face.protobuf.generate.ser2cli.friend.Server;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  23:22 2018/1/7.
 * @since face2face
 */

public interface FriendService extends BaseService {
    Server.ResListFriends listMyFriends(User user);

    void refreshUserFriends(User user);

    void onUserLogin(User user);

    void onUserLogout(User user);
}
