package com.luv.face2face.service.impl;


import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.luv.face2face.domain.FriendView;
import com.luv.face2face.domain.User;
import com.luv.face2face.protobuf.generate.ser2cli.friend.Server.FriendItemVo;
import com.luv.face2face.protobuf.generate.ser2cli.friend.Server.ResFriendLogin;
import com.luv.face2face.protobuf.generate.ser2cli.friend.Server.ResFriendLogout;
import com.luv.face2face.protobuf.generate.ser2cli.friend.Server.ResListFriends;
import com.luv.face2face.service.FriendService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class FriendServiceImpl extends AbstractService implements FriendService
{

//    @Autowired
//    private FriendJpaDao friendJpaDao;
//
//    @Autowired
//    private UserJpaDao userJpaDao;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private OnlineService onlineService;

    @Override
    public ResListFriends listMyFriends(User user)
    {
        ResListFriends.Builder builder = ResListFriends.newBuilder();
        FriendItemVo.Builder friendItemBuilder = FriendItemVo.newBuilder();
        List<FriendView> friendViews = friendJpaDao.findByFriend(user);
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
        onlineService.getOnlineUserSessionById(user.getUserId()).sendPacket(
            listMyFriends(user));
        onUserLogin(user);
    }

    @Override
    public void onUserLogin(User user)
    {
        List<FriendView> friendViews = friendJpaDao.findByFriend(user);
        ResFriendLogin.Builder builder = ResFriendLogin.newBuilder();
        builder.setFriendId(user.getUserId());
        for (FriendView friend : friendViews)
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
        List<FriendView> friendViews = friendJpaDao.findByFriend(user);
        ResFriendLogout.Builder builder = ResFriendLogout.newBuilder();
        builder.setFriendId(user.getUserId());
        for (FriendView friend : friendViews)
        {
            long friendId = friend.getUserId();
            if (userService.isOnlineUser(friendId))
            {
                onlineService.getOnlineUserSessionById(friendId).sendPacket(builder.build());
            }
        }
    }

}
