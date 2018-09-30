package cn.com.jinke.wh_drugcontrol.me;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;

import org.xutils.view.annotation.ViewInject;

import cn.com.jinke.wh_drugcontrol.BuildConfig;
import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.PermissionUI;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.utils.GestureARouterUtil;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.PermissionsChecker;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by jinke on 2017/7/17.
 * 启动页
 */
public class BootUI extends ProjectBaseUI {

    private int[] MSG = new int[]{BOOT_MSG};

    private final static String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION};

    /**
     *  权限检测器
     */
    private PermissionsChecker mChecker;

    @ViewInject(R.id.boot_logo_iv)
    private ImageView mBootLogoIV;

    @ViewInject(R.id.boot_title_iv)
    private ImageView mBootTitleIV;

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case BOOT_MSG:
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
            finish();
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
        setContentView(R.layout.ui_boot);
    }

    @Override
    protected void onInitData() {}

    @Override
    protected void onInitView() {
        if(BUILD_TYPE.equals(BuildConfig.BUILD_NAME)){
            mBootLogoIV.setBackgroundResource(R.drawable.h_boot_logo);
            mBootTitleIV.setBackgroundResource(R.drawable.h_boot_title);
//            mBootLogoIV.setBackgroundResource(R.drawable.p_boot_logo);
//            mBootTitleIV.setBackgroundResource(R.drawable.p_boot_title);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mChecker = new PermissionsChecker(this);
        if (mChecker.lacksPermissions(PERMISSIONS)) {
            PermissionUI.startActivityForResult(this, PERMISSIONS);
        } else {
            MessageProxy.sendEmptyMessageDelay(BOOT_MSG, BOOTTIME);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == PermissionUI.REQUEST_CODE && resultCode == PermissionUI.PERMISSIONS_DENIED) {
            MessageProxy.sendEmptyMessage(FINISH_ALL_ACTIVITY);
        } else {
            MessageProxy.sendEmptyMessageDelay(BOOT_MSG, BOOTTIME);
        }
    }
}
