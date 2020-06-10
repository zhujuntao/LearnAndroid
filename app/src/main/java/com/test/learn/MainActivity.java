package com.test.learn;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.test.learn.activity.Location.BDMapTestActivity;
import com.test.learn.base.BaseActivity;

import static android.content.ContentValues.TAG;

public class MainActivity extends BaseActivity {

    public  byte[] CHECK_BYTE_ARR = {0x32,0x46,0x3a,0x30,0x31,0x3a,0x37,0x46,0x3a,0x33,0x45,0x3a,0x37,0x32,0x3a,0x42,0x37,
            0x3a,0x43,0x31,0x3a,0x36,0x39,0x3a,0x30,0x39,0x3a,0x36,0x35,0x3a,0x30,0x37,0x3a,0x45,0x34,0x3a,0x39,0x37,0x3a,0x45,0x34,0x3a,
            0x39,0x45,0x3a,0x43,0x35,0x3a,0x41,0x30,0x3a,0x33,0x38,0x3a,0x41,0x36,0x3a,0x36,0x41};

    public static void actionStart(Context context,String data1,String data2){
        Intent intent =new Intent(context ,MainActivity.class);
        intent.putExtra("param1",data1);
        intent.putExtra("param2",data2);
        context.startActivity(intent);
    }




    /*
     * 这个方法你已经看到过很多次了，每个活动中我们都重写了这个方法，
     * 它会在活动 第一次被创建的时候调用。你应该在这个方法中完成活动的初始化操作，
     * 比如说加载布局、绑定事件等。
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //提交
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BDMapTestActivity.class));
            }
        });
        Log.d(TAG, "onCreate: "+"=CHECK_BYTE_ARR=="+new String(CHECK_BYTE_ARR));
    }

    /*
     *这个方法在活动由不可见变为可见的时候调用。
     * */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /*
     *这个方法在活动准备好和用户进行交互的时候调用。此时的活动一定位于返回栈的栈顶，并且处于运行状态。
     * */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /*
     *这个方法在系统准备去启动或者恢复另一个活动的时候调用。
     * 我们通常会在这个方 法中将一些消耗 CPU 的资源释放掉，
     * 以及保存一些关键数据，但这个方法的执行速度 一定要快，
     * 不然会影响到新的栈顶活动的使用。
     * */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /*
     * 这个方法在活动完全不可见的时候调用。它和 onPause()方法的主要区别在于，
     * 如 果启动的新活动是一个对话框式的活动，那么 onPause()方法会得到执行，
     * 而 onStop() 方法并不会执行。
     * */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /*
     * 这个方法在活动被销毁之前调用，之后活动的状态将变为销毁状态。
     * */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*
    * 这个方法在活动由停止状态变为运行状态之前调用，也就是活动被重新启动了。
      以上七个方法中除了 onRestart()方法，其他都是两两相对的，从而又可以将活动分为三
      种生存期。
    * */
    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
