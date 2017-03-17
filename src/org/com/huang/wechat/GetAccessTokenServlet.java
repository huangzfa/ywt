package org.com.huang.wechat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.com.huang.util.TokenThread;

public class GetAccessTokenServlet extends HttpServlet {
   
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		new Thread(new TokenThread()).start();
		// 启动定时获取access_token的线程
	}
}
