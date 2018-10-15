package cn.com.jinke.wh_drugcontrol.booter;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import cn.com.jinke.wh_drugcontrol.manager.AppUpdateTimeManager;
import cn.com.jinke.wh_drugcontrol.thread.Dispatcher;
/**
 * Created by jinke on 2017/5/2.
 * APP整体单例
 */

public class ProjectApplication extends MultiDexApplication {

    private static Context mContext;

    private int activityCount=0;

    /**
     * 当期activity的弱应用
     */
    private static WeakReference<Activity> mCurrentActivity;

    /**
     * 监听 app 前后台的切换
     */
    ActivityLifecycleCall lifecycleCall = new ActivityLifecycleCall();

    @Override
    public void onCreate() {
        super.onCreate();
        //主线程也就是UI线程
        Dispatcher.init(Thread.currentThread());
        mContext = this;
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                String processName = appProcess.processName;
                if (processName.equals("cn.com.hy.hn_drugcontrol")) {
                    //自己的东西才初始化
                    APPManager.initSync();
                    APPManager.initAsync();
                }
            }
        }
        registerActivityLifecycleCallbacks(lifecycleCall);
    }

    @Override
    public void onTerminate() {
        unregisterActivityLifecycleCallbacks(lifecycleCall);
        super.onTerminate();
    }

    public static void setCurrentActivity(Activity activity) {
        if (activity == null) {
            if (mCurrentActivity != null) {
                mCurrentActivity.clear();
                mCurrentActivity = null;
            }
        } else {
            if (mCurrentActivity != null) {
                Activity act = mCurrentActivity.get();
                if (act != null && act.hashCode() == activity.hashCode()) {
                    return;
                }
            }
            mCurrentActivity = new WeakReference<Activity>(activity);
        }
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity != null ? mCurrentActivity.get() : null;
    }

    public static Context getContext() {
        return mContext;
    }

    public static void showToast(int resId) {
        showToast(getContext().getString(resId));
    }

    public static void showToast(final CharSequence text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    class ActivityLifecycleCall implements ActivityLifecycleCallbacks{
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            activityCount++;
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityCount--;
            if(activityCount ==0){
                AppUpdateTimeManager.getInstance().updateAppUseStatu(AppUpdateTimeManager.AppUseStatu.CHANGE2BG);
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
}
