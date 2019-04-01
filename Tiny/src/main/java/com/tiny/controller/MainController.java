package com.tiny.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tiny.DOA.DBUtils;
import com.tiny.dto.CommonResponseDTO;
import com.tiny.service.LogOfDevice;
import com.tiny.serviceImpl.MailerServiceImpl;
import com.tiny.serviceImpl.MainServiceImpl;
import com.tiny.utils.Disclamer;
import com.tiny.utils.LogDTO;
import com.tiny.utils.TinyUtils;

@Controller
public class MainController {

	@Autowired
	LogOfDevice logOfDevice;
	
	@Autowired
	MainServiceImpl mainServiceImpl;
	
	@Autowired
	MailerServiceImpl mailService;
	
	@Autowired
	DBUtils dbUtil;
	
	//String location ="D:\\RBL_TinyTest\\Audit Trail\\log.csv";
	String location = "C:\\Users\\c5109\\Desktop\\TinyUrl Test\\Log\\Log.csv";
	//String csvFilePath = "D:\\RBL_TinyTest\\CSV\\Path.csv";
	private String csvFilePath = "C:\\Users\\c5109\\Desktop\\TinyUrl Test\\Input CSV\\Path.csv";
	
	
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = { "/{tinyUrl:.+}" }, method = RequestMethod.GET)
	 * public ResponseEntity<byte[]> method(@PathVariable String tinyUrl,
	 * HttpServletRequest request) { ResponseEntity<byte[]> response = null; try {
	 * List<LogDTO> list = new ArrayList<LogDTO>(); UrlDTO urlDTO =
	 * mainServiceImpl.loadDataBasedShortURL(tinyUrl); LogDTO dto = new LogDTO();
	 * dto.setTinyURL(urlDTO.getTinyURL());
	 * dto.setIpAddress(request.getRemoteAddr());
	 * dto.setDeviceInfo(TinyUtils.populateRequestedDeviceDetails(request.getHeader(
	 * "User-Agent"))); dto.setLongUrl(urlDTO.getLongURL());
	 * mainServiceImpl.saveTraficLog(dto); list.add(dto);
	 * mainServiceImpl.writeTrafficLog(list, location);
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setContentType(MediaType.parseMediaType("application/pdf")); String
	 * filename = urlDTO.getLongURL(); headers.add("content-disposition",
	 * "inline;filename=" + filename);
	 * headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	 * response = new
	 * ResponseEntity<byte[]>(Files.readAllBytes(Paths.get(filename)), headers,
	 * HttpStatus.OK); } catch (Exception exception) { exception.printStackTrace();
	 * } return response; }
	 */


	@RequestMapping(value = "/loadCSV", method = RequestMethod.GET)
	public @ResponseBody void loadCSV() throws Exception {
		try {
			mainServiceImpl.saveCSV(csvFilePath);
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}
	}
	
	
	@RequestMapping(value = "/{tinyUrl:.+}", method= RequestMethod.GET)
    public ModelAndView mainWithParam(Model model, @PathVariable String tinyUrl,Connection conn) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		int flag;
		flag = dbUtil.getFlag(tinyUrl, conn);
		if(flag==1)
			modelAndView.setViewName("Disclaimer");
		else
			modelAndView.setViewName("InactiveURL");
					
	  return modelAndView;
    }

	 

	@RequestMapping(value ="/sendMail")
	public @ResponseBody void sendMail() throws Exception
	{
		try {
			mailService.sendCSVLogs(location);
		} catch (MessagingException e) {
			throw e;
		}
	}
	

	@RequestMapping(value = "/processForm", method = RequestMethod.POST,produces = "application/json")
	public @ResponseBody CommonResponseDTO save(@RequestParam(value = "radioButtonName") String radioButtonName,
			@RequestParam(value = "tinyURL") String tinyURL,
			HttpServletRequest request,HttpServletResponse reposnse) throws ClassNotFoundException, IOException,
			InterruptedException {
		// byte[] documentBytes = null;
		String documentBytes = null;
		CommonResponseDTO responseDto = new CommonResponseDTO();
		if (radioButtonName != null && "Y".equals(radioButtonName)) {
			List<LogDTO> list = new ArrayList<LogDTO>();
			try {
				LogDTO dto = new LogDTO();
				dto.setTinyURL(tinyURL);
				dto.setIpAddress(request.getRemoteAddr());
				dto.setDeviceInfo(TinyUtils.populateRequestedDeviceDetails(request.getHeader("User-Agent")));
				dto.setLongUrl(mainServiceImpl.getLongUrl(tinyURL));
				mainServiceImpl.saveTraficLog(dto);
				list.add(dto);
				mainServiceImpl.writeTrafficLog(list, location); 
				Disclamer disClamerObject = new Disclamer(radioButtonName, tinyURL);
				documentBytes = mainServiceImpl.showPdf(disClamerObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (null != documentBytes) {
				responseDto.setPdfString(documentBytes);
				responseDto.setResultFlag(true);
			}
		} else { 
			responseDto.setResultFlag(false);
			responseDto.setPdfString(documentBytes);
		}
		return responseDto;
	}
	@RequestMapping(value="/decline" , method=RequestMethod.GET)
	public ModelAndView declineUrl() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Decline");
		return modelAndView;
		
	}
}
