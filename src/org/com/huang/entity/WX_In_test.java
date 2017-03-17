package org.com.huang.entity;

import org.com.huang.util.WX_Util;

public class WX_In_test extends WX_In{
    private String Content=null;
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public WX_In_test(String requestStr) {
		super(requestStr);
		Content=WX_Util.getXMLCDATA(requestStr,"Content");
		// TODO Auto-generated constructor stub
	}

}
