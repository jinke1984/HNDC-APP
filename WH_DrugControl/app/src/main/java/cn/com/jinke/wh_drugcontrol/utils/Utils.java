package cn.com.jinke.wh_drugcontrol.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;

import java.util.List;

/**
 * Utils
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/1
 */

public class Utils {
    private Utils() {
    }

    /**
     * 判断当前应用程序处于前台还是后台
     */
    public static boolean isApplicationToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;

    }


    /**
     * 检测手机是否屏幕
     */
    private static boolean isScreenOn(Activity activity) {

        boolean isScreenOn;

        PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            isScreenOn = pm.isInteractive();
        } else {
            isScreenOn = pm.isScreenOn();
        }

        return isScreenOn;
    }
}
