package org.com.huang.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.com.huang.entity.Access_Token;
import org.com.huang.entity.HuoDong;
import org.com.huang.entity.WX_In;
import org.com.huang.entity.WX_In_test;
import org.com.huang.response.WX_Out_text;
import org.com.huang.response.WX_out;
import org.com.huang.service.HuoDongService;
import org.com.huang.util.WX_Util;

public class wechat extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    PrintWriter out = response.getWriter();
		    String str=WX_Util.validate(request);
	        System.out.println("开始签名校验");
            out.print(str);
            out.flush();
            out.close();


	}

	/**
	 */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8"); //设置输出编码格式
        PrintWriter out = response.getWriter();
/*        System.out.println("请求进入");
        String str=WX_Util.getStringInputStream(request);
        System.out.println(str);*/
        //k客服回复
        Access_Token access_token=new Access_Token();
        String strJson = "{\"touser\" :\"oe7rSjlz1flhx7HP3-DnlgrpobqM\",";
        strJson += "\"msgtype\":\"text\",";
        strJson += "\"text\":{";
        strJson += "\"content\":\"Hello World\"";
        strJson += "}}";
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?&body=0&access_token=" + access_token.getAccess_token();
       
        //out.print(strb);
		out.flush();
        out.close();
       
    }
    /**
     * 排序
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String sort(String token, String timestamp, String nonce) {
        String[] strArray = { token, timestamp, nonce };
        Arrays.sort(strArray);

        StringBuilder sbuilder = new StringBuilder();
        for (String str : strArray) {
            sbuilder.append(str);
        }

        return sbuilder.toString();
    }
    /**
     * 接收消息并回复，
     * @param request
     * @return
     */
	public static String getReturnStr(HttpServletRequest request) throws ServletException, IOException{
		String str=getStringInputStream(request);//报文解析字符串

		WX_In in=new WX_In(str);//字符串标签值存到对象中
		WX_Out_text out_text=new WX_Out_text(in);//标签值给到输出对象
		WX_out wx_out=null;//初始化输出父对象
		//文本消息
		if(in.getMsgType().equals("text")){
			WX_In_test text=new WX_In_test(str);
			try{
				int price=Integer.parseInt(text.getContent());
				if(price>0){
					HuoDong huodong=new HuoDong();
					huodong.setOpenid(in.getFromUserName());
					huodong.setPrice(price);
					HuoDongService.save(huodong);
					int x=HuoDongService.queryByPrice(price);
					out_text.setContent("已有"+x+"人选择"+price+"元");
				}else{					
					out_text.setContent("只能接受大于0的数字");
				}
			}catch(Exception e){			
				out_text.setContent("只能接受数字");
			}
		}else{
			out_text.setContent("你选择的类型不支持");
		}
		wx_out=out_text;		
		return out_text.getReturnStr();	
	}
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
			System.out.println(bstr);
		if(reader!=null){
			reader.close();
		}
		if(rbuffer!=null){
			rbuffer.close();
		}
		return bstr.toString();
	}	
	
}
