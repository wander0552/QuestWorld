package com.wander.questworld.Sight;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wander.Json.SightJson;
import com.wander.MyUtils.Constants;
import com.wander.MyUtils.urlUtils;
import com.wander.model.Attractions;
import com.wander.model.Destinations;
import com.wander.questworld.R;
import com.wander.questworld.Sight.Detail.AttractionsActivity;
import com.wander.xutils.MyDB;
import com.wander.xutils.MyHttp;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends ActionBarActivity implements AMap.OnInfoWindowClickListener, AMap.OnMarkerClickListener, LocationSource {
    private MapView mapView;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private LocationManagerProxy mAMapLocationManager;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle extras = getIntent().getExtras();
        id= extras.getString("id");

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 必须要写
        init();
    }

    private void showSight(List<Attractions> list) {
        // marker旋转90度
//        marker2.setRotateAngle(90);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Attractions attraction = list.get(i);
            aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).
                    position(new LatLng(attraction.getLat(),attraction.getLng())).title(attraction.getId() + ":" + attraction.getName())
                    .snippet("用户评分：" + attraction.getUser_score()).draggable(true));
        }

        //        设置地图的中心点
//      CameraUpdate cameraUpdate = CameraUpdateFactory.changeLatLng(latlng);//仅改变可视区域中心点的坐标，其他属性不变。
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(list.get(0).getLat(),list.get(0).getLng()), 9);//可以改变可视区域的经纬度、缩放级别，并且保留其他属性。
        aMap.moveCamera(cameraUpdate);
    }

    private void loadData() {
        HttpUtils httpUtils = MyHttp.getInstance();
        httpUtils.send(HttpRequest.HttpMethod.GET, urlUtils.getAttractions(id), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                List<Attractions> attractions = SightJson.getAttractions(id, responseInfo.result);
//                List<Destinations> destinationses=SightJson.getDestinations(urlUtils.DESTINATIONS);
//                Log.d("maplist", attractions.toString());
//                Log.d("destinations",destinationses.toString());
                if (attractions != null) {
                    showSight(attractions);
                    try {
                        MyDB.getDbUtil().saveAll(attractions);
//                        MyDB.getDbUtil().saveAll(destinationses);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(MapActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
            loadData();
        }
    }

    /**
     * 设置一些amap的属性  定位
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
//         myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);

        aMap.setLocationSource(this);// 设置定位监听
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setScaleControlsEnabled(true);//显示比例尺
        uiSettings.setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
//        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false

        //设置信息窗口的点击事件
        aMap.setOnInfoWindowClickListener(this);
        aMap.setOnMarkerClickListener(this);
    }

    /**
     * 监听点击infowindow窗口事件回调
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        Bundle bundle=new Bundle();
        bundle.putString("id",marker.getTitle().split(":")[0]);
        Intent intent=new Intent(this,AttractionsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, "点击景点查看详情", Toast.LENGTH_SHORT).show();
        if (aMap != null) {
        }
        return false;
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mAMapLocationManager == null) {
            mAMapLocationManager = LocationManagerProxy.getInstance(this);
            /*
             * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
            mAMapLocationManager.requestLocationUpdates(
                    LocationProviderProxy.AMapNetwork, 2000, 10, new AMapLocationListener() {
                        @Override
                        public void onLocationChanged(AMapLocation aLocation) {
                            if (mListener != null && aLocation != null) {
                                mListener.onLocationChanged(aLocation);// 显示系统小蓝点
                            }
                        }

                        @Override
                        public void onLocationChanged(Location location) {

                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    });
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
//        mListener = null;
//        mAMapLocationManager = null;
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}
