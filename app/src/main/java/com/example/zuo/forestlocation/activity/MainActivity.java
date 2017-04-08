package com.example.zuo.forestlocation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zuo.forestlocation.R;
import com.example.zuo.forestlocation.base.FrameActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends FrameActivity {



    @InjectView(R.id.main_name)
    EditText mainName;
    @InjectView(R.id.main_passWord)
    EditText mainPassWord;
    @InjectView(R.id.main_login)
    TextView mainLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appendMainBody(R.layout.activity_main);
        setTopBarTitle("登录");
        ButterKnife.inject(this);
    }


    @OnClick(R.id.main_login)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_login:
                if (mainName.getText().toString().equals("admin") && mainPassWord.getText().toString().equals("123456"))
                    startActivity(new Intent(this, LocationMap.class));
                break;
        }
    }


}
