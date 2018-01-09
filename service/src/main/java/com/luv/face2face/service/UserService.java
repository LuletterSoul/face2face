package com.luv.face2face.service;


import com.google.protobuf.Message;
import com.luv.face2face.domain.User;
import com.luv.face2face.protobuf.generate.ser2cli.file.Server;
import io.netty.channel.Channel;

import java.io.File;

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


    /**
     * 更新用户
     * @param user
     */

    void refreshUserProfile(User user);


    /**
     * 缓存上传信息
     * @param userId
     * @param msg
     */

    void cacheUserUploadMsg(long userId, ReqFileUploadMsg msg);

    /**
     * 获取用户上传的信息
     * @param userId
     * @return
     */

    ReqFileUploadMsg getUserUploadMsg(long userId);

    /**
     * 发送文件上传的准允
     * @param userId
     * @param path
     */
    void sendUploadFilePromise(Long userId,String path);

    /**
     * 通知用户文件就绪
     * @param message
     */
    void notifyFileReady(ResFileUploadComplete message);

    /**
     * 发送下载的内容信息
     * @param resFileDownloadMsg
     */
    void sendDownloadPromise(ResFileDownloadMsg resFileDownloadMsg,Long userId);

    void sendFile(File file, Long toUserId);

    void notifyFileReceived(Long userId, ResFileDownloadComplete complete);

}
