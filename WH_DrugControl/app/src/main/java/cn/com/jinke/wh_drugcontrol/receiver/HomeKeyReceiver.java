package cn.com.jinke.wh_drugcontrol.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * HomeKeyReceiver
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/1
 */

public class HomeKeyReceiver extends BroadcastReceiver {

    private final String SYSTEM_DIALOG_REASON_KEY = "reason";
    private final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
    private final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    private final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

    private OnHomeKeyListener onHomeKeyListener;

    public HomeKeyReceiver setOnHomeKeyListener(OnHomeKeyListener listener) {
        this.onHomeKeyListener = listener;
        return this;
    }

    public void register(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.registerReceiver(this, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (reason != null) {
                if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                    // 按下HomeKey
                    if (onHomeKeyListener != null) {
                        onHomeKeyListener.onHomePressed();
                    }

                } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                    // 按下RecentApps
                    if (onHomeKeyListener != null) {
                        onHomeKeyListener.onHomeLongPressed();
                    }
                }
            }

        }
    }

    public interface OnHomeKeyListener {
        void onHomePressed();

        void onHomeLongPressed();
    }

}
