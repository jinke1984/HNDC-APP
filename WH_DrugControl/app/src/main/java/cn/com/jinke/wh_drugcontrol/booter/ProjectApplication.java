package cn.com.jinke.wh_drugcontrol.booter;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.facebook.react.modules.network.OkHttpClientProvider;
import com.facebook.react.modules.network.ReactCookieJarContainer;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import cn.com.jinke.wh_drugcontrol.manager.AppUpdateTimeManager;
import cn.com.jinke.wh_drugcontrol.thread.Dispatcher;
import okhttp3.OkHttpClient;

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

        OkHttpClientProvider.replaceOkHttpClient(newOkHttpClient());

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

    /**
     * 获取一个OkHttpClient实例
     *
     * @return OkHttpClient
     */
    private OkHttpClient newOkHttpClient() {
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        InputStream inputStream;
        try {
            inputStream = this.getAssets().open("CA.cer");     //证书输入流

//            inputStream = new Buffer().writeUtf8(cer).inputStream();
            trustManager = trustManagerForCertificates(inputStream);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
            return new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, trustManager)
                    .connectTimeout(10, TimeUnit.SECONDS)    //设置连接超时
                    .readTimeout(10, TimeUnit.SECONDS)          //设置响应超时
                    .cookieJar(new ReactCookieJarContainer())
                    .build();
        } catch (GeneralSecurityException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
            return new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)    //设置连接超时
                    .readTimeout(10, TimeUnit.SECONDS)          //设置响应超时
                    .cookieJar(new ReactCookieJarContainer())
                    .build();  //发生安全类异常,返回一个没有证书的OkHttpClient
        } catch (IOException e) {
            e.printStackTrace();
            return new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)    //设置连接超时
                    .readTimeout(10, TimeUnit.SECONDS)          //设置响应超时
                    .cookieJar(new ReactCookieJarContainer())
                    .build();  //发生安全类异常,返回一个没有证书的OkHttpClient
        }
    }

    private X509TrustManager trustManagerForCertificates(InputStream inputStream)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates =
                certificateFactory.generateCertificates(inputStream);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }
        char[] password = "password".toCharArray();
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }
        KeyManagerFactory keyManagerFactory = KeyManagerFactory
                .getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            //添加自定义密码,使用默认密码
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, password);      // By convention, 'null' creates an empty key store.
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
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
