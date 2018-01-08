package com.luv.face2face.service.impl;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv.face2face.repository.FriendJpaDao;
import com.luv.face2face.repository.GroupViewJpaDao;
import com.luv.face2face.repository.RemarkJpaDao;
import com.luv.face2face.repository.UserJpaDao;
import com.luv.face2face.service.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 0:21 2018/1/8.
 * @since face2face
 */

@Service
@Transactional
public abstract class AbstractService implements BaseService
{
    @Autowired
    protected UserJpaDao userJpaDao;

    @Autowired
    protected FriendJpaDao friendJpaDao;

    @Autowired
    protected RemarkJpaDao remarkJpaDao;

    @Autowired
    protected GroupViewJpaDao groupViewJpaDao;

    @Autowired
    protected OnlineService onlineService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected ChatService chatService;

    @Autowired
    protected LoginService loginService;

    @Autowired
    protected FriendService friendService;

}
