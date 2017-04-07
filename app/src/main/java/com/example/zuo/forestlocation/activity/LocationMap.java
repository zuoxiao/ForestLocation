package com.example.zuo.forestlocation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_map);
        ButterKnife.inject(this);
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
}
