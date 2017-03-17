package org.com.huang.util;

import org.com.huang.entity.Access_Token;
/**
 * 时时获取AccessToken的servlet
 * @author Administrator
 *
 */
public class TokenThread  implements Runnable{
	//微信公众号的凭证和秘钥
	//public static final String appID = "wx89d2752d77d7f39a";
	//public static final String appScret = "fd89f0b714465faa88d51766f53405f5";
	public static final String appID = "wx036a0365f8223428";
	public static final String appScret = "9db32532eecd9eea65792e563772aff7";
	public static Access_Token access_token=null;

	@Override
	public void run() {
		while (true) {
			try {
				// 调用工具类获取access_token(每日最多获取100000次，每次获取的有效期为7200秒)
				access_token = AccessTokenUtil.getAccessToken(appID, appScret);

				if (null != access_token) {
					System.out.println("accessToken获取成功："
							+ access_token.getExpires_in());
					// 7000秒之后重新进行获取
					Thread.sleep((access_token.getExpires_in() - 200) * 1000);
				} else {
					// 获取失败时，60秒之后尝试重新获取
					Thread.sleep(60 * 1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		
	}

}
