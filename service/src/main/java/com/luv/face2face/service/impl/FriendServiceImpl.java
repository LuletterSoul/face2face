package com.luv.face2face.service.impl;


import java.util.List;
import java.util.Set;

import com.luv.face2face.domain.FriendGroupView;
import com.luv.face2face.domain.Remark;
import org.springframework.stereotype.Service;

import com.luv.face2face.domain.User;
import com.luv.face2face.protobuf.generate.ser2cli.friend.Server.FriendItemVo;
import com.luv.face2face.protobuf.generate.ser2cli.friend.Server.ResFriendLogin;
import com.luv.face2face.protobuf.generate.ser2cli.friend.Server.ResFriendLogout;
import com.luv.face2face.protobuf.generate.ser2cli.friend.Server.ResListFriends;
import com.luv.face2face.service.FriendService;

import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;


@Service
@Slf4j
@Transactional
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
//        List<FriendView> friendViews = friendJpaDao.findByFriend(user);
        //查出所有的好友
        List<User> friendViews = friendJpaDao.findByUser(user);
//        //查出所有的分组
//        List<FriendGroupView> friendGroupViews = groupViewJpaDao.findByOwner(user);
        for (User f : friendViews)
        {
            //查出好友所在的分组
            FriendGroupView friendGroupView = groupViewJpaDao.findByOwnerAndFAndMembers(user, f.getUserId());
            //查出对好友的备注
            Remark remark = remarkJpaDao.findByMarkerAndAndBioMarker(user, f);
            friendItemBuilder.setGroupId(friendGroupView.getGroupViewId());
            friendItemBuilder.setRemark(remark.getMarkContent());
            friendItemBuilder.setGroupName(friendGroupView.getGroupName());
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
        List<User> friendViews = friendJpaDao.findByUser(user);
        ResFriendLogin.Builder builder = ResFriendLogin.newBuilder();
        builder.setFriendId(user.getUserId());
        for (User friend : friendViews)
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
        List<User> friendViews = friendJpaDao.findByUser(user);
        ResFriendLogout.Builder builder = ResFriendLogout.newBuilder();
        builder.setFriendId(user.getUserId());
        for (User friend : friendViews)
        {
            long friendId = friend.getUserId();
            if (userService.isOnlineUser(friendId))
            {
                onlineService.getOnlineUserSessionById(friendId).sendPacket(builder.build());
            }
        }
    }

}
