package cn.com.jinke.wh_drugcontrol.manager;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.RejectedExecutionException;

import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;

/**
 * Created by jinke on 2017/9/20.
 */

public class AutoFocusManager implements Camera.AutoFocusCallback, CodeConstants{

    private static final long AUTO_FOCUS_INTERVAL_MS = 2000L;
    private static final Collection<String> FOCUS_MODES_CALLING_AF;
    static {
        FOCUS_MODES_CALLING_AF = new ArrayList<>(2);
        FOCUS_MODES_CALLING_AF.add(Camera.Parameters.FOCUS_MODE_AUTO);
        FOCUS_MODES_CALLING_AF.add(Camera.Parameters.FOCUS_MODE_MACRO);
    }

    private boolean stopped;
    private boolean focusing;
    private final boolean useAutoFocus;
    private final Camera camera;
    private AsyncTask<?, ?, ?> outstandingTask;

    public AutoFocusManager(Context context, Camera camera) {
        this.camera = camera;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String currentFocusMode = camera.getParameters().getFocusMode();
        useAutoFocus = sharedPrefs.getBoolean(KEY_AUTO_FOCUS, true)
                && FOCUS_MODES_CALLING_AF.contains(currentFocusMode);
        AppLogger.e("Current focus mode '" + currentFocusMode + "'; use auto focus? " + useAutoFocus);
        start();
    }

    @Override
    public synchronized void onAutoFocus(boolean success, Camera theCamera) {
        focusing = false;
        autoFocusAgainLater();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private synchronized void autoFocusAgainLater() {
        if (!stopped && outstandingTask == null) {
            AutoFocusTask newTask = new AutoFocusTask();
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                    newTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    newTask.execute();
                }
                outstandingTask = newTask;
            } catch (RejectedExecutionException ree) {
                AppLogger.e("Could not request auto focus"+ree);
            }
        }
    }

    public synchronized void start() {
        if (useAutoFocus) {
            outstandingTask = null;
            if (!stopped && !focusing) {
                try {
                    camera.autoFocus(this);
                    focusing = true;
                } catch (RuntimeException re) {
                    // Have heard RuntimeException reported in Android 4.0.x+;
                    // continue?
                    AppLogger.e("Unexpected exception while focusing"+re);
                    // Try again later to keep cycle going
                    autoFocusAgainLater();
                }
            }
        }
    }

    private synchronized void cancelOutstandingTask() {
        if (outstandingTask != null) {
            if (outstandingTask.getStatus() != AsyncTask.Status.FINISHED) {
                outstandingTask.cancel(true);
            }
            outstandingTask = null;
        }
    }

    public synchronized void stop() {
        stopped = true;
        if (useAutoFocus) {
            cancelOutstandingTask();
            // Doesn't hurt to call this even if not focusing
            try {
                camera.cancelAutoFocus();
            } catch (RuntimeException re) {
                // Have heard RuntimeException reported in Android 4.0.x+;
                // continue?
                AppLogger.e("Unexpected exception while cancelling focusing"+re);
            }
        }
    }

    private final class AutoFocusTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object... voids) {
            try {
                Thread.sleep(AUTO_FOCUS_INTERVAL_MS);
            } catch (InterruptedException e) {
                // continue
            }
            start();
            return null;
        }
    }
}
