package com.luv.face2face.service;


import com.luv.face2face.auth.domain.User;
import com.luv.face2face.service.session.SessionCloseReason;
import io.netty.channel.Channel;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 10:58 2018/1/6.
 * @since luv-face2face
 */

public interface LoginService
{
    /**
     * 用户登录
     * @param channel 用户信道
     * @param userId 用户id
     * @param password 密码
     */
    void loginUser(Channel channel, Long userId,String password);

    /**
     * 用户登出
     * @param channel
     * @param reason 登出原因
     */
    void logoutUser(Long userId,Channel channel, SessionCloseReason reason);
}
