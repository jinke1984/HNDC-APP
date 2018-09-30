package cn.com.jinke.wh_drugcontrol.utils;

import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ESHARE;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ETYPE;

/**
 * GestureARouterUtil
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/1
 */

public class GestureARouterUtil implements CodeConstants {

    /**
     * 导航到手势密码
     *
     * @param forceSetPwd true：强制进入设置手势密码
     * @param firstIn     true:首次进入app
     * @param targetUi    关闭手势界面后要进入的目标页面
     */
    public static void navToGesturePwdUi(boolean forceSetPwd, final boolean firstIn, final String targetUi) {
        final boolean useGesture = ShareUtils.getInstance().get(ESHARE.SYS, SP_KEY_USE_GESTURE, ETYPE.BOOL);
        final String password = ShareUtils.getInstance().get(ESHARE.SYS, SP_KEY_PASSWORD, ETYPE.STRING);
        boolean setPwd = false;

        if (forceSetPwd || TextUtils.isEmpty(password)) {
            setPwd = true;
        }

        if (useGesture) {
            ARouter.getInstance()
                    .build(RouteUtils.R_GESTURE_PWD_UI)
                    .withBoolean(EXTRA_SET_GESTURE_PASSWORD, setPwd)
                    .withBoolean(EXTRA_FIRST_IN_APP, firstIn)
                    .withString(TARGET_UI, targetUi)
                    .navigation();
        } else {
            if (firstIn) {
                ARouter.getInstance().build(RouteUtils.R_FRAMEWORK_UI)
                        .withString(TARGET_UI, targetUi)
                        .navigation();
            } else {
                if (targetUi != null) {
                    ARouter.getInstance().build(targetUi).navigation();
                }
            }
        }
    }

}
