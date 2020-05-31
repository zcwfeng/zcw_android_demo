package com.example.android_fresco.image;

import android.annotation.SuppressLint;

import com.example.android_fresco.BuildConfig;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class OkHttp {

    public static final long DEFAULT_CONNECT_TIMEOUT = 20000;
    public static final long DEFAULT_READ_TIMEOUT = 120000;
    public static final long DEFAULT_WRITE_TIMEOUT = 120000;


    // The singleton HTTP client.
    private static final OkHttpClient sShareClient;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        configForDebug(builder);
        sShareClient = builder
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }

    /**
     * 测试环境抓包
     * @param builder
     */
    private static void configForDebug(OkHttpClient.Builder builder) {
        if (BuildConfig.DEBUG) {
            builder.hostnameVerifier(new TrustAllHostnameVerifier());
            builder.sslSocketFactory(createSSLSocketFactory());
        }
    }

    public static OkHttpClient getDefaultClient() {
        return sShareClient;
    }

    /**
     * 通过共享的 Client 创建一个新的 Builder
     */
    public static OkHttpClient.Builder newBuilder() {
        OkHttpClient.Builder builder = sShareClient.newBuilder();
        configForDebug(builder);
        return builder
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    /**
     * 默认信任所有的证书
     * @return
     */
    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
