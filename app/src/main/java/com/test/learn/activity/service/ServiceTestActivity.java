package com.test.learn.activity.service;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.test.learn.R;
import com.test.learn.base.BaseActivity;
import com.test.learn.service.MyIntentService;
import com.test.learn.service.MyService;

public class ServiceTestActivity extends BaseActivity implements View.OnClickListener {
    private Button startService;
    private Button stopService;

    private Button bindService;
    private Button unbindService;
    private MyService.DownloadBinder downloadBinder;


    private Button startIntentService;

    private ServiceConnection connection = new ServiceConnection() {

        //活动与服务 成功绑定
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        //以及解除绑定的时候调用
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
        startService = (Button) findViewById(R.id.start_service);
        stopService = (Button) findViewById(R.id.stop_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService = (Button) findViewById(R.id.bind_service);
        unbindService = (Button) findViewById(R.id.unbind_service);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);

        startIntentService = (Button) findViewById(R.id.start_intent_service);
        startIntentService.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service:
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent); // 启动服务
                break;
            case R.id.stop_service:
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent); // 停止服务
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(this, MyService.class);
                /*
                 * 第一个参数就 是刚刚构建出的 Intent 对象，
                 * 第二个参数是前面创建出的 ServiceConnection 的实例，
                 * 第三个 参数则是一个标志位，这里传入 BIND_AUTO_CREATE 表示在活动和服务进行绑定后自动 创建服务。
                 * 这会使得 MyService 中的 onCreate()方法得到执行，但 onStartCommand()方法不 会执行。
                 * */
                bindService(bindIntent, connection, BIND_AUTO_CREATE); // 绑定服务 break;
            case R.id.unbind_service:
                unbindService(connection); // 解绑服务
                break;
            case R.id.start_intent_service:
                // 打印主线程的id

                Log.d("MainActivity", "Thread id is " + Thread.currentThread().getId());
                Intent intentService = new Intent(this, MyIntentService.class);
                startService(intentService);
                break;
            default:
                break;
        }
    }
}
