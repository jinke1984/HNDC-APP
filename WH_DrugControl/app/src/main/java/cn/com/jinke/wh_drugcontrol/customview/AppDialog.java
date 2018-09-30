package cn.com.jinke.wh_drugcontrol.customview;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.utils.ActivityHelper;

/**
 * Author: jinke
 * Date: 2016-05-06 09:04
 * Description:
 */
public class AppDialog extends Dialog implements View.OnClickListener {

	private Activity mActivity;
	private View.OnClickListener mListener;
	private TextView mContent;
	private TextView mTitle;

	public AppDialog(Activity activity, String content, String title) {
		super(activity, R.style.DimDialogStyle);
		setContentView(R.layout.custom_app_dialog);
		mActivity = activity;
		setCanceledOnTouchOutside(false);
		setCancelable(false);
		mTitle = (TextView) findViewById(R.id.dialog_title);
		mContent = (TextView) findViewById(R.id.content);
		mTitle.setText(title);
		mContent.setText(content);
		findViewById(R.id.ok).setOnClickListener(this);
		findViewById(R.id.cancel).setOnClickListener(this);
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
		dismiss();
	}
}
