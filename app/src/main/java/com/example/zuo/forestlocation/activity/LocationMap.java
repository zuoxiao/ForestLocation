package com.example.zuo.forestlocation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.example.zuo.forestlocation.R;
import com.example.zuo.forestlocation.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zuo on 2017/4/7.
 */

public class LocationMap extends BaseActivity {
    @InjectView(R.id.location_save)
    TextView locationSave;
    @InjectView(R.id.location_find)
    TextView locationFind;
    @InjectView(R.id.location_draw)
    TextView locationDraw;
    MapView mMapView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_map);
        ButterKnife.inject(this);
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
    }

    @OnClick({R.id.location_save, R.id.location_find, R.id.location_draw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_save:
                break;
            case R.id.location_find:
                break;
            case R.id.location_draw:
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

}
