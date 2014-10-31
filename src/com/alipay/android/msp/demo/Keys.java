/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */
package com.alipay.android.msp.demo;

/**
* 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
* 这里签名时，只需要使用生成的RSA私钥。
* Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。*/
public final class Keys {

	/**合作身份者id，以2088开头的16位纯数字*/
	public static final String DEFAULT_PARTNER = "2088511684686317";

	/**收款支付宝账号*/
	public static final String DEFAULT_SELLER = "finedining@ics.smg.cn";
	
	/**MD5*/
	public static final String DEFAULT_MD5 = "e8q1idqgp2y6hqjnfaxca5zymsc9moac";

	/**商户私钥，自助生成*/
	public static final String PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALx7F/TH0TiNQBjopFxHUE07oEN71kms7Sjnfa77B7zK4i9V0gdUFAlMHo5iC2tPLyeN25/iiQz5l2LVv69JxyB8BD4nC+GeIQx+zlVY6sYSV0KitRVAK+pxJmkDrrX6WD6tqq1oGpHlP50qBZUHBbFn2E9RTg46TEZrihLatgFvAgMBAAECgYAw0PjdbztjGEJ3sRmQxO+pcT4K/HH91wn9xCcrQN06Kpn2hJXHTCF8XvizC/XFL1cZOYaenH++mo55DL9+2O73qqhv457agW/aSY0yHDa//iXex4MMpWVhtKCiNH8HCRUUeTdBKi1ExljvsEoKBdz0pk9JoRbmxOkdpgt1MUuxcQJBAPBPCIl0EB92oZLK9+iVELTn2xHPApHwVX/lfDTpAXtGrOWdwC+eXzvljNmBKIPdUxAvXGcvqnWryMo2LBnYrWMCQQDIybZqAHRf9Byn6T+OLxh/8dGZ3CWOcJAdtbyefdysQJJCdY8eRCteILERqPpImFQofBiky1nY1qBUyRljEm+FAkAuS+unH5cjmKVUNIpCYsWPNMP90FX8a8LtGvcs8l74swsISbYG9on+biEOnlWB4hfonFc/Ae3jMa9DVeB1MktlAkB/J3a0tGr+ZRpMn6LXCS4rwFkrZcYG6XPqzK+Rc0VvdEjre4VR9nu8VVmtRGST2pvZM5VrrzfL4UeRyfSg0Aj9AkBg9y/3m2y3bQDnCdnu4Q49AASAvybXJRFSN3nRJ5QKiVppSiiosyM7yvC1cV5VXQ+ks42npetMHsmaBWM6BIFb";

	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}
