package cn.com.jinke.wh_drugcontrol.me;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.customview.AppOneDialog;
import cn.com.jinke.wh_drugcontrol.customview.CustomEditText;
import cn.com.jinke.wh_drugcontrol.me.manager.SignInManager;
import cn.com.jinke.wh_drugcontrol.utils.BDLocationTL;
import cn.com.jinke.wh_drugcontrol.utils.BirthdayPicker;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * @author jinke
 * @date 2018/9/10
 * @description
 */
@Route(path = RouteUtils.R_ME_SIGNIN)
public class SignInUI extends ProjectBaseUI {

    @ViewInject(R.id.sign_ksrq)
    private CustomEditText mKsrqEt;

    @ViewInject(R.id.sign_jsrq)
    private CustomEditText mJsrqEt;

    private String mKsrq;
    private String mJsrq;

    private int[] MSG = new int[]{ACCESS_NET_FAILED, SIGN_IN_SUC};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case SIGN_IN_SUC:
                dismissLoading();
                if (msg.arg1 == SUCCESS){
                    showToast(R.string.qdcg);
                }else {
                    String result = (String) msg.obj;
                    showToast(result);
                }
                break;
            case ACCESS_NET_FAILED:
                dismissLoading();
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
        setContentView(R.layout.ui_signin);
    }

    @Override
    protected void onInitView() {
        Header header = getHeader();
        if(header != null){
            header.titleText.setText(R.string.grqd);
            header.rightLayout.setVisibility(View.GONE);
            header.rightImageBtn.setVisibility(View.GONE);
            header.rightText.setText(R.string.qdao);
            header.rightLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //startLocationFor();
                }
            });
        }
    }

    @Override
    protected void onInitData() {}

    @Event(value = {R.id.sign_ksrq, R.id.sign_jsrq, R.id.sign_query_btn, R.id.sign_begin_btn},
            type = android.view.View.OnClickListener.class)
    private void click(View view){
        switch (view.getId()){
            case R.id.sign_ksrq:
                //开始时间
                BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        mKsrq = String.format("%s-%s-%s", year, month, day);
                        mKsrqEt.setText(mKsrq);
                    }
                });
                break;
            case R.id.sign_jsrq:
                //结束时间
                BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        mJsrq = String.format("%s-%s-%s", year, month, day);
                        mJsrqEt.setText(mJsrq);
                    }
                });
                break;
            case R.id.sign_query_btn:
                //查询
                mKsrq = mKsrqEt.getText().toString().trim();
                mJsrq = mJsrqEt.getText().toString().trim();

                if(TextUtils.isEmpty(mKsrq)){
                    mKsrq = null;
                }

                if(TextUtils.isEmpty(mJsrq)){
                    mJsrq = null;
                }

                SigninListUI.startActivity(mKsrq, mJsrq);
                break;
            case R.id.sign_begin_btn:
                //签到
                startLocationFor();
                break;
            default:
                break;
        }
    }

    /**
     * 开始签到
     */
    private void beginSigin(double latitude, double longitude, String address, String city){
        if(TextUtils.isEmpty(city)){
            city = "";
        }

        if(TextUtils.isEmpty(address)){
            address = "";
        }

        showLoading();
        SignInManager.getInstance().addSignIn(latitude,longitude,address,city);
    }

    /**
     * 签到界面的跳转
     */
    public final static void startActivity(){
        ARouter.getInstance().build(RouteUtils.R_ME_SIGNIN).navigation();
    }

    private void startLocationFor() {
        showLoading();
        BDLocationTL.getInstance().startLocation(new BDLocationTL.MyLocationLisenner() {
            @Override
            public void onLocationFailed(BDLocation bdLocation) {
                //定位失败
                dismissLoading();
                tipDialog(getString(R.string.qddwsb));
            }

            @Override
            public void onLocationSuccess(BDLocation bdLocation) {

                //定位成功
                dismissLoading();
                if (bdLocation != null) {
                    double latitude = bdLocation.getLatitude();
                    double longitude = bdLocation.getLongitude();
                    String city = bdLocation.getCity();
                    String address = bdLocation.getAddrStr();
                    beginSigin(latitude, longitude, address, city);
                } else {
                    tipDialog(getString(R.string.qddwsb));
                }
            }
        });
    }

    private void tipDialog(String context) {
        AppOneDialog dialog = new AppOneDialog(this, context, getString(R.string.tishi), true, getString(R.string.qd));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.one_ok:
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BDLocationTL.getInstance().stopLocation();
    }
}
