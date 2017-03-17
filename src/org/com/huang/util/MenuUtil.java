package org.com.huang.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import org.com.huang.entity.Button;
import org.com.huang.entity.CommonButton;
import org.com.huang.entity.ComplexButton;
import org.com.huang.entity.Menu;
import org.com.huang.entity.ViewButton;

/**
 * 创建微信菜单的Util
 * @author Administrator
 *
 */
public class MenuUtil {
	/**
	 * 封装微信菜单数据
	 * @return
	 */
	public static Menu getMenu(){
		//首先创建二级菜单
		CommonButton cb_1 = new CommonButton();
		cb_1.setKey("key1");
		cb_1.setName("子菜单1");
		cb_1.setType("click");
		
		CommonButton cb_2 = new CommonButton();
		cb_2.setKey("key2");
		cb_2.setName("子菜单2");
		cb_2.setType("click");
		
		//创建第一个一级菜单
		ComplexButton cx_1 = new ComplexButton();
		cx_1.setName("一级菜单");
		cx_1.setSub_button(new Button[]{cb_1,cb_2});
		
		//继续创建二级菜单
		CommonButton cb_3 = new CommonButton();
		cb_3.setKey("key3");
		cb_3.setName("子菜单3");
		cb_3.setType("click");
		
		ViewButton cb_4 = new ViewButton();
		cb_4.setName("访问网页");
		cb_4.setType("view");
		//需要使用网页授权获取微信用户的信息		
        String redirect_uri=WX_Util.urlEncodeUTF8("http://16h7554b37.imwork.net/wechat/OAuthServlet");
		cb_4.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+TokenThread.appID+"&redirect_uri="+redirect_uri+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
		
		//创建第二个一级菜单
		ComplexButton cx_2 = new ComplexButton();
		cx_2.setName("一级菜单");
		cx_2.setSub_button(new Button[]{cb_3,cb_4});

		//封装菜单数据
		Menu menu=new Menu();
		menu.setButton(new ComplexButton[]{cx_1,cx_2});
		
		return menu;
	}
	/**
	* 创建自定义菜单(每天限制1000次)
	* */
	public static int createMenu(Menu menu) {
		String jsonMenu = JSONObject.fromObject(menu).toString();
		System.out.println("菜单：" + jsonMenu);
		String path = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
				+ TokenThread.access_token.getAccess_token();
		int status = 0;
		try {
			URL url = new URL(path);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setDoOutput(true);
			http.setDoInput(true);
			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.connect();
			OutputStream os = http.getOutputStream();
			os.write(jsonMenu.getBytes("UTF-8"));
			os.close();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] bt = new byte[size];
			is.read(bt);
			String message = new String(bt, "UTF-8");
			JSONObject jsonMsg = JSONObject.fromObject(message);
			status = Integer.parseInt(jsonMsg.getString("errcode"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return status;
	}
}
