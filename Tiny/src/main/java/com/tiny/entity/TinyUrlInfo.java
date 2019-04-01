package com.tiny.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tiny_urls", schema = "logger_qauser", catalog = "logger_qa")
public class TinyUrlInfo {

	private Long id;
	private String longUrls;
	private String shortUrls;
	private String documentIndex;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "long_url", length = 300)
	public String getLongUrls() {
		return longUrls;
	}
	public void setLongUrls(String longUrls) {
		this.longUrls = longUrls;
	}
	@Column(name = "short_url", length = 50)
	public String getShortUrls() {
		return shortUrls;
	}
	public void setShortUrls(String shortUrls) {
		this.shortUrls = shortUrls;
	}
	@Column(name = "document_Index", length = 500)
	public String getDocumentIndex() {
		return documentIndex;
	}

	public void setDocumentIndex(String documentIndex) {
		this.documentIndex = documentIndex;
	}
	public TinyUrlInfo(Long id, String longUrls, String shortUrls,String documentIndex) {
		super();
		this.id = id;
		this.longUrls = longUrls;
		this.shortUrls = shortUrls;
		this.documentIndex = documentIndex;
	}
	public TinyUrlInfo() {
		super();
	}

	public TinyUrlInfo(String longUrls, String shortUrls,String documentIndex) {
		super();
		this.longUrls = longUrls;
		this.shortUrls = shortUrls;
		this.documentIndex = documentIndex;
	}
	
}
