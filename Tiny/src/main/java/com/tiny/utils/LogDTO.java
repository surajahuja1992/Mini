package com.tiny.utils;

/**
 * @author dell
 *
 */
public class LogDTO {

	private String ipAddress;
	private String tinyURL;
	private String deviceInfo;
	private String longUrl;
	
	
	public LogDTO() {
		super();
	}

	public LogDTO(String ipAddress, String tinyURL, String deviceInfo, String longUrl) {
		super();
		this.ipAddress = ipAddress;
		this.tinyURL = tinyURL;
		this.deviceInfo = deviceInfo;
		this.longUrl = longUrl;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public String getTinyURL() {
	return tinyURL;
}

public void setTinyURL(String tinyURL) {
	this.tinyURL = tinyURL;
}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	
	
}
