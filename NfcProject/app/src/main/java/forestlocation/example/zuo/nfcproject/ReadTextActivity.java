package forestlocation.example.zuo.nfcproject;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.widget.TextView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import forestlocation.example.zuo.tools.NfcReadHelper;
import forestlocation.example.zuo.tools.Utils;

/**
 * Author:Created by Ricky on 2017/8/25.
 * Email:584182977@qq.com
 * Description:
 */
public class ReadTextActivity extends BaseNfcActivity {
    private TextView mNfcText;
    private String mTagText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_text);
        mNfcText = (TextView) findViewById(R.id.tv_nfctext);
    }

    @Override
    public void onNewIntent(Intent intent) {

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            readAllData(intent);
        }

    }

    /**
     * 读取全部数据
     */
    private void readAllData(Intent intent) {
        String password = "332616785132";
        NfcReadHelper.getInstence(intent)
                .setPassword(password)
                .getAllData(new NfcReadHelper.NFCCallback() {
                    @Override
                    public void callBack(Map<String, List<String>> data) {
                        String text = "";
                        for (String key : data.keySet()) {
                            List list = data.get(key);
                            for (int i = 0; i < list.size(); i++) {
                                String str = "第" + key + "扇区" + "第" + i + "块内容：\n" + Utils.hexStringToString((String) list.get(i));
                                text += str + "\n";
                            }
                        }
                        mTagText = text;
                        Bundle bundle = new Bundle();
                        bundle.putString("data", text);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = 2;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void callBack(String data) {

                    }

                    @Override
                    public void error() {
                        Bundle bundle = new Bundle();
                        bundle.putString("data", "读取失败");
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                });
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mNfcText.setText(mTagText);
            return false;
        }
    });

}
