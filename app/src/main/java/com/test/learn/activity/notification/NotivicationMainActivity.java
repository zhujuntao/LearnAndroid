package com.test.learn.activity.notification;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import com.test.learn.MainActivity;
import com.test.learn.R;
import com.test.learn.base.BaseActivity;

public class NotivicationMainActivity extends BaseActivity implements View.OnClickListener {

    private Button sendNotice;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notivication_main);
        sendNotice = (Button) findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(this);

// https://www.jianshu.com/p/3702a9c9b08a
        //1 创建NotificationManager
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        // 创建Notification.Builder，使用的是建造者模式

        Notification.Builder builder = new Notification.Builder(NotivicationMainActivity.this.getApplicationContext())
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
        mNotificationManager.notify(1, builder.build());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_notice:

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new Notification(R.drawable.ic_launcher, "This is ticker text", System.currentTimeMillis());

                // 第一个参数是 Context，这个没什么好解释的。第二个参数用于指定通知的标题内 容，
                // 下拉系统状态栏就可以看到这部分内容。第三个参数用于指定通知的正文内容，
                // 同样下 拉系统状态栏就可以看到这部分内容。第四个参数我们暂时还用不到，可以先传入 null。
                //notification.setLatestEventInfo(context, "This is content title", "This is content text", null);

                manager.notify(1, notification);



              /*Intent intent = new Intent(this, MainActivity.class);
              PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
              notification.setLatestEventInfo(this, "This is content title", "This is content text", pi);
              manager.notify(1, notification);*/


                //取消通知栏显示
//              NotificationManager manager = (NotificationManager)
//                      getSystemService(NOTIFICATION_SERVICE); manager.cancel(1);


                /*
                 * 我们可以使用 ledARGB、ledOnMS、 ledOffMS 以及 flags 这几个属性来实现这种效果。
                 * ledARGB 用于控制 LED 灯的颜色，一般 有红绿蓝三种颜色可选。
                 * ledOnMS 用于指定 LED 灯亮起的时长，以毫秒为单位。
                 * ledOffMS 用于指定 LED 灯暗去的时长，也是以毫秒为单位。
                 * flags 可用于指定通知的一些行为，其中 就包括显示 LED 灯这一选项。所以，当通知到来时，
                 * 如果想要实现 LED 灯以绿色的灯光一 闪一闪的效果，就可以写成:
                 *
                 * */
//              notification.ledARGB = Color.GREEN; notification.ledOnMS = 1000;
//              notification.ledOffMS = 1000;
//              notification.flags = Notification.FLAG_SHOW_LIGHTS;

//              可以直接使用通知的默认效果，它会根据 当前手机的环境来决定播放什么铃声，以及如何振动，写法如下:
//          notification.defaults = Notification.DEFAULT_ALL;


                break;
            default:
                break;
        }
    }
}
