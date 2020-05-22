package com.example.learnandroid.activity.service;


import android.content.Intent;
import android.os.Bundle;

import com.example.learnandroid.R;
import com.example.learnandroid.base.BaseActivity;
import com.example.learnandroid.service.LongRunningService;

public class LongRunningServiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_running_service);
        Intent intent = new Intent(this, LongRunningService.class);
        startService(intent);
    }
}
