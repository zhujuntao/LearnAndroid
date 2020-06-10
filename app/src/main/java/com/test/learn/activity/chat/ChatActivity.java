package com.test.learn.activity.chat;


import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.test.learn.R;
import com.test.learn.adapter.chat.ChatMsgAdapter;
import com.test.learn.base.BaseActivity;
import com.test.learn.bean.Msg;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity {
    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private ChatMsgAdapter adapter;
    private List<Msg> msgList = new ArrayList<Msg>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        initMsgs(); // 初始化消息数据
        adapter = new ChatMsgAdapter(ChatActivity.this, R.layout.chat_msg_item, msgList);
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgListView = (ListView) findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged(); // 当有新消息时，刷新
                    msgListView.setSelection(msgList.size()); // 将ListView inputText.setText(""); // 清空输入框中的内容
                }
            }
        });
    }

    private void initMsgs() {
        Msg msg1 = new Msg("Hello guy.", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello. Who is that?", Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is Tom. Nice talking to you. ", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }
}
