package cn.com.jinke.wh_drugcontrol.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class ScreenReceiver extends BroadcastReceiver {

    private ScreenReceiver.OnScreenListener onScreenListener;

    public ScreenReceiver setOnScreenListener(OnScreenListener listener) {
        this.onScreenListener = listener;
        return this;
    }

    public void register(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        context.registerReceiver(this, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SCREEN_ON)) {
            // 屏幕点亮
            if (onScreenListener != null) {
                onScreenListener.onScreenOn();
            }

        } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            // 屏幕熄灭
            if (onScreenListener != null) {
                onScreenListener.onScreenOff();
            }
        }
    }

    public interface OnScreenListener {
        void onScreenOn();

        void onScreenOff();
    }
}
