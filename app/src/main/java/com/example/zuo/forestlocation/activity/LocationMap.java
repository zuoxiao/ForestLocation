package com.example.zuo.forestlocation.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.example.zuo.forestlocation.R;
import com.example.zuo.forestlocation.base.BaseActivity;
import com.example.zuo.forestlocation.bean.LocationBean;
import com.example.zuo.forestlocation.tool.SqLite_DB_Utile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zuo on 2017/4/7.
 */

public class LocationMap extends BaseActivity implements AMap.OnCameraChangeListener, LocationSource, AMapLocationListener, AMap.OnMapClickListener {
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
    MyLocationStyle myLocationStyle;
    LocationSource.OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    private UiSettings mUiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_map);
        ButterKnife.inject(this);
        initView(savedInstanceState);
        Log.d("sha1", sHA1(this));
        drawMarker();
    }

    private void initView(Bundle savedInstanceState) {
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
        aMap.setOnCameraChangeListener(this);

        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.navi_map_gps_locked));
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);//
        aMap.setLocationSource(this);
// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
// 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        aMap.setOnMarkerClickListener(markerClickListener);
        aMap.setOnMapClickListener(this);
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setScaleControlsEnabled(true);
        // 定义 Marker 点击事件监听

    }



    @OnClick({R.id.location_save, R.id.location_find, R.id.location_draw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_save:
                if (target != null) {
                    LocationBean locationBean = new LocationBean();
                    locationBean.setLatitude(target.latitude);
                    locationBean.setLongitude(target.longitude);
                    UUID uuid = UUID.randomUUID();
                    // locationBean.setId(uuid.toString());
                    SqLite_DB_Utile.getInit(this).saveLocationData(locationBean);
                    Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                    drawMarker();
                }{
                Toast.makeText(this, "保存失败,请重试", Toast.LENGTH_SHORT).show();
            }

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
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
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
                    addAll(latLngs).width(10).color(Color.argb(255, 111, 168, 220)));
        }
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 绘制点
     */
    private void drawMarker() {
        List<LocationBean> location = SqLite_DB_Utile.getInit(this).getAllLocationData();
        if (location != null && location.size() > 0) {
            for (int i = 0; i < location.size(); i++) {
                LatLng latLng = new LatLng(location.get(i).getLatitude(), location.get(i).getLongitude());
                aMap.addMarker(new MarkerOptions().position(latLng).title("位置编号：" + String.valueOf(i + 1)).snippet("经度：" + location.get(i).getLongitude() + "\n" + "纬度：" + location.get(i).getLatitude()).visible(true).draggable(false).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.mipmap.poi_marker_pressed))));
            }

        }
    }
    Marker currentMarker;
    AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
        // marker 对象被点击时回调的接口
        // 返回 true 则表示接口已响应事件，否则返回false
        @Override
        public boolean onMarkerClick(Marker marker) {
            currentMarker=marker;
            return false;
        }
    };
    @Override
    public void onMapClick(LatLng latLng) {

        if(currentMarker!=null&&currentMarker.isInfoWindowShown()){
            currentMarker.hideInfoWindow();//这个是隐藏infowindow窗口的方法
        }
    }
}
