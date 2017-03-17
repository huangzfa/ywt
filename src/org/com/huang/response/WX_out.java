package org.com.huang.response;

import java.util.Date;

import org.com.huang.entity.WX_In;

public abstract class WX_out {
   private String ToUserName=null;
   private String FromUserName=null;
   private String CreateTime=null;
   private String MsgType=null;
   public abstract String getReturnStr();
   public WX_out(WX_In in){
	   super();
	   ToUserName=in.getFromUserName();
	   FromUserName=in.getToUserName();
	   CreateTime=new Date().getTime()+"";
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
}
