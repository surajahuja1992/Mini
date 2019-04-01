package com.tiny.dto;

public class CommonResponseDTO {

	private boolean resultFlag;
	private String pdfString;
	
	public boolean isResultFlag() {
		return resultFlag;
	}
	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}
	public String getPdfString() {
		return pdfString;
	}
	public void setPdfString(String pdfString) {
		this.pdfString = pdfString;
	}
	
}
