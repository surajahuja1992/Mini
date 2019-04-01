package com.tiny.service;

import org.springframework.stereotype.Service;

import com.tiny.utils.LogDTO;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

@Service
public class LogOfDevice {

	public String systemInfo(LogDTO dto) {
		try {
			System.out.println(dto.getIpAddress());
			//DB operation 
			System.out.println(populateRequestedDeviceDetails(dto.getDeviceInfo()));
		} catch (Exception e) {
			// TODO: handle exception
		}// TODO Auto-generated method stub
		return "http://www.pdf995.com/samples/pdf.pdf";
	}

	public String populateRequestedDeviceDetails(String userAgentString) {
		String deviceDetails = "";
		String browserAndVersion = "";
		String osAndDevice = "";
		try {
		if (userAgentString!=null) {
		UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);

		OperatingSystem operatingSystem = userAgent.getOperatingSystem();
		osAndDevice = operatingSystem.getName() + " - " + operatingSystem.getDeviceType();

		Browser browser = userAgent.getBrowser();
		browserAndVersion = browser.getName() + " - " + userAgent.getBrowserVersion();

		}
		deviceDetails = browserAndVersion + "," + osAndDevice;
		} catch (Exception exception) {
		throw exception;
		}
		return deviceDetails;
		}
	
}
