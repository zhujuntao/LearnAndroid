package com.test.learn.activity.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

import com.test.learn.R;
import com.test.learn.base.BaseActivity;

public class AccelerometerSensoActivity extends BaseActivity {
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer_senso);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // 加速度可能会是负值，所以要取它们的绝对值

            float xValue = Math.abs(event.values[0]);
            float yValue = Math.abs(event.values[1]);
            float zValue = Math.abs(event.values[2]);
            //由于重力加速度的存在，即使手机在静止的情况下，某一个轴上的加速度也有可能达到 9.8m/s2，
            // 因此这个预定值必定是要大于 9.8m/s2 的，这里我们就设定为 15m/s2
            if (xValue > 15 || yValue > 15 || zValue > 15) {
                // 认为用户摇动了手机，触发摇一摇逻辑
                Toast.makeText(AccelerometerSensoActivity.this, "摇一摇", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
