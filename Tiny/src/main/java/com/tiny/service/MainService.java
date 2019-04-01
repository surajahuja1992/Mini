package com.tiny.service;

import com.tiny.utils.LogDTO;
import com.tiny.utils.RequestXMLDTO;
import com.tiny.utils.UrlDTO;

public interface MainService {

	public void saveCSV(String filePath) throws Exception;
	public UrlDTO loadDataBasedShortURL(String shortUrls) throws Exception;
	public void saveTraficLog(LogDTO dto) throws Exception;
	public String sendDocToDMS(RequestXMLDTO xmlDto) throws Exception;
}
