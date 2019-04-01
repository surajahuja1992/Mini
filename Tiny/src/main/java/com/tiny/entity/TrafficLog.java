package com.tiny.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "traffic_log", schema = "logger_qauser", catalog = "logger_qa")
public class TrafficLog {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name = "url", length = 500)
	private String url;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_time", nullable = false, length = 19)
	private Date dataTime;
	@Column(name = "device_info", length = 500)
	private String diviceInfo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

	public String getDiviceInfo() {
		return diviceInfo;
	}

	public void setDiviceInfo(String diviceInfo) {
		this.diviceInfo = diviceInfo;
	}

}
