package com.test.learn.activity.sms;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.test.learn.R;
import com.test.learn.base.BaseActivity;

public class SmSMainActivity extends BaseActivity {


    private EditText to;
    private EditText msgInput;
    private Button send;


    private TextView sender;
    private TextView content;
    private IntentFilter receiveFilter;
    private MessageReceiver messageReceiver;

    private IntentFilter sendFilter;
    private SendStatusReceiver sendStatusReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sm_smain);
        sender = (TextView) findViewById(R.id.sender);
        content = (TextView) findViewById(R.id.content);
        receiveFilter = new IntentFilter();
        receiveFilter.setPriority(100);
        receiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        messageReceiver = new MessageReceiver();
        registerReceiver(messageReceiver, receiveFilter);


        sendFilter = new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
        sendStatusReceiver = new SendStatusReceiver();
        registerReceiver(sendStatusReceiver, sendFilter);

        to = (EditText) findViewById(R.id.to);
        msgInput = (EditText) findViewById(R.id.msg_input);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * 其中第一个参数用于指定接收人的手 机号码，第三个参数用于指定短信的内容，
                 * 其他的几个参数我们暂时用不到，直接传入 null 就可以了。
                 * */
                SmsManager smsManager = SmsManager.getDefault();
                Intent sentIntent = new Intent("SENT_SMS_ACTION");
                PendingIntent pi = PendingIntent.getBroadcast
                        (SmSMainActivity.this, 0, sentIntent, 0);
                smsManager.sendTextMessage(to.getText().toString(), null, msgInput.getText().toString(), pi, null);
            }
        });

    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            /*
             * 一是提高 MessageReceiver 的优先级，让它能够先 于系统短信程序接收到短信广播。
             * 二是在 onReceive()方法中调用 abortBroadcast()方法，中止
             * */
            abortBroadcast();

            Bundle bundle = intent.getExtras();
            // 提取短信消息
            Object[] pdus = (Object[]) bundle.get("pdus"); //

            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
            String address = messages[0].getOriginatingAddress(); // 获取发送方号码
            String fullMessage = "";

            for (SmsMessage message : messages) {
                fullMessage += message.getMessageBody(); // 获取短信内容
            }
            sender.setText(address);
            content.setText(fullMessage);
        }
    }

    class SendStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (getResultCode() == RESULT_OK) {
                // 短信发送成功
                Toast.makeText(context, "Send succeeded", Toast.LENGTH_LONG).show();
            } else {
                // 短信发送失败
                Toast.makeText(context, "Send failed", Toast.LENGTH_LONG).show();
            }
        }

    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageReceiver);
        unregisterReceiver(sendStatusReceiver);
    }
}
