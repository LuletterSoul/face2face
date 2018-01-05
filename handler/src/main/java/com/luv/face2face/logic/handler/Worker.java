package com.luv.face2face.logic.handler;

import org.apache.thrift.TException;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:05 2018/1/5.
 * @since luv-face2face
 */

public class Worker extends Thread
{
    private IMHandler handler;

    public Worker(IMHandler handler)
    {
        this.handler = handler;
    }

    @Override
    public void run()
    {
        try {
            handler.execute();
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
