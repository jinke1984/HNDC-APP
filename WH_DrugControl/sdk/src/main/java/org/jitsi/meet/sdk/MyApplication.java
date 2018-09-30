package org.jitsi.meet.sdk;

import android.app.Application;

import com.facebook.react.modules.network.OkHttpClientProvider;
import com.facebook.react.modules.network.ReactCookieJarContainer;

import java.io.IOException;
import java.io.InputStream;
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

import okhttp3.OkHttpClient;

/**
 * Created by scg on 18-1-25.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        OkHttpClientProvider.replaceOkHttpClient(newOkHttpClient());

        super.onCreate();
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

    /**
     * Returns a trust manager that trusts {@code certificates} and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a {@code
     * SSLHandshakeException}.
     * <p>
     * This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     * <p>
     * Warning: Customizing Trusted Certificates is Dangerous!
     * <p>
     * Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * administrator.
     */
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
}
