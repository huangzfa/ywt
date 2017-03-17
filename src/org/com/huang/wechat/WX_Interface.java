package org.com.huang.wechat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.com.huang.entity.HuoDong;
import org.com.huang.entity.WX_In;
import org.com.huang.entity.WX_In_test;
import org.com.huang.response.WX_Out_text;
import org.com.huang.response.WX_out;
import org.com.huang.service.HuoDongService;
import org.com.huang.util.WX_Util;

public class WX_Interface extends HttpServlet {

	/**
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//String str=WX_Util.validate(request);
		PrintWriter out = response.getWriter();
         System.out.println(1);
		out.flush();
		out.close();
	}

	/**

	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String str=WX_Util.getStringInputStream(request);//报文解析字符串
		System.out.println(str);
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
		out.print(out_text.getReturnStr());								
		out.flush();
		out.close();
	}

}
