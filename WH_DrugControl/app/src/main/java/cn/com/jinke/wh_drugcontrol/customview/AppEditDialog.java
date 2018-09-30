package cn.com.jinke.wh_drugcontrol.customview;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.utils.ActivityHelper;

/**
 * Created by jinke on 2017/5/20.
 * 带编辑框的对话框
 */

public class AppEditDialog extends Dialog implements View.OnClickListener{

    private Activity mActivity;
    private View.OnClickListener mListener;
    private EditText mInputEditText;
    private TextView mTitleTextView;
    private String mCode = null;

    public AppEditDialog(Activity activity, String aTitle){
        super(activity, R.style.DimDialogStyle);
        setContentView(R.layout.custom_edittext_dialog);
        mActivity = activity;
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        mInputEditText = (EditText)findViewById(R.id.input_code_edittext);
        mTitleTextView = (TextView)findViewById(R.id.top_textview);
        mTitleTextView.setText(aTitle);
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
            if(v.getId() == R.id.ok){
                mCode = mInputEditText.getText().toString().trim();
                setCode(mCode);
            }
            mListener.onClick(v);
        }
        //dismiss();
    }

    public String getCode() {
        return mCode;
    }

    private void setCode(String mCode) {
        this.mCode = mCode;
    }
}
