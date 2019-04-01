package com.tiny.utils;

public class RequestXMLDTO {
	private String requestUUID="ATPL001";
	private String serviceRequestID="CreditCardAddDocs";
	private float serviceRequestVersion =10.2f;
	private String channelId="DMS";
	private String base64Encoded ="N";
	private String documentName ="Statement";
	private String documentType="N";
	private String docNameExtn="pdf";
	private String dataDefName="Credit_Card";
	
	private int year;
	private String month;
	private String cardNumber;
	private String document;
	private int documentSize;
	private String noOfPages;
	private String messageDateTime;
	
	public int getYear() {
		return year;
	}
	public void setYear(int i) {
		this.year = i;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String updatedMonth) {
		this.month = updatedMonth;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public int getDocumentSize() {
		return documentSize;
	}
	public void setDocumentSize(int documentSize) {
		this.documentSize = documentSize;
	}
	public String getNoOfPages() {
		return noOfPages;
	}
	public void setNoOfPages(String string) {
		this.noOfPages = string;
	}
	public String getMessageDateTime() {
		return messageDateTime;
	}
	public void setMessageDateTime(String messageDateTime) {
		this.messageDateTime = messageDateTime;
	}
	public String getRequestUUID() {
		return requestUUID;
	}
	public String getServiceRequestID() {
		return serviceRequestID;
	}
	public float getServiceRequestVersion() {
		return serviceRequestVersion;
	}
	public String getChannelId() {
		return channelId;
	}
	public String getBase64Encoded() {
		return base64Encoded;
	}
	public String getDocumentName() {
		return documentName;
	}
	public String getDocumentType() {
		return documentType;
	}
	public String getDocNameExtn() {
		return docNameExtn;
	}
	public String getDataDefName() {
		return dataDefName;
	}


}
