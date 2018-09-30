package cn.com.jinke.wh_drugcontrol.password;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.customview.AppDialog;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.GestureEncodeUtil;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

@Route(path = RouteUtils.R_GESTURE_PWD_UI)
public class GesturePwdUI extends ProjectBaseUI implements CodeConstants,
        GesturePwdView.OnPasswordSetListener, GesturePwdView.OnPasswordClearListener {

    @ViewInject(R.id.gesture_thumbnail)
    private GestureThumbnail gestureThumbnail;

    @ViewInject(R.id.gesture_tips)
    private TextView gestureTips;

    @ViewInject(R.id.gesture_login)
    private TextView gestureLogin;

    @ViewInject(R.id.gesture_container)
    private FrameLayout gestureContainer;

    private GesturePwdView gesturePwdView;

    private boolean isSetPassword = true;

    private boolean isFirstSet = true;

    public static boolean isRunning = false;
    private boolean firstInApp = false;
    private String targetUi;
    private String firstPassword;
    private String memoryPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_gesture_pwd);
        isRunning = true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        onInitView();
    }

    @Override
    protected void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }

    @Event(value = R.id.gesture_login, type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
        case R.id.gesture_login:
            if (isSetPassword) {
                gestureTips.setText(getString(R.string.gesture_tips_first));
                isFirstSet = true;
                gestureThumbnail.setPath("");
                gesturePwdView.reset(0);

            } else {
                forgetPassword();
            }
            break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onInitData() {
    }

    @Override
    protected void onInitView() {
        final boolean setPwd = getIntent().getBooleanExtra(EXTRA_SET_GESTURE_PASSWORD, false);
        firstInApp = getIntent().getBooleanExtra(EXTRA_FIRST_IN_APP, false);
        targetUi = getIntent().getStringExtra(TARGET_UI);
        memoryPassword = ShareUtils.getInstance().get(ShareUtils.ESHARE.SYS,
                SP_KEY_PASSWORD, ShareUtils.ETYPE.STRING);

        isSetPassword = setPwd || (memoryPassword == null);
        if (isSetPassword) {
            gestureThumbnail.setVisibility(View.VISIBLE);
            gestureLogin.setText(getString(R.string.gesture_login_reset));
            setGestureTipsErr(false, getString(R.string.gesture_tips_first));
        } else {
            gestureThumbnail.setVisibility(View.GONE);
            gestureLogin.setText(getString(R.string.gesture_login_forget));
            setGestureTipsErr(false, getString(R.string.gesture_tips_pwd));
        }

        gesturePwdView = new GesturePwdView(this, gestureContainer, !isSetPassword)
                .setOnPasswordSetListener(this).setOnPasswordClearListener(this);
    }

    @Override
    public void onSetPassword(GesturePwdView gesturePwdView, String password, boolean isVerify) {
        if (isVerify) { // 校验密码
            if (memoryPassword.equals(GestureEncodeUtil.encodeWithMd5(password))) {
                // 校验成功
                if (firstInApp) {
                    ARouter.getInstance().build(RouteUtils.R_FRAMEWORK_UI)
                            .withString(TARGET_UI, targetUi)
                            .navigation();
                } else {
                    if (targetUi != null) {
                        ARouter.getInstance().build(targetUi).navigation();
                    }
                }

                finish();

            } else {
                setGestureTipsErr(true, getString(R.string.gesture_tips_pwd_err));
                gesturePwdView.reset(true, 500);
            }
        } else { // 设置密码
            if (!isPasswordValid(password)) {
                setGestureTipsErr(true, getString(R.string.gesture_tips_err_rule));
                gesturePwdView.reset(true, 500);
                return;
            }

            if (isFirstSet) {
                isFirstSet = false;
                firstPassword = password;
                gestureThumbnail.setPath(password);
                gesturePwdView.reset(500);
            } else {
                if (password.equals(firstPassword)) {
                    ShareUtils.getInstance().commit(ShareUtils.ESHARE.SYS,
                            SP_KEY_PASSWORD, GestureEncodeUtil.encodeWithMd5(password));
                    showToast(R.string.gesture_tips_pwd_success);

                    ARouter.getInstance().build(RouteUtils.R_FRAMEWORK_UI).navigation();

                    finish();

                } else {
                    setGestureTipsErr(true, getString(R.string.gesture_tips_err_not_equal));
                    gesturePwdView.reset(true, 500);
                }
            }
        }
    }

    @Override
    public void onPasswordClear(GesturePwdView gesturePwdView) {
        if (isSetPassword) {
            if (isFirstSet) {
                setGestureTipsErr(false, getString(R.string.gesture_tips_first));
            } else {
                setGestureTipsErr(false, getString(R.string.gesture_tips_second));
            }
        } else {
            setGestureTipsErr(false, getString(R.string.gesture_tips_pwd));
        }
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

    private void setGestureTipsErr(boolean error, String msg) {
        final String txt = "<font color='#fb5230'>" + msg + "</font>";
        if (error) {
            gestureTips.setText(Html.fromHtml(txt));
            // 左右移动动画
            Animation shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_left_right);
            gestureTips.startAnimation(shakeAnimation);
        } else {
            gestureTips.setText(msg);
        }
    }

    private void forgetPassword() {
        AppDialog dialog = new AppDialog(this, getString(R.string.gesture_login_forget_relogin), getString(R.string.tishi));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                case R.id.ok:
                    ShareUtils.getInstance().commit(ShareUtils.ESHARE.SYS, SP_KEY_RELOGIN, true);
                    MasterManager.getInstance().delete();
                    final Bundle bundle = new Bundle();
                    bundle.putBoolean(EXTRA_SET_GESTURE_PASSWORD, true);
                    ARouter.getInstance().build(RouteUtils.R_LOGIN_UI)
                            .with(bundle)
                            .navigation();
                    finish();
                    break;
                case R.id.cancel:
                    break;
                }
            }
        });
        dialog.show();
    }

}
