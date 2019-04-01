package com.tiny.serviceImpl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tiny.DOA.DBUtils;
import com.tiny.connection.DBConn;
import com.tiny.service.MainService;
import com.tiny.utils.Disclamer;
import com.tiny.utils.LogDTO;
import com.tiny.utils.RequestXMLDTO;
import com.tiny.utils.TinyUtils;
import com.tiny.utils.UrlDTO;


@Service
public class MainServiceImpl extends DBConn implements MainService {
	@Autowired
	TinyUtils tinyUtils;
	
	DBUtils callStmt = new DBUtils();
	
	private RestTemplate restTemplate;

	@Autowired
	public void HelloController(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}
	
	/*
	 * @Autowired TinyUrlRepository tinyUrlRepository;
	 */
	/*
	 * @Autowired TrafficLogRepository trafficLogRepository;
	 */
	@Autowired
	TinyUtils utils;
	
	@Override
	public void saveCSV(String filePath) throws Exception {
		Connection conn  = null; 
		try {
			List<UrlDTO> finalList = tinyUtils.gererateURL(tinyUtils.readCSV(filePath));
			conn = dbConnection();
			conn.setAutoCommit(false);
			callStmt.sendData(finalList, conn);
			conn.commit();
		} catch (Exception e) {
			System.out.println(e);
			conn.rollback();
			throw e;
		}finally {
			DBConn.closeConncetion(conn, null, null);
		}

	}
	
	@Override
	public UrlDTO loadDataBasedShortURL(String shortUrls) throws Exception {
		return null;
		/*
		 * UrlDTO dto = new UrlDTO(); try { List<TinyUrlInfo> dbList =
		 * tinyUrlRepository.findByShortUrls(shortUrls); if(!dbList.isEmpty()) {
		 * TinyUrlInfo info = dbList.get(dbList.size()-1);
		 * dto.setLongURL(info.getLongUrls()); dto.setTinyURL(info.getShortUrls()); }
		 * }catch(Exception exception) { throw exception; } return dto;
		 */
	}
	@Override
	public void saveTraficLog(LogDTO dto) throws Exception {
		/*
		 * try { TrafficLog trafficLog = new TrafficLog(); trafficLog.setDataTime(new
		 * Date()); trafficLog.setDiviceInfo(dto.getIpAddress() +
		 * " - "+dto.getDeviceInfo()); trafficLog.setUrl(dto.getLongUrl());
		 * trafficLogRepository.save(trafficLog); }catch (Exception e) { throw e; }
		 */
		Connection conn  = null; 
		try {
			conn = dbConnection();
			conn.setAutoCommit(false);
			callStmt.insertLog(dto,conn);;
			conn.commit();
		} catch (Exception e) {
			System.out.println(e);
			conn.rollback();
			throw e;
		}finally {
			DBConn.closeConncetion(conn, null, null);
		}
		
	}
	public void writeTrafficLog(List<LogDTO> logs, String loc) throws Exception {
		try {
			FileWriter fw = new FileWriter(loc, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			logs.forEach(data -> {
				pw.println(new Date() + ","  + data.getIpAddress() + "," + data.getDeviceInfo() + "," + data.getLongUrl());
			});
			pw.flush();
			pw.close();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public String sendDocToDMS(RequestXMLDTO xmlDto) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		String xml = tinyUtils.generateSendingXml(xmlDto);
		//System.out.println(xml);
		HttpEntity<String> request = new HttpEntity<String>(xml, headers);
		ResponseEntity<String> response = restTemplate
				.postForEntity("http://10.80.45.72:7080/rbl/generic/dms/cc_ssAddDocument", request, String.class);
		String data = response.getBody();
		
		final Pattern pattern = Pattern.compile("<documentIndex>(.+?)</documentIndex>", Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(data);
		matcher.find();
		String documentIndex = matcher.group(1); 
		return documentIndex;
	}
	public String recieveDoc(String documentIndex) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		String xml = tinyUtils.generateRecievingXml(documentIndex);
		//System.out.println(xml);
		HttpEntity<String> request = new HttpEntity<String>(xml, headers);
		ResponseEntity<String> response = restTemplate
				.postForEntity("http://10.80.45.72:7080/rbl/generic/dms/viewDocuments", request, String.class);
		String data = response.getBody();
		//System.out.println("View Response"+data);
		final Pattern pattern = Pattern.compile("<document>(.+?)</document>", Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(data);
		matcher.find();
		String pdf = matcher.group(1);
		return pdf;
	}
 
	public String showPdf(Disclamer disClamerObject) throws Exception {
		String documentIndex;
		Connection conn = null;
		try {
			conn = dbConnection();
			documentIndex = callStmt.getDocumentIndex(disClamerObject.getTinyURL(), conn);
			String pdf = recieveDoc(documentIndex);
			String base64String = pdf.replaceAll("\\s+", "");
			// byte[] decodedBytes = Base64.getDecoder().decode(base64String);
			return base64String;
		} catch (Exception e) {
			throw e;
		} finally {
			DBConn.closeConncetion(conn, null, null);
		}

	}
	public String getLongUrl(String tinyUrl) throws Exception {
		String longUrl=null;
		Connection conn  = null; 
		try {
			conn = dbConnection();
			longUrl =  callStmt.getLongUrl(tinyUrl,conn);
		}catch (Exception e) {
			throw e;
		}finally {
			DBConn.closeConncetion(conn, null, null);
		}
		return longUrl;
		
	}

	
	
}
