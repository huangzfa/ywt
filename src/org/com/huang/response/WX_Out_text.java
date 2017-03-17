package org.com.huang.response;

import org.com.huang.entity.WX_In;

public  class WX_Out_text extends WX_out{
	
   public WX_Out_text(WX_In in) {
		super(in);
		this.setMsgType("text");
		// TODO Auto-generated constructor stub
	}

	public String getContent() {
		return Content;
	}
	
	public void setContent(String content) {
		Content = content;
	}
	 
	private String Content=null;

	@Override
	public String getReturnStr() {
		StringBuffer strb=new StringBuffer();
		strb.append("<xml>                                             ");
		strb.append(" <ToUserName><![CDATA["+this.getToUserName()+"]]></ToUserName>      ");
		strb.append(" <FromUserName><![CDATA["+this.getFromUserName()+"]]></FromUserName>");
		strb.append(" <CreateTime>"+this.getCreateTime()+"</CreateTime>              ");
		strb.append(" <MsgType><![CDATA["+this.getMsgType()+"]]></MsgType>              ");
		strb.append(" <Content><![CDATA["+this.getContent()+"]]></Content>    ");
		strb.append(" </xml>                                           ");
		return strb.toString();
	}
}
