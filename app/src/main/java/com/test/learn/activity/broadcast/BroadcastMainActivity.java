package com.test.learn.activity.broadcast;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.test.learn.R;
import com.test.learn.base.BaseActivity;
import com.test.learn.broadcastreceiver.network.NetworkChangeReceiver;
import com.test.learn.broadcastreceiver.standard.LocalReceiver;

public class BroadcastMainActivity extends BaseActivity {

    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;


    private IntentFilter localIntentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_main);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);

        //发送广播
        //自定义广播
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent("com.example.learnandroid.broadcastreceiver.standard.MY_BROADCAST");
                //发送标准广播
                //sendBroadcast(intent);
                //发送有序广播
                sendOrderedBroadcast(intent, null);


                //发送本地广播
                Intent intent_local = new Intent("com.example.learnandroid.broadcastreceiver.standard.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent_local);

            }
        });


        /*
        * 本地广播是无法通过静态注册的方式来接收的。其实这也完全 可以理解，
        * 因为静态注册主要就是为了让程序在未启动的情况下也能收到广播，而发送本地
        * 广播时，我们的程序肯定是已经启动了，因此也完全不需要使用静态注册的功能。
        *
        * 最后我们再来盘点一下使用本地广播的几点优势吧。
          1. 可以明确地知道正在发送的广播不会离开我们的程序，因此不需要担心机密数据泄 漏的问题。
          2. 其他的程序无法将广播发送到我们程序的内部，因此不需要担心会有安全漏洞的隐 患。
          3. 发送本地广播比起发送系统全局广播将会更加高效。
        * */
        //本地广播
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("com.example.learnandroid.broadcastreceiver.standard.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        //注册本地广播
        localBroadcastManager.registerReceiver(localReceiver, localIntentFilter);

        //发送强制下线广播
        Button forceOffline = (Button) findViewById(R.id.force_offline);
        forceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.learnandroid.broadcastreceiver.standard.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
}
