package cn.com.jinke.wh_drugcontrol.me;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.com.jinke.wh_drugcontrol.BuildConfig;
import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.me.manager.LoginManager;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ESHARE;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ETYPE;
import cn.com.jinke.wh_drugcontrol.utils.GestureARouterUtil;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by jinke on 2017/7/7.
 */

@Route(path = RouteUtils.R_LOGIN_UI)
public class LoginUI extends ProjectBaseUI {

    @ViewInject(R.id.account)
    private EditText mAccountET = null;

    @ViewInject(R.id.password)
    private EditText mPasswordET = null;

    @ViewInject(R.id.login_btn)
    private Button mLoginBtn = null;

    @ViewInject(R.id.login_logo)
    private ImageView mLoginLogoIV;

    @ViewInject(R.id.login_title)
    private TextView mLoginTitleTV;

    @ViewInject(R.id.switch_net_cb)
    private RadioGroup mSwitchRG;

    @ViewInject(R.id.out_rb)
    private RadioButton mOutRB;

    @ViewInject(R.id.in_rb)
    private RadioButton mInRB;

    private int[] MSG = new int[]{LOGIN_MSG, ACCESS_NET_FAILED};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case LOGIN_MSG:
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                ShareUtils.getInstance().commit(ShareUtils.ESHARE.SYS, SP_KEY_RELOGIN, false);
                GestureARouterUtil.navToGesturePwdUi(true, true, null);
                finish();
//            UserCard userCard = MasterManager.getInstance().getUserCard();
//            if (userCard != null) {
//                ARouter.getInstance().build(RouteUtils.R_FRAMEWORK_UI).navigation();
//                finish();
//            }
            } else {
                showToast(R.string.dlsb);
            }
            break;
        case ACCESS_NET_FAILED:
            dismissLoading();
            showToast(R.string.dlsb);
            break;
        default:
            break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG);
        setContentView(R.layout.ui_login);
    }

    @Override
    protected void onInitView() {
        loginBtnEnable();
        mAccountET.addTextChangedListener(watcher);
        mPasswordET.addTextChangedListener(watcher);

        //TODO true 代表内网, false 代表外网
        boolean result = ShareUtils.getInstance().get(ESHARE.SYS, SWITCH_NET, ETYPE.BOOL);
        if(result){
            mOutRB.setChecked(false);
            mInRB.setChecked(true);
        }else{
            mOutRB.setChecked(true);
            mInRB.setChecked(false);
        }

        if(BUILD_TYPE.equals(BuildConfig.BUILD_NAME)){
            mLoginLogoIV.setBackgroundResource(R.drawable.h_login_logo);
            mLoginTitleTV.setText(R.string.h_app_name);
//            mLoginLogoIV.setBackgroundResource(R.drawable.p_login_logo);
//            mLoginTitleTV.setText(R.string.p_name);
        }
    }

    @Override
    protected void onInitData() {

    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub
            loginBtnEnable();
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }
    };

    private void loginBtnEnable() {
        if (TextUtils.isEmpty(mAccountET.getText().toString().trim())
                || TextUtils.isEmpty(mPasswordET.getText().toString().trim())) {
            mLoginBtn.setEnabled(false);
        } else {
            mLoginBtn.setEnabled(true);
        }
    }

    @Event(value = {R.id.login_btn}, type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
        case R.id.login_btn:
            if (isNetworkConnected()) {
                showLoading();
                String account = mAccountET.getText().toString().trim();
                String password = mPasswordET.getText().toString().trim();
                LoginManager.getInstance().login(account, password);
            } else {
                showToast(R.string.common_network_unavailable);
            }
            break;
        default:
            break;
        }
    }

    @Event(value = {R.id.switch_net_cb}, type = android.widget.RadioGroup.OnCheckedChangeListener.class)
    private void onCheckChange(RadioGroup group, int checkedId){
        View view = group.findViewById(checkedId);
        if(!view.isPressed()){
            return;
        }

        //TODO true 代表内网, false 代表外网
        switch (view.getId()){
            case R.id.in_rb:
                //内网
                ShareUtils.getInstance().commit(ESHARE.SYS, SWITCH_NET, true);
                break;
            case R.id.out_rb:
                //外网
                ShareUtils.getInstance().commit(ESHARE.SYS, SWITCH_NET, false);
                break;
            default:
                break;
        }
    }
}