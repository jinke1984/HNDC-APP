package cn.com.jinke.wh_drugcontrol.me;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.utils.AppUtils;
import com.netease.nim.avchatkit.AVChatKit;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.com.jinke.wh_drugcontrol.BuildConfig;
import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.chat.manager.ChatManager;
import cn.com.jinke.wh_drugcontrol.customview.AppDialog;
import cn.com.jinke.wh_drugcontrol.customview.AppOneDialog;
import cn.com.jinke.wh_drugcontrol.database.DbManager;
import cn.com.jinke.wh_drugcontrol.manager.AppUpdateTimeManager;
import cn.com.jinke.wh_drugcontrol.manager.VersionManager;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.manager.MeManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.message.manager.MessageManager;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ESHARE;
import cn.com.jinke.wh_drugcontrol.thread.Dispatcher;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.DesUtil;
import cn.com.jinke.wh_drugcontrol.utils.GestureARouterUtil;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;

/**
 * Created by xkr on 2017/11/7.
 * 关于
 */
@Route(path = RouteUtils.R_ABOUT)
public class AboutUI extends ProjectBaseUI {

    @ViewInject(R.id.tv_app_version)
    private TextView appVersionTv;

    @ViewInject(R.id.iv_logo)
    private ImageView mLogoIV;

    @ViewInject(R.id.tv_app_name)
    private TextView mTitleIV;

    @ViewInject(R.id.tv_flsm)
    private TextView mFlsmTV;

    @ViewInject(R.id.tv_yhxy)
    private TextView mYhxyTV;

    @ViewInject(R.id.iv_flsm)
    private View mFlsmIV;

    @ViewInject(R.id.iv_yhxy)
    private View mYhxyIV;

    @ViewInject(R.id.code_iv)
    private ImageView mQRIV;

    @ViewInject(R.id.copyright_name)
    private TextView mCopyrightNameTV;

    private int[] MSG = new int[]{LOGOUT_SUC};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case LOGOUT_SUC:
                AppUpdateTimeManager.getInstance().updateAppUseStatu(AppUpdateTimeManager.AppUseStatu.LOGOUT);
                MessageProxy.sendEmptyMessage(FINISH_ALL_ACTIVITY);
                ARouter.getInstance().build(RouteUtils.R_LOGIN_UI).navigation(this, new NavCallback() {

                    @Override
                    public void onArrival(Postcard postcard) {
                        // 没有登录的逻辑，走登录页面
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        // 已经登录的逻辑，走到首页
                        GestureARouterUtil.navToGesturePwdUi(false, true, null);
                    }
                });
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
        setContentView(R.layout.activity_about);
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        Header header = getHeader();
        if (header != null) {
            header.titleText.setText(getString(R.string.about));
        }
        appVersionTv.setText(getString(R.string.app_version_no, AppUtils.getAppVersionName(this)));

        if(BUILD_TYPE.equals(BuildConfig.BUILD_NAME)){
            mLogoIV.setBackgroundResource(R.drawable.h_about_logo);
            mTitleIV.setText(R.string.h_app_name);
            mCopyrightNameTV.setText(R.string.copyright_name);
//            mLogoIV.setBackgroundResource(R.drawable.about_logo);
//            mTitleIV.setText(R.string.p_name);
        }else{
            mFlsmTV.setVisibility(View.GONE);
            mYhxyTV.setVisibility(View.GONE);
            mFlsmIV.setVisibility(View.GONE);
            mYhxyIV.setVisibility(View.GONE);
        }

        UserCard userCard = MasterManager.getInstance().getUserCard();
        if(userCard != null){
            try{
                String desPhone = DesUtil.encode(DesUtil.KEY, userCard.getInfoMobile());
                String desId = DesUtil.encode(DesUtil.KEY, userCard.getUserId());
                Bitmap bitmap = MeManager.getInstance().createQRBitmap(desPhone, desId);
                if (bitmap != null) {
                    mQRIV.setImageBitmap(bitmap);
                    mQRIV.setTag(bitmap);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Event(value = {R.id.tv_gesture, R.id.tv_update, R.id.tv_logout,
            R.id.tv_flsm, R.id.tv_yhxy, R.id.tv_signIn}, type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_gesture:
                ARouter.getInstance().build(RouteUtils.R_SWITCH_GESTURE).navigation();
                break;
            case R.id.tv_update:
                VersionManager.startCheckUpdate();
                break;
            case R.id.tv_logout:
                exitDialog();
                break;
            case R.id.tv_flsm:
                show(getString(R.string.flsm), "file:///android_asset/flsm.html");
                break;
            case R.id.tv_yhxy:
                show(getString(R.string.yhxy), "file:///android_asset/yhxy.html");
                break;
            case R.id.tv_signIn:
                //个人签到是需要在互联网环境里面进行的
                switchOutNet();
                break;
            default:
                break;
        }
    }

    /**
     * true 代表在内网，false代表在外网
     */
    private void switchOutNet(){
        boolean result = ShareUtils.getInstance().get(ESHARE.SYS, SWITCH_NET, ShareUtils.ETYPE.BOOL);
        if(result){
            switchOutNetDialog();
        }else{
            SignInUI.startActivity();
        }
    }

    /**
     * 定期报告新增功能在外网操作
     */
    private void switchOutNetDialog(){
        String content = String.format(getString(R.string.wwts), getString(R.string.grqd));
        AppOneDialog dialog = new AppOneDialog(this, content, getString(R.string.tishi), true, getString(R.string.qd));
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

    private void exitDialog() {
        AppDialog dialog = new AppDialog(this, getString(R.string.nqdytcm), getString(R.string.tishi));
        dialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (v.getId()) {
                    case R.id.ok:
                        //确定
                        exit();
                        break;
                    case R.id.cancel:
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    private void exit(){

        Dispatcher.runOnSingleThread(new Runnable() {
            @Override
            public void run() {
                //删除用户信息
                MasterManager.getInstance().clear();
                DbManager.getUserDb().getTableUser().delete();

                //删除推送消息
                MessageManager.reset();
                DbManager.getMessageDb().getTableMessage().delete();

                //初始化手势密码
                ShareUtils.getInstance().commit(ESHARE.SYS, SP_KEY_PASSWORD, null);
                ShareUtils.getInstance().commit(ESHARE.SYS, SP_KEY_USE_GESTURE, true);

                //删除聊天的消息记录
                ChatManager.getInstance().clear();

                //切换网络操作环境为内网
                ShareUtils.getInstance().commit(ESHARE.SYS, SWITCH_NET, true);

                MessageProxy.sendEmptyMessage(LOGOUT_SUC);
            }
        });
    }

    private void show(String title, String url){
        ARouter.getInstance().build(RouteUtils.R_WEB_UI)
                .withString(TITLE,title)
                .withString(URL,url)
                .navigation();
    }

}
