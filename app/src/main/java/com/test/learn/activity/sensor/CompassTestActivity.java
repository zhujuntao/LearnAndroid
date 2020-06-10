package com.test.learn.activity.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.test.learn.R;
import com.test.learn.base.BaseActivity;

public class CompassTestActivity extends BaseActivity {
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass_test);
        sensorManager = (SensorManager) getSystemService(Context.
                SENSOR_SERVICE);
        //加速度传感器和地磁传感器
        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.
                TYPE_MAGNETIC_FIELD);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }

    private SensorEventListener listener = new SensorEventListener() {

        float[] accelerometerValues = new float[3];
        float[] magneticValues = new float[3];
        private float lastRotateDegree;


        @Override
        public void onSensorChanged(SensorEvent event) {
            // 判断当前是加速度传感器还是地磁传感器
            // 判断当前是加速度传感器还是地磁传感器
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                // 注意赋值时要调用clone()方法
                accelerometerValues = event.values.clone();
            } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                // 注意赋值时要调用clone()方法
                magneticValues = event.values.clone();
            }
            float[] R = new float[9];
            float[] values = new float[3];
            //包含旋转矩阵的 R 数组
            /*
             * 其中第一个参数 R 是一个长度为 9 的 float 数组，
             * getRotationMatrix()方法计算出的旋转 数据就会赋值到这个数组当中。
             * 第二个参数是一个用于将地磁向量转换成重力坐标的旋转矩 阵，通常指定为 null 即可。
             * 第三和第四个参数则分别就是加速度传感器和地磁传感器输出的 values 值
             *
             *
             * */
            SensorManager.getRotationMatrix(R, null, accelerometerValues,
                    magneticValues);
            SensorManager.getOrientation(R, values);
            /*
             * values 是一个长度为 3 的 float 数组，手机在各个方向上的旋转数据都会被存放到这个数 组当中。
             * 其中
             * values[0]记录着手机围绕着图 12.3 中 Z 轴的旋转弧度，
             * values[1]记录着手机围 绕 X 轴的旋转弧度，
             * values[2]记录着手机围绕 Y 轴的旋转弧度。
             *
             *注意这里计算出的数据都是以弧度为单位的，因此如果你想将它们转换成角度还需要调 用如下方法:
             *Math.toDegrees(values[0]);
             * */
            Log.d("MainActivity", "value[0] is " + Math.toDegrees(values[0]));


            //
            // 将计算出的旋转角度取反，用于旋转指南针背景图
            float rotateDegree = -(float) Math.toDegrees(values[0]);
            if (Math.abs(rotateDegree - lastRotateDegree) > 1) {
                RotateAnimation animation = new RotateAnimation(lastRotateDegree, rotateDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setFillAfter(true);
                //compassImg.startAnimation(animation);
                lastRotateDegree = rotateDegree;


            }
        }

            @Override
            public void onAccuracyChanged (Sensor sensor,int accuracy){

            }
        };
    }
