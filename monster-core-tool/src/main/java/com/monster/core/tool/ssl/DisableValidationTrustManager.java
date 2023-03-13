package com.monster.core.tool.ssl;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 不进行证书校验的TrustManager
 * <p>
 * Create on 2023/3/13 11:18
 *
 * @author monster gan
 */
public class DisableValidationTrustManager implements X509TrustManager {

    public static final X509TrustManager INSTANCE = new DisableValidationTrustManager();

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

}
