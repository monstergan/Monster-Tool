package com.monster.core.tool.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 不进行证书校验的HostnameVerifier,信任所有 host name
 * Create by monster gan on 2023/3/13 11:19
 */
public class TrustAllHostNames implements HostnameVerifier {

	public static final TrustAllHostNames INSTANCE = new TrustAllHostNames();

	@Override
	public boolean verify(String s, SSLSession sslSession) {
		return true;
	}

}
