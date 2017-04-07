package com.example.zuo.forestlocation.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.zuo.forestlocation.tool.CustomProgressDialog;


/**
 * Created by zuo on 2017/4/1.
 */
public class BaseActivity extends Activity implements View.OnClickListener {

    private CustomProgressDialog progressDialog = null;

    // 进度对话框的对象
    private ProgressDialog mProgressDialog;


    /**
     * 显示一个Toast信息
     *
     * @param pMsg 要显示的信息
     */
    protected void showMsg(String pMsg) {
        Toast.makeText(this, pMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);


    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    /**
     * 显示一个Toast信息
     *
     * @param pResID 要显示的信息ID
     */
    protected void showMsg(int pResID) {
        Toast.makeText(this, pResID, Toast.LENGTH_SHORT).show();
    }


    /**
     * 得到LayoutInflater对象
     *
     * @return LayoutInflater对象
     */
    protected LayoutInflater getLayouInflater() {
        return LayoutInflater.from(this);
    }

    // protected void SetAlertDialogIsClose(DialogInterface pDialog,Boolean
    // pIsClose)
    // {
    // try {
    // Field _Field =
    // pDialog.getClass().getSuperclass().getDeclaredField("mShowing");
    // _Field.setAccessible(true);
    // _Field.set(pDialog, pIsClose);
    // } catch (Exception e) {
    // }
    // }

    /**
     * 实现简单的对话框
     *
     * @param pTitelResID 标题的资源ID
     * @param pMessage    主要内容的ID
     * @return
     */
    protected AlertDialog showAlertDialog(int pTitelResID, String pMessage) {
        return showAlertDialog(pTitelResID, pMessage, -1, -1, null, null);
    }

    protected AlertDialog createAlertDialog(int pTitelResID, String pMessage) {
        return createAlertDialog(pTitelResID, pMessage, -1, -1, null, null);
    }

    /**
     * 实现两个按钮的对话框
     *
     * @param pTitelResID               标题的资源ID
     * @param pMessage                  主要内容的ID
     * @param pPositiveBtnResID         Positive按钮的ID
     * @param pNegativeBtnResID         Negative按钮的ID
     * @param pPositiveBtnClickListener Positive按钮的点击监听器
     * @param pNegativeBtnClickListener Negative按钮的点击监听器
     * @return
     */
    protected AlertDialog showAlertDialog(int pTitelResID, String pMessage,
                                          int pPositiveBtnResID, int pNegativeBtnResID,
                                          DialogInterface.OnClickListener pPositiveBtnClickListener,
                                          DialogInterface.OnClickListener pNegativeBtnClickListener) {
        AlertDialog.Builder _builder = new AlertDialog.Builder(this);
        _builder.setTitle(pTitelResID).setMessage(pMessage);
        if (pPositiveBtnResID != -1) {
            _builder.setPositiveButton(pPositiveBtnResID,
                    pPositiveBtnClickListener);
        }

        if (pNegativeBtnResID != -1) {
            _builder.setNegativeButton(pNegativeBtnResID,
                    pNegativeBtnClickListener);
        }

        return _builder.show();
    }

    protected AlertDialog createAlertDialog(int pTitelResID, String pMessage,
                                            int pPositiveBtnResID, int pNegativeBtnResID,
                                            DialogInterface.OnClickListener pPositiveBtnClickListener,
                                            DialogInterface.OnClickListener pNegativeBtnClickListener) {

        AlertDialog.Builder _builder = new AlertDialog.Builder(this);
        _builder.setTitle(pTitelResID).setMessage(pMessage);
        if (pPositiveBtnResID != -1) {
            _builder.setPositiveButton(pPositiveBtnResID,
                    pPositiveBtnClickListener);
        }

        if (pNegativeBtnResID != -1) {
            _builder.setNegativeButton(pNegativeBtnResID,
                    pNegativeBtnClickListener);
        }
        return _builder.create();
    }

    /**
     * 显示进度对话框
     *
     * @param pTitleResID   标题的资源ID
     * @param pMessageResID 主要内容的ID
     */
    protected void showProgressDialog(int pTitleResID, int pMessageResID) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(getString(pTitleResID));
        mProgressDialog.setMessage(getString(pMessageResID));

        mProgressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    protected void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }


    public void startProgressDialog() {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(this);
            progressDialog.setMessage("正在加载中...");
        }

        progressDialog.show();
    }

    public void startProgressDialog(String msg) {
        try {
            if (progressDialog == null) {
                progressDialog = CustomProgressDialog.createDialog(this);
                progressDialog.setMessage(msg);
            }

            progressDialog.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopProgressDialog();

    }

    @Override
    public void onClick(View v) {

    }
}
