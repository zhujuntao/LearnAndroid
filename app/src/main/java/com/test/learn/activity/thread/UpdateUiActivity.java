package com.test.learn.activity.thread;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.test.learn.R;
import com.test.learn.base.BaseActivity;

public class UpdateUiActivity extends BaseActivity implements View.OnClickListener {


    private TextView text;
    private Button changeText;

    public static final int UPDATE_TEXT = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    // 在这里可以进行UI操作
                    text.setText("Nice to meet you");
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ui);
        text = (TextView) findViewById(R.id.text);
        changeText = (Button) findViewById(R.id.change_text);
        changeText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_text:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message);// 将Message对象发送出去

                        //text.setText("Nice to meet you");
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
