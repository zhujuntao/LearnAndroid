package com.example.learnandroid.activity.Location;


import android.location.Location;
import android.os.Bundle;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.example.learnandroid.R;
import com.example.learnandroid.base.BaseActivity;

public class BDMapTestActivity extends BaseActivity {


    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bdmap_test);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //普通地图 ,mBaiduMap是地图控制器对象

        /*
         * MAP_TYPE_NORMAL     普通地图（包含3D地图）
         *
         * MAP_TYPE_SATELLITE  卫星图
         *
         * MAP_TYPE_NONE       空白地图
         *
         * */
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);


        //开启交通图
        mBaiduMap.setTrafficEnabled(true);

        //开启地图的定位图层

        mBaiduMap.setMyLocationEnabled(true);


        //定位初始化
        mLocationClient = new LocationClient(this);

        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);

        //设置locationClientOption
        mLocationClient.setLocOption(option);

        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mLocationClient.start();


    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
    }

    private void navigateTo(Location location) {

       /* MapController controller = mMapView.getController();
        controller.setZoom(16); // 设置缩放级别
        GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6),
                (int) (location.getLongitude() * 1E6));
        controller.setCenter(point); // 设置地图中心点*/
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
        }
    }

   /* private void navigateTo(Location location) {
        MapController controller = mapView.getController(); // 设置缩放级别
        controller.setZoom(16);
        GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6),
                (int) (location.getLongitude() * 1E6));
// 设置地图中心点
        controller.setCenter(point);
        location
        MyLocationOverlay myLocationOverlay = new MyLocationOverlay(mapView);
        LocationData locationData = new LocationData();
// 指定我的位置
        locationData.latitude = location.getLatitude();
        locationData.longitude = location.getLongitude();
        myLocationOverlay.setData(locationData);
        mapView.getOverlays().add(myLocationOverlay);
        mapView.refresh(); // 刷新使新增覆盖物生效
    }*/

}
