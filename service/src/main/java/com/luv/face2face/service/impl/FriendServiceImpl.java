package com.luv.face2face.service.impl;


import static com.luv.face2face.protobuf.generate.ser2cli.friend.Server.*;

import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luv.face2face.domain.FriendView;
import com.luv.face2face.domain.User;
import com.luv.face2face.protobuf.generate.ser2cli.friend.Server.*;
import com.luv.face2face.repository.FriendJpaDao;
import com.luv.face2face.repository.UserJpaDao;
import com.luv.face2face.service.FriendService;
import com.luv.face2face.service.OnlineService;
import com.luv.face2face.service.UserService;


@Component
@Slf4j
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendJpaDao friendJpaDao;

    @Autowired
    private UserJpaDao userJpaDao;

    @Autowired
    private UserService userService;

    private OnlineService onlineService;

    @Override
    public ResListFriends listMyFriends(long userId)
    {
        ResListFriends.Builder builder = ResListFriends.newBuilder();
        FriendItemVo.Builder friendItemBuilder = FriendItemVo.newBuilder();
        Set<FriendView> friendViews = userJpaDao.findOne(userId).getFriendViews();
        for (FriendView f : friendViews)
        {
            friendItemBuilder.setGroupId(f.getGroupId());
            friendItemBuilder.setRemark(f.getRemark());
            friendItemBuilder.setGroupName(f.getGroupName());
            friendItemBuilder.setSex(f.getSex());
            friendItemBuilder.setSignature(f.getSignature());
            friendItemBuilder.setNickname(f.getNickname());
            friendItemBuilder.setUserId(f.getUserId());
            if (userService.isOnlineUser(f.getUserId()))
            {
                friendItemBuilder.setIsOnline(true);
            }
            else
            {
                friendItemBuilder.setIsOnline(false);
            }
            builder.addFriend(friendItemBuilder);
        }
        return builder.build();
    }

    @Override
    public void refreshUserFriends(User user)
    {
        ResListFriends myFriends = listMyFriends(user.getUserId());
        onlineService.getOnlineUserSessionById(user.getUserId()).sendPacket(
            listMyFriends(user.getUserId()));
        onUserLogin(user);
    }

    @Override
    public void onUserLogin(User user)
    {
        Set<FriendView> friends = userJpaDao.findOne(user.getUserId()).getFriendViews();
        ResFriendLogin.Builder builder = ResFriendLogin.newBuilder();
        builder.setFriendId(user.getUserId());
        for (FriendView friend : friends)
        {
            long friendId = friend.getUserId();
            if (userService.isOnlineUser(friendId))
            {
                onlineService.getOnlineUserSessionById(friendId).sendPacket(builder.build());
            }
        }
    }

    @Override
    public void onUserLogout(User user)
    {
        Set<FriendView> friends = userJpaDao.findOne(user.getUserId()).getFriendViews();
        ResFriendLogout.Builder builder = ResFriendLogout.newBuilder();
        builder.setFriendId(user.getUserId());
        for (FriendView friend : friends)
        {
            long friendId = friend.getUserId();
            if (userService.isOnlineUser(friendId))
            {
                onlineService.getOnlineUserSessionById(friendId).sendPacket(builder.build());
            }
        }
    }

}
