package com.test.learn.activity.service;


import android.content.Intent;
import android.os.Bundle;

import com.test.learn.R;
import com.test.learn.base.BaseActivity;
import com.test.learn.service.LongRunningService;

public class LongRunningServiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_running_service);
        Intent intent = new Intent(this, LongRunningService.class);
        startService(intent);
    }
}
