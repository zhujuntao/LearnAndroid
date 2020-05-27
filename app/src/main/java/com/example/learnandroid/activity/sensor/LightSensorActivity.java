package com.example.learnandroid.activity.sensor;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.learnandroid.R;
import com.example.learnandroid.base.BaseActivity;

public class LightSensorActivity extends BaseActivity {

    private SensorManager sensorManager;
    private TextView lightLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);
        lightLevel = (TextView) findViewById(R.id.light_level);
        //SensorManager 是系统所有传感器的管理器，有了它的实例之后就可以调用 getDefaultSensor() 方法来得到任意的传感器类型了，如下所示:
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        //用于表示传感器输出信息的更新速率，共有 SENSOR_DELAY_UI、SENSOR_DELAY_NORMAL、
        // SENSOR_DELAY_GAME和SENSOR_DELAY_FASTEST这四种值可选，它们的更新速率是 依次递增的。
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // values数组中第一个下标的值就是当前的光照强度
            float value = event.values[0];
            lightLevel.setText("Current light level is " + value + " lx");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }
}
