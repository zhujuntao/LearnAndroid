package com.test.learn.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MessengerService extends Service {

    /**
     * 用于接收从客户端传递过来的数据
     */
    public class IncomingHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {

            }
        }
    }

    /**
     * 创建Messenger并传入Handler实例对象
     */

    final Messenger messenger = new Messenger(new IncomingHandler());


    /**
     * 当绑定Service时,该方法被调用,将通过mMessenger返回一个实现 * IBinder接口的实例对象
     */

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("MessengerService", "Service is invoke onBind");
        return messenger.getBinder();
    }


}
