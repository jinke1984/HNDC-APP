package cn.com.jinke.wh_drugcontrol.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.jinke.wh_drugcontrol.R;

/**
 * Created by jinke on 2017/7/25.
 * 自定义编辑框
 */

public class CustomEditText extends RelativeLayout {

    private Context mContext;
    private EditText mEditText;
    private TextView mTextView;
    private ImageView mImageView;

    private String mHint = "";
    private String mTitle = "";
    private boolean mEnadle = true;
    private int mShow = 0;
    private int mIvId = R.drawable.add_down;
    private boolean isNumber = false;
    private int mMinLen = 0;
    private int mMaxLen = Integer.MAX_VALUE;
    private Toast mToast;
    private int mOriColor = Color.BLACK;
    private boolean isMatchRule = true;

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            applyRule(s);
        }
    };

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    //布局和控件的初始化
    public void init(AttributeSet attrs) {
        TypedArray typeArray = mContext.obtainStyledAttributes(attrs, R.styleable.custom_edittext);
        mIvId = typeArray.getResourceId(R.styleable.custom_edittext_custom_iv, mIvId);
        mTitle = typeArray.getString(R.styleable.custom_edittext_custom_tv);
        mHint = typeArray.getString(R.styleable.custom_edittext_custom_hint);
        mEnadle = typeArray.getBoolean(R.styleable.custom_edittext_custom_enable, mEnadle);
        isNumber = typeArray.getBoolean(R.styleable.custom_edittext_custom_number, isNumber);
        boolean isShow = typeArray.getBoolean(R.styleable.custom_edittext_custom_show, false);
        mMinLen = typeArray.getInteger(R.styleable.custom_edittext_custom_minLen, 0);
        mMaxLen = typeArray.getInteger(R.styleable.custom_edittext_custom_maxLen, Integer.MAX_VALUE);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.custom_edittext, this);
        mEditText = (EditText) view.findViewById(R.id.custom_et);
        mTextView = (TextView) view.findViewById(R.id.custom_tv);
        mImageView = (ImageView) view.findViewById(R.id.custom_iv);
        mEditText.addTextChangedListener(mTextWatcher);
        mImageView.setImageResource(mIvId);
        mTextView.setText(mTitle);
        mEditText.setEnabled(mEnadle);
        if (isShow) {
            mShow = View.VISIBLE;
        } else {
            mShow = View.GONE;
        }
        mImageView.setVisibility(mShow);
        if (isNumber) {
            mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if (mHint != null && !TextUtils.isEmpty(mHint.trim())) {
            mEditText.setHint(mHint);
        }
        typeArray.recycle();

        mOriColor = mEditText.getCurrentTextColor();
        mToast = Toast.makeText(mContext, mContext.getString(R.string.input_string_len, mMinLen, mMaxLen), Toast.LENGTH_SHORT);
    }

    private void applyRule(CharSequence text) {
        if (text.length() < mMinLen || text.length() > mMaxLen) {
            isMatchRule = false;
            mEditText.setTextColor(Color.RED);
            if (mToast != null) mToast.show();
        } else {
            isMatchRule = true;
            mEditText.setTextColor(mOriColor);
            if (mToast != null) mToast.cancel();
        }
    }

    /**
     * @return
     * @description 输入框是否有文字
     */
    public boolean isEmpty() {
        return getText() == null || getText().length() == 0;
    }

    /**
     * @return
     * @description 获取输入框内的文字
     */
    public String getText() {
        return mEditText.getText().toString();
    }

    /**
     * @return
     * @description 设置输入框内的文字
     */
    public void setText(String s) {
        mEditText.setText(s);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mEditText.setEnabled(enabled);
    }

    public boolean isMatchRule() {
        return isMatchRule;
    }

}
