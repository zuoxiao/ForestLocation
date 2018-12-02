package forestlocation.example.zuo.nfcproject;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;



import java.nio.charset.Charset;
import java.util.Locale;

import forestlocation.example.zuo.tools.NFCWriteHelper;

/**
 * Author:Created by Ricky on 2017/8/25.
 * Email:584182977@qq.com
 * Description:
 */
public class WriteTextActivity extends BaseNfcActivity {
    private String mText = "NFC-NewText-123-1234567891111111111111111111111111111111111111111111111111111111111111111";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_text);
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (mText == null)
            return;


        NFCWriteHelper.getInstence(intent).writeData(mText, 1, 2, new NFCWriteHelper.NFCCallback() {
            @Override
            public void isSusses(boolean flag) {

            }
        });
    }


}
