package com.example.zuo.forestlocation.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zuo.forestlocation.R;


/**
 * ������������Activity����
 * 
 * @author ouweibin
 * @date 2015-07-10
 * @version 1.0
 */
public class FrameActivity extends BaseActivity {
	private ImageView mIvBack;
	private TextView mTvTitle;
	private ImageView mIvRight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_top_title);
		initView();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initView() {
		mIvBack = (ImageView) findViewById(R.id.iv_back);
		mTvTitle = (TextView) findViewById(R.id.tv_top_title);
		mIvRight = (ImageView) findViewById(R.id.iv_right);
		mIvBack.setOnClickListener(new OnBackClickedListener());
	}

	protected void setTopBarTitle(String pText) {
		mTvTitle.setText(pText);
	}


	protected void setTopBarTitle(int pResID) {
		mTvTitle.setText(pResID);
	}

	/**
	 * ��ʾ���ذ�ť
	 */
	protected void showTitleBackButton() {
		mIvBack.setVisibility(View.VISIBLE);
	}

	/**
	 * ���ط��ذ�ť
	 */
	protected void hideTitleBackButton() {
		mIvBack.setVisibility(View.GONE);
	}

	/**
	 * ��ʾ�Ҳ�İ�ť
	 */
	protected void showTitleRightButton() {
		mIvRight.setVisibility(View.VISIBLE);
	}

	/**
	 * �����Ҳ�İ�Ŧ
	 */
	protected void hideTitleRightButton() {
		mIvRight.setVisibility(View.GONE);
	}

	/**
	 * Ϊ�Ҳఴť��������Drawable����
	 * 
	 * @param pDrawable
	 *            ����ӵ�Drawable����
	 */
	protected void setTitleRightButtonBackgroundDrawable(Drawable pDrawable) {
		mIvRight.setBackgroundDrawable(pDrawable);
	}

	/**
	 * Ϊ�Ҳఴť���ñ�����ɫ
	 * 
	 * @param pColor
	 *            �����õ���ɫ
	 */
	protected void setTitleRightButtonBackgroundColor(int pColor) {
		mIvRight.setBackgroundColor(pColor);

	}

	/**
	 * Ϊ�Ҳఴť���ñ�����Դ
	 * 
	 * @param pResID
	 */
	protected void setTitleRightButtonImageResource(int pResID) {
		mIvRight.setImageResource(pResID);
	}

	/**
	 * Ϊ�Ҳఴť���õ�������ļ�����
	 * 
	 * @param l
	 *            �������������ʵ��
	 */
	protected void setTitleRightButtonListener(View.OnClickListener l) {
		mIvRight.setOnClickListener(l);
	}

	/**
	 * ���Layout������Body��
	 * 
	 * @param pResID
	 *            Ҫ��ӵ�Layout��ԴID
	 */
	protected void appendMainBody(int pResID) {
		LinearLayout _MainBody = (LinearLayout) findViewById(getMainBodyLayoutID());
		View _View = LayoutInflater.from(this).inflate(pResID, null);

		LayoutParams lp = new LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		_View.setLayoutParams(lp);
		_MainBody.addView(_View);
	}

	/**
	 * ���Layout������Body��
	 * 
	 * @param pView
	 *            Ҫ��ӵ�View
	 */
	protected void appendMainBody(View pView) {
		LinearLayout _MainBody = (LinearLayout) findViewById(getMainBodyLayoutID());
		LayoutParams lp = new LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		_MainBody.addView(pView, lp);
	}

	/**
	 * ��ӵ���Layout������Body��
	 * 
	 * @param pResID
	 *            Ҫ��ӵ�Layout��ԴID
	 */
	protected void appendOneLayout(int pResID) {

		LinearLayout _MainBody = (LinearLayout) findViewById(getMainBodyLayoutID());
		View _View = LayoutInflater.from(this).inflate(pResID, null);
		LayoutParams lp = new LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		_View.setLayoutParams(lp);
		_MainBody.addView(_View);
	}

	/**
	 * �õ����岼�ֵ�id
	 * 
	 * @return ���岼�ֵ�id
	 */
	protected int getMainBodyLayoutID() {
		return R.id.ll_main;
	}

	class OnBackClickedListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onBackPressed();
		}

	}
}
