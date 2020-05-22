package com.example.learnandroid.activity.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.learnandroid.base.BaseActivity;
import com.example.learnandroid.service.MessengerService;

public class MessengerServiceTest extends BaseActivity {

    /**
     * 与服务端交互的Messenger
     */
    Messenger mService = null;
    boolean mBound;

    /**
     * 实现与服务端链接的对象
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {

        /** *
         * 通过服务端传递的IBinder对象,创建相应的Messenger * 通过该Messenger对象与服务端进行交互
         *
         */

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindService(new Intent(MessengerServiceTest.this, MessengerService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void sayHello(View view) {

        if (!mBound) return;
        // 创建与服务交互的消息实体Message
        Message message = Message.obtain(null, 1, 0, 0);


        try {
            //发送消息
            mService.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();

        }

    }
}