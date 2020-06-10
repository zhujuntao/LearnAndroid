package com.test.learn.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.test.learn.MainActivity;
import com.test.learn.R;

public class MyService extends Service {

    private DownloadBinder mBinder = new DownloadBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //在服务创建的时候调用

        Log.d("MyService", "onCreate executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //每次服务启动的时候调用
        Log.d("MyService", "onStartCommand executed");

        new Thread(new Runnable() {
            @Override
            public void run() { // 处理具体的逻辑
                //调用 stopService()或者 stopSelf()方法才能让服务停止下来
                stopSelf();
            }
        }).start();


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //服务销毁的时候 调用。
        Log.d("MyService", "onDestroy executed");
    }


    public class DownloadBinder extends Binder {


        public void startDownload() {
            Log.d("MyService", "startDownload executed");
        }

        public int getProgress() {
            Log.d("MyService", "getProgress executed");
            return 0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void startNotification() {
        //1 创建NotificationManager
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // 创建Notification.Builder，使用的是建造者模式

        Notification.Builder builder = new Notification.Builder(this.getApplicationContext())
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)//通知发出的时候播放一段音频
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker("设置状态栏标题")//设置状态栏标题
                .setContentTitle("这个是标题")//这个是标题
                .setContentText("内容")
                .setDefaults(Notification.DEFAULT_ALL)//设置默认提示音
                .setPriority(Notification.PRIORITY_DEFAULT)//设置优先级
                .setOngoing(false)//设置让通知左右滑动不能删除
                .setAutoCancel(true);//设置打开程序后通知自动消失

//        3 创建PendingIntent，处理点击通知之后的逻辑

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//添加为栈顶activity
        intent.putExtra("what", 3);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//        4 给Notification.Builder设置PendingIntent,然后发送通知
        builder.setContentIntent(pi);
        //mNotificationManager.notify(1, builder.build());
        //调用 startForeground()方法后就会让 MyService 变成 一个前台服务，并在系统状态栏显示出来。
        startForeground(1, builder.build());


    }

}
