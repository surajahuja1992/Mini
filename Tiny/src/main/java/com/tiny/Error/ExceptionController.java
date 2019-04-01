package com.tiny.Error;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ExceptionController extends ResponseEntityExceptionHandler {
	@Autowired
	ErrorLogger errorLog;

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
	 StringWriter errors = new StringWriter(); ex.printStackTrace(new PrintWriter(errors));;
	  ErrorDetails errorDetails = new ErrorDetails(new Date(), errors.toString(),
	      request.getDescription(false));
	  ErrorLogger.writeLog(errorDetails);
	  System.out.println(errorDetails);
	  return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}