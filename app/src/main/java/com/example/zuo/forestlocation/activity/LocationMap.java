package com.example.zuo.forestlocation.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.example.zuo.forestlocation.R;
import com.example.zuo.forestlocation.base.BaseActivity;
import com.example.zuo.forestlocation.bean.LocationBean;
import com.example.zuo.forestlocation.tool.SqLite_DB_Utile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zuo on 2017/4/7.
 */

public class LocationMap extends BaseActivity implements AMap.OnCameraChangeListener {
    @InjectView(R.id.location_save)
    TextView locationSave;
    @InjectView(R.id.location_find)
    TextView locationFind;
    @InjectView(R.id.location_draw)
    TextView locationDraw;
    MapView mMapView;
    @InjectView(R.id.linearLayout)
    LinearLayout linearLayout;


    AMap aMap;
    LatLng target;
    Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_map);
        ButterKnife.inject(this);
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
        aMap.setOnCameraChangeListener(this);
    }

    @OnClick({R.id.location_save, R.id.location_find, R.id.location_draw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_save:
                LocationBean locationBean = new LocationBean();
                locationBean.setLatitude(target.latitude);
                locationBean.setLongitude(target.longitude);
                UUID uuid = UUID.randomUUID();
                locationBean.setId(uuid.toString());
                SqLite_DB_Utile.getInit(this).saveLocationData(locationBean);
                break;
            case R.id.location_find:

                break;
            case R.id.location_draw:
                drawLine();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onCameraChange(CameraPosition position) {
        target = position.target;

    }

    @Override
    public void onCameraChangeFinish(CameraPosition position) {

    }

    private void drawLine() {
        List<LatLng> latLngs = new ArrayList<LatLng>();
        List<LocationBean> location = SqLite_DB_Utile.getInit(this).getAllLocationData();
        if (location != null && location.size() > 0) {
            for (int i = 0; i < location.size(); i++) {
                latLngs.add(new LatLng(location.get(i).getLatitude(), location.get(i).getLongitude()));
            }
            polyline = aMap.addPolyline(new PolylineOptions().
                    addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));
        }
    }


}
