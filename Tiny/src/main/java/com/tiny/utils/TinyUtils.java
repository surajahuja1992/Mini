package com.tiny.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiny.entity.TrafficLog;
import com.tiny.sendXmlPojo.FIXML;
import com.tiny.sendXmlPojo.FIXML.Body;
import com.tiny.sendXmlPojo.FIXML.Body.CCAddDoc;
import com.tiny.sendXmlPojo.FIXML.Body.CCAddDoc.DataClassAttribs;
import com.tiny.sendXmlPojo.FIXML.Body.CCAddDoc.DataClassAttribs.IndexInfo;
import com.tiny.sendXmlPojo.FIXML.Body.CCAddDoc.DocumentProps;
import com.tiny.sendXmlPojo.FIXML.Header;
import com.tiny.sendXmlPojo.FIXML.Header.RequestHeader;
import com.tiny.sendXmlPojo.FIXML.Header.RequestHeader.MessageKey;
import com.tiny.sendXmlPojo.FIXML.Header.RequestHeader.RequestMessageInfo;
import com.tiny.sendXmlPojo.FIXML.Header.RequestHeader.Security;
import com.tiny.sendXmlPojo.FIXML.Header.RequestHeader.Security.Token;
import com.tiny.sendXmlPojo.FIXML.Header.RequestHeader.Security.Token.PasswordToken;
import com.tiny.serviceImpl.MainServiceImpl;
import com.tiny.viewXmlPojo.FIXML.Body.ViewDocumentsRequest;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;


@Service
public class TinyUtils {
	@Autowired
	MainServiceImpl mainServiceImple;


	// Variables to read the CSV
	static String seperator = ",";
	//public String loc = "D:\\RBL_TinyTest\\Updated CSV\\Path.csv";
	public String loc = "C:\\Users\\c5109\\Desktop\\TinyUrl Test\\Updated CSV\\Path.csv";
	// Variables for converting csv values into random numbers
	public static String charsetStr = "abcdefghijklmnopqrstuvwxyzABCEDFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public final int base = charsetStr.length();
	private AtomicInteger counter = new AtomicInteger(10);

	public List<InputCsvDto> readCSV(String location) throws Exception {
		List<InputCsvDto> list = new ArrayList<InputCsvDto>();
		try {
			FileReader fr = new FileReader(location);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			//br.readLine();
			while ((line = br.readLine()) != null) {
				String[] lineValue = line.split(seperator);
				InputCsvDto csvDto =  new InputCsvDto();
				csvDto.setPdfPath(lineValue[0]);
				csvDto.setCardNumber(lineValue[1]);
				csvDto.setPdfPages(lineValue[2]);
				list.add(csvDto);
			}
			br.close();
		} catch (Exception e) {
			throw e;
		}
		return list;

	}
	

	public List<UrlDTO> gererateURL(List<InputCsvDto> values) throws Exception {
		List<UrlDTO> list = new ArrayList<UrlDTO>();
		for (InputCsvDto csvDTO : values) {
			UrlDTO dto = new UrlDTO();
			RequestXMLDTO xmlDto = new RequestXMLDTO();
			final long nextNumber = getNextNumber();
			dto.setLongURL(csvDTO.getPdfPath());
			dto.setTinyURL(convertAndGetBase62Code(nextNumber));
			xmlDto.setDocument(encodePDF(csvDTO.getPdfPath()));
			xmlDto.setDocumentSize((int)getSize(csvDTO.getPdfPath()));
			xmlDto.setCardNumber(csvDTO.getCardNumber());
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			String strDate = sdf.format(date);
			xmlDto.setMessageDateTime(strDate);
			int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
			String updatedMonth = ((month < 10) ? "0" : "") + month;
			xmlDto.setMonth(updatedMonth);
			xmlDto.setNoOfPages(csvDTO.getPdfPages());
			xmlDto.setYear(Calendar.getInstance().get(Calendar.YEAR));
			dto.setDocumentIndex(mainServiceImple.sendDocToDMS(xmlDto));			
			//dto.setDocumentIndex("0");
			list.add(dto);
		}
		try {
			writerCSV(list, loc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return list;

	}
	public void writerCSV(List<UrlDTO> shortUrls, String loc) throws Exception {
		try {
			FileWriter fw = new FileWriter(loc, false);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			shortUrls.forEach(data -> {
				pw.println(data.getLongURL() + "," + data.getTinyURL() + "," + data.getDocumentIndex());
			});
			pw.flush();
			pw.close();
		} catch (Exception e) {
			throw e;
		}
	}


	private long getNextNumber() {
		int counterValue = counter.incrementAndGet();
		long systemTime = Long.valueOf("" + counterValue + System.currentTimeMillis());
		return systemTime;

	}

	private String convertAndGetBase62Code(long num) {
		StringBuffer sb = new StringBuffer("");
		while (num > 0) {
			int remainder = (int) (num % base);
			sb.append(charsetStr.charAt(remainder));
			num = num / base;
		}
		return sb.toString();

	}

	public static void writerCSVLog(List<TrafficLog> shortUrls, String loc) throws Exception {
		try {
			FileWriter fw = new FileWriter(loc, false);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			shortUrls.forEach(data -> {
				pw.println(data.getDiviceInfo() + "," + data.getUrl() + "," + data.getDataTime());
			});
			pw.flush();
			pw.close();
		} catch (Exception e) {
			throw e;
		}
	}

	public static String populateRequestedDeviceDetails(String userAgentString) {
		String deviceDetails = "";
		String browserAndVersion = "";
		String osAndDevice = "";
		try {
			if (userAgentString != null) {
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
	

	public static boolean isEmpty(Object argString) {
		if ((argString == null)) {
			return true;
		} else {
			return false;
		}
	}
	public String encodePDF(String filePath) {
		String encodedString = "";
		try {
			byte[] input_file = Files.readAllBytes(Paths.get(filePath));
			byte[] encodedBytes = Base64.getEncoder().encode(input_file);
			encodedString = new String(encodedBytes);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return encodedString;
	}
	public long getSize(String filePath) {
		File file = new File(filePath);
		long pdfSize = file.length();
		return pdfSize;
	}
	public String generateSendingXml(RequestXMLDTO xmlDto) throws Exception {
		String xml=null;
		PasswordToken passwordToken = new PasswordToken("", "");
		FIXML fixml = new FIXML();
		Header header = new Header();
		RequestHeader requestHeaer = new RequestHeader(
				new MessageKey(xmlDto.getRequestUUID(), xmlDto.getServiceRequestID(), xmlDto.getServiceRequestVersion(),
						xmlDto.getChannelId(), ""),
				new RequestMessageInfo("", "", "", "", "", xmlDto.getMessageDateTime()), new Security(new Token(passwordToken), "", "", "", "", ""));
		header.setRequestHeader(requestHeaer);

		List<IndexInfo> indexInfoList = new ArrayList<IndexInfo>();
		for (int i = 1; i <= 10; i++) {
			IndexInfo info;
			switch (i) {
			case 4:
				 info=new IndexInfo(i, String.valueOf((xmlDto.getCardNumber())));
				break;
			case 5:
				 info=new IndexInfo(i, String.valueOf(xmlDto.getMonth()));
				break;
			case 6:
				 info=new IndexInfo(i, String.valueOf(xmlDto.getYear()));
				break;

			default:
				 info=new IndexInfo(i,"");
				break;
			}
			indexInfoList.add(info);
		}
		DataClassAttribs dataClassAttribs = new DataClassAttribs("", "Credit_Card", indexInfoList);
		DocumentProps documentProps = new DocumentProps(xmlDto.getBase64Encoded(), "", "", xmlDto.getDocumentName(),
				xmlDto.getDocumentType(), xmlDto.getDocNameExtn(), xmlDto.getNoOfPages(), xmlDto.getDocumentSize(), "",
				"", xmlDto.getDocument(), "");

		CCAddDoc ccAddDoc = new CCAddDoc(documentProps, dataClassAttribs);

		Body body = new Body(ccAddDoc);

		fixml.setHeader(header);
		fixml.setBody(body);
		StringWriter stringWriter=new StringWriter();
		
		try {
			JAXBContext context=JAXBContext.newInstance(FIXML.class);
			Marshaller mar = context.createMarshaller();
			mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
			//mar.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.finacle.com/fixml ******.xsd");
			mar.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.finacle.com/fixml ******.xsd");
		   // mar.setProperty("com.sun.xml.bind.namespacePrefixMapper", new DefaultNamespacePrefixMapper() );
			
			mar.marshal(fixml, stringWriter);
			xml = stringWriter.toString();
			//xml=xml.replaceFirst("<FIXML>",replaceString);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		return xml;

	}
	
	public String generateRecievingXml(String documentIndex) throws Exception {
		//String replaceString ="<FIXML xmlns=\"http://www.finacle.com/fixml\">";
		String xml=null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		String strDate = sdf.format(date);
		
		// TODO Auto-generated method stub
		com.tiny.viewXmlPojo.FIXML.Header.RequestHeader.MessageKey messageKey = new com.tiny.viewXmlPojo.FIXML.Header.RequestHeader.MessageKey("ATPL002", "viewDocument", 10.2F, "DMS", "");
		com.tiny.viewXmlPojo.FIXML.Header.RequestHeader.RequestMessageInfo requestMessageInfo = new com.tiny.viewXmlPojo.FIXML.Header.RequestHeader.RequestMessageInfo((byte)01, "GMT+05:30", "", "", "", strDate);
		com.tiny.viewXmlPojo.FIXML.Header.RequestHeader.Security security = new com.tiny.viewXmlPojo.FIXML.Header.RequestHeader.Security(new Token(new PasswordToken("", "")), "", "", "", "", "");
		
		ViewDocumentsRequest viewDocumentRequest =  new ViewDocumentsRequest(documentIndex, "", "", "");
		com.tiny.viewXmlPojo.FIXML.Body body = new com.tiny.viewXmlPojo.FIXML.Body(viewDocumentRequest);
		
		com.tiny.viewXmlPojo.FIXML.Header.RequestHeader requestHeader = new com.tiny.viewXmlPojo.FIXML.Header.RequestHeader(messageKey, requestMessageInfo, security);
		com.tiny.viewXmlPojo.FIXML.Header header= new com.tiny.viewXmlPojo.FIXML.Header(requestHeader);
		com.tiny.viewXmlPojo.FIXML fixml = new com.tiny.viewXmlPojo.FIXML(header, body);
		
		StringWriter stringWriter=new StringWriter();
		
		try {
			JAXBContext context=JAXBContext.newInstance(com.tiny.viewXmlPojo.FIXML.class);
			Marshaller mar = context.createMarshaller();
			mar.marshal(fixml, stringWriter);
			xml = stringWriter.toString();
			//xml=xml.replaceFirst("<FIXML>",replaceString);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			throw e;
		}		
		
		return xml;
	}

}
