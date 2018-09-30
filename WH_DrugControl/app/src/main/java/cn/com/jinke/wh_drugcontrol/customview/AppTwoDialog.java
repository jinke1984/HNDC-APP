package cn.com.jinke.wh_drugcontrol.customview;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.utils.ActivityHelper;


/**
 * 具有2个按钮的自定义对话框
 * @author jinke
 *
 */
public class AppTwoDialog extends Dialog implements View.OnClickListener{
	
	private Activity mActivity;
	private View.OnClickListener mListener;
	private TextView mContent;
	private TextView mTitleTV;
	private TextView mOKBtn;
	private TextView mCancelBtn;
	private boolean isCanDisappear = true;
	
	/**
	 * @param activity   上下文
	 * @param content    对话框显示的内容
	 * @param aString    对话框title显示的内容
	 * @param isCancel   是否点击消失
	 * @param aOK        按钮描述文件
	 * @param aCancel    按钮描述文件
	 */
	public AppTwoDialog(Activity activity, String content, String aString, boolean isCancel, String aOK, String aCancel){
		super(activity, R.style.DimDialogStyle);
		setContentView(R.layout.dialog_app_two);
		mActivity = activity;
		setCanceledOnTouchOutside(isCancel);
		setCancelable(isCancel);
		mTitleTV = (TextView)findViewById(R.id.dialog_two_tv);
		mContent = (TextView) findViewById(R.id.two_content);
		mContent.setText(content);
		mTitleTV.setText(aString);
		
		mOKBtn = (TextView)findViewById(R.id.two_ok);
		mCancelBtn = (TextView)findViewById(R.id.two_cancel);
		
		if(!TextUtils.isEmpty(aOK)){
			mOKBtn.setText(aOK);
		}
		
		if(!TextUtils.isEmpty(aCancel)){
			mCancelBtn.setText(aCancel);
		}
		
		mOKBtn.setOnClickListener(this);
		mCancelBtn.setOnClickListener(this);
		
	}
	
	public void setOnClickListener(View.OnClickListener listener) {
		this.mListener = listener;
	}
	
	@Override
	public void dismiss() {
		if (ActivityHelper.isActivityRunning(mActivity)) {
			super.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		if (mListener != null) {
			mListener.onClick(v);
		}
		if(isCanDisappear){
			dismiss();
		}
	}
	public void setIsCanDisappear(boolean canDisappear) {
		this.isCanDisappear = canDisappear;
	}
}
