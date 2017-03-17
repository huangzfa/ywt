package org.com.huang.entity;

import org.com.huang.util.WX_Util;

public class WX_In_image extends WX_In{
    private String PicUrl=null;
    private String MediaId=null;
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public WX_In_image(String requestStr) {
		super(requestStr);
		MediaId=WX_Util.getXMLCDATA(requestStr,"MediaId");
		PicUrl=WX_Util.getXMLCDATA(requestStr,"PicUrl");		
	}

}
