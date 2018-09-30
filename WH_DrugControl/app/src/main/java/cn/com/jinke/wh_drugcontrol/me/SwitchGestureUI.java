package cn.com.jinke.wh_drugcontrol.me;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.xutils.view.annotation.ViewInject;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.password.GesturePwdView;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.utils.GestureEncodeUtil;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * 手势开关
 */
@Route(path = RouteUtils.R_SWITCH_GESTURE)
public class SwitchGestureUI extends ProjectBaseUI implements ToggleButton.OnCheckedChangeListener,
        GesturePwdView.OnPasswordSetListener, GesturePwdView.OnPasswordClearListener {

    @ViewInject(R.id.switch_pwd_btn)
    private ToggleButton mToggleBtn;

    @ViewInject(R.id.switch_pwd_state)
    private TextView mState;

    @ViewInject(R.id.switch_pwd_container)
    private FrameLayout mContainer;

    private GesturePwdView mPwdView;

    private boolean isFirstSet = true;
    private String firstPassword = "";
    private boolean currentState = false; // true：已开启手势密码

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle_gesture);
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        Header header = getHeader();
        if (header != null) {
            header.titleText.setText(getString(R.string.gesture_title));
        }
        mToggleBtn.setOnCheckedChangeListener(this);
        mPwdView = new GesturePwdView(this, mContainer, false)
                .setOnPasswordSetListener(this)
                .setOnPasswordClearListener(this);
        resetView();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isFirstSet = true;
        mState.setText(getString(R.string.gesture_tips_pwd));
        setContainerVisible(isChecked != currentState);
        mPwdView.setIsVerify(!isChecked).reset(0, false);
    }

    @Override
    public void onSetPassword(GesturePwdView gesturePwdView, String password, boolean isVerify) {
        if (isVerify) {
            String memPwd = ShareUtils.getInstance().get(ShareUtils.ESHARE.SYS, SP_KEY_PASSWORD, ShareUtils.ETYPE.STRING);
            if (!memPwd.equals(GestureEncodeUtil.encodeWithMd5(password))) {
                showToast(R.string.gesture_tips_pwd_err);
                gesturePwdView.reset(true, 500);
            } else {
                // 关闭手势
                currentState = false;
                ShareUtils.getInstance().commit(ShareUtils.ESHARE.SYS, SP_KEY_PASSWORD, null);
                ShareUtils.getInstance().commit(ShareUtils.ESHARE.SYS, SP_KEY_USE_GESTURE, false);
                showToast(R.string.gesture_toggle_off_success);
                setContainerVisible(false);
            }
        } else {
            if (isFirstSet) {
                if (!isPasswordValid(password)) {
                    setStateTxt(getString(R.string.gesture_tips_err_rule), true);
                    gesturePwdView.reset(true, 500);
                    return;
                }

                isFirstSet = false;
                firstPassword = password;
                gesturePwdView.reset(500);

            } else {
                if (firstPassword.equals(password)) {
                    currentState = true;
                    String pwd = GestureEncodeUtil.encodeWithMd5(password);
                    ShareUtils.getInstance().commit(ShareUtils.ESHARE.SYS, SP_KEY_PASSWORD, pwd);
                    ShareUtils.getInstance().commit(ShareUtils.ESHARE.SYS, SP_KEY_USE_GESTURE, true);
                    showToast(R.string.gesture_toggle_on_success);
                    setContainerVisible(false);

                } else {
                    isFirstSet = true;
                    showToast(R.string.gesture_tips_pwd_err);
                    gesturePwdView.reset(true, 500);
                }
            }
        }
    }

    @Override
    public void onPasswordClear(GesturePwdView gesturePwdView) {
        if (gesturePwdView.isVerify()) {
            resetView();
        } else {
            if (!isFirstSet) {
                mState.setText(getString(R.string.gesture_tips_second));
            } else {
                resetView();
            }
        }
    }

    private void setContainerVisible(boolean visible) {
        if (visible) {
            mState.setVisibility(View.VISIBLE);
            mContainer.setVisibility(View.VISIBLE);
        } else {
            mState.setVisibility(View.GONE);
            mContainer.setVisibility(View.GONE);
        }
    }

    private void resetView() {
//        boolean memOn = ShareUtils.getInstance().get(ShareUtils.ESHARE.SYS, SP_KEY_USE_GESTURE, ShareUtils.ETYPE.BOOL);
        String memPassword = ShareUtils.getInstance().get(ShareUtils.ESHARE.SYS, SP_KEY_PASSWORD, ShareUtils.ETYPE.STRING);
        currentState = !TextUtils.isEmpty(memPassword);
        mToggleBtn.setChecked(currentState);
        setContainerVisible(false);
    }

    /**
     * 判断密码合法性
     *
     * @param password 密码
     * @return true：合法
     */
    private boolean isPasswordValid(String password) {
        if (TextUtils.isEmpty(password)) return false;
        if (password.length() < 4) return false;
        return true;
    }

    private void setStateTxt(String msg, boolean error) {
        final String txt = "<font color='#fb5230'>" + msg + "</font>";
        if (error) {
            mState.setText(Html.fromHtml(txt));
            // 左右移动动画
            Animation shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_left_right);
            mState.startAnimation(shakeAnimation);
        } else {
            mState.setText(msg);
        }
    }
}
