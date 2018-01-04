package com.luv.face2face.auth.utils;

import com.luv.face2face.auth.handler.AuthServerHandler;
import io.netty.buffer.ByteBuf;
import com.luv.face2face.protobuf.ParseRegistryMap;
import com.luv.face2face.protobuf.Utils;
import com.luv.face2face.protobuf.generate.cli2srv.login.Auth;
import com.luv.face2face.protobuf.generate.internal.Internal;

/**
 * Created by win7 on 2016/3/3.
 */
public class RouteUtil {
    public static void sendResponse(int code, String desc, long netId,String userId) {
        Auth.SResponse.Builder sb = Auth.SResponse.newBuilder();
        sb.setCode(code);
        sb.setDesc(desc);

        ByteBuf byteBuf = Utils.pack2Server(sb.build(), ParseRegistryMap.SRESPONSE, netId, Internal.Dest.Client, userId);
        AuthServerHandler.getGateAuthConnection().writeAndFlush(byteBuf);
    }
}
