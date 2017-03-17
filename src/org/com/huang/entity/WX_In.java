package org.com.huang.entity;

import org.com.huang.util.WX_Util;

public class WX_In {
   private String ToUserName=null;
   private String FromUserName=null;
   private String CreateTime=null;
   private String MsgType=null;
   private String MsgId=null;
   public WX_In(String requestStr){
	   super();
	   ToUserName=WX_Util.getXMLCDATA(requestStr,"ToUserName");
	   FromUserName=WX_Util.getXMLCDATA(requestStr,"FromUserName");
	   CreateTime=WX_Util.getXMLCDATA(requestStr,"CreateTime");
	   MsgType=WX_Util.getXMLCDATA(requestStr,"MsgType");
	   MsgId=WX_Util.getXMLCDATA(requestStr,"MsgId");
   }
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}
