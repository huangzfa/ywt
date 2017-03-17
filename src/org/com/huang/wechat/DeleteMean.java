package org.com.huang.wechat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.com.huang.util.TokenThread;
import org.com.huang.util.WX_Util;

public class DeleteMean extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int status = 0;
		String path = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="
				+ TokenThread.access_token.getAccess_token();
		JSONObject jsonObject = new JSONObject();
		try {
			URL url = new URL(path);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();

			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();
			OutputStream os = http.getOutputStream();
			os.flush();
			os.close();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			jsonObject= JSONObject.fromObject(message);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		status = Integer.parseInt(jsonObject.getString("errcode"));
		if (status == 0) {
			System.out.println("菜单删除成功");
		} else {
			System.out.println("菜单删除失败");
		}
		out.flush();
		out.close();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
	}

}
