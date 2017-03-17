package org.com.huang.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.com.huang.entity.Access_Token;
import org.com.huang.entity.SNSUserInfo;
import org.com.huang.entity.WeixinOauth2Token;

public class WX_Util {
	private final static String token = "huang";
	public static String validate(HttpServletRequest request) {
        String signature = request.getParameter("signature");//微信加密签名
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");//随机数
        String echostr = request.getParameter("echostr");//	随机字符串
        String str="";
        try{
        	if(null!=signature){
        		String[] arrTmp={token,timestamp,nonce};
        		Arrays.sort(arrTmp);
        		StringBuffer sb=new StringBuffer();
        		for(int i=0;i<arrTmp.length;i++){
        			sb.append(arrTmp[i]);
        		}
        		MessageDigest md=MessageDigest.getInstance("SHA-1");
        		byte[] bytes=md.digest(new String(sb).getBytes());
        		StringBuffer buf=new StringBuffer();
        		for(int i=0;i<bytes.length;i++){
        			if(((int)bytes[i] & 0xff)< 0x10){
        				buf.append("0");
        			}
        			buf.append(Long.toString((int)bytes[i] & 0xff,16));
        		}
        		if(signature.equals(buf.toString())){
        			str=echostr;
        		}
        	}
        }catch(Exception e){
        	
        }   
		return str;
	}
    /**
     * 从request得到微信服务器发来的报文
     * @param request
     * @return
     * @throws IOException
     */
	public static String getStringInputStream(HttpServletRequest request) throws IOException {
		InputStreamReader reader=null;
		BufferedReader rbuffer=null;
		StringBuffer bstr=null;
		reader= new InputStreamReader(
					request.getInputStream(),"UTF-8");
			 rbuffer= new BufferedReader(reader);
			 bstr=new StringBuffer();
			String str=null;
			if(null!=(str=rbuffer.readLine())){
				bstr.append(str);
			}
		if(reader!=null){
			reader.close();
		}
		if(rbuffer!=null){
			rbuffer.close();
		}
		return bstr.toString();
	}
	/**
	 * 从报文中读取标签内容
	 * @param requestStr 报文内容
	 * @param tagName    标签名
	 * @return
	 */
	public static String getXMLCDATA(String requestStr,String tagName){
		String geginTagName="<"+tagName+"><![CDATA[";
		String endTagName="]]></"+tagName+">";
		boolean flagIslong=false;
		if(tagName.equals("CreateTime")){
			flagIslong=true;
		}else if(tagName.equals("MsgId")){
			flagIslong=true;
		}else if(tagName.equals("Location_X")){
			flagIslong=true;
		}else if(tagName.equals("Location_Y")){
			flagIslong=true;
		}else if(tagName.equals("Scale")){
			flagIslong=true;
		}
		
		if(flagIslong){
			 geginTagName="<"+tagName+">";
			 endTagName  ="</"+tagName+">";
		}
		int beginIndex=requestStr.indexOf(geginTagName)+geginTagName.length();
		int endIndex=requestStr.indexOf(endTagName);
		//除去标签之外的内同就是我们要的数据
		System.out.println(requestStr);
		return requestStr.substring(beginIndex, endIndex).toString();
	}
	/**
	 * 网页授权凭证，获取用户token
	 * @param code
	 * @return
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String code){
		WeixinOauth2Token wat=new WeixinOauth2Token ();
		String appid = TokenThread.appID;
		String secret = TokenThread.appScret;
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid + "&secret=" + secret + "&code=" + code
				+ "&grant_type=authorization_code";
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject=httpsRequest(url,"GET",null);
            System.out.println(jsonObject.getString("scope"));
            wat.setAccessToken(jsonObject.getString("access_token"));
            wat.setExpiresIn(jsonObject.getInt("expires_in"));
            wat.setRefreshToken(jsonObject.getString("refresh_token"));
            wat.setOpenId(jsonObject.getString("openid"));
            wat.setScope(jsonObject.getString("scope"));
		}catch (Exception e) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
			System.out.println("获取网页授权凭证失败 errcode:{} errmsg:{}"+ errorCode+","+ errorMsg);
		}
		return wat;
	}
	/**
     * 发送https请求
     * 
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            System.out.println("请求超时");
        } catch (Exception e) {
        	System.out.println("https请求异常");
        }
        return jsonObject;
    }
    /**
     * 通过网页授权获取用户信息
     * 
     * @param accessToken 网页授权接口调用凭证,不是基础中的accessToken
     * @param openId 用户标识
     * @return SNSUserInfo
     */
    public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
		SNSUserInfo snsUserInfo = null;
		Access_Token token = new Access_Token();
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl
				.replace("ACCESS_TOKEN", token.getAccess_token()).replace(
						"OPENID", openId);
		// 通过网页授权获取用户信息
		JSONObject jsonObject = WX_Util.httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				snsUserInfo = new SNSUserInfo();
				// 用户的标识
				snsUserInfo.setOpenId(jsonObject.getString("openid"));
				// 昵称
				snsUserInfo.setNickname(jsonObject.getString("nickname"));
				// 性别（1是男性，2是女性，0是未知）
				snsUserInfo.setSex(jsonObject.getInt("sex"));
				// 用户所在国家
				snsUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				snsUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				snsUserInfo.setCity(jsonObject.getString("city"));
				// 用户头像
				snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				// 用户特权信息
				snsUserInfo.setPrivilegeList(JSONArray.toList(
						jsonObject.getJSONArray("privilege"), List.class));
			} catch (Exception e) {
				snsUserInfo = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
			}
		}
		return snsUserInfo;
	}  
    /**
     * URL编码（utf-8）
     * 
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    } 
}
