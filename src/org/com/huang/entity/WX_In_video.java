package org.com.huang.entity;

import org.com.huang.util.WX_Util;

public class WX_In_video extends WX_In{
	private String mediaId=null;
	private String ThumbMediaId=null;		
    public WX_In_video(String requestStr) {
		super(requestStr);
		mediaId=WX_Util.getXMLCDATA(requestStr,"mediaId");
		ThumbMediaId=WX_Util.getXMLCDATA(requestStr,"ThumbMediaId");
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}


}
