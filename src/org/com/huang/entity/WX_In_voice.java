package org.com.huang.entity;

import org.com.huang.util.WX_Util;

public class WX_In_voice extends WX_In{
    private String mediaId=null;
    private String Format=null;
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	public WX_In_voice(String requestStr) {
		super(requestStr);
		Format=WX_Util.getXMLCDATA(requestStr,"Format");
		mediaId=WX_Util.getXMLCDATA(requestStr,"mediaId");
		// TODO Auto-generated constructor stub
	}

}
