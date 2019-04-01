package com.tiny.DOA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.tiny.connection.DBConn;
import com.tiny.utils.LogDTO;
import com.tiny.utils.UrlDTO;

public class DBUtils {
	public void sendData(List<UrlDTO> data, Connection conn) throws SQLException {
		int i = 0;
		PreparedStatement stmt = null;
		try {
			for (UrlDTO urlDTO : data) {
				i = 0;
				String querry = "INSERT INTO logger_qauser.tiny_urls(long_url,short_url,document_index) VALUES(?,?,?)";
				stmt = conn.prepareStatement(querry);
				stmt.setString(++i, urlDTO.getLongURL());
				stmt.setString(++i, urlDTO.getTinyURL());
				stmt.setString(++i, urlDTO.getDocumentIndex());
				stmt.execute();
			}
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}finally {
			DBConn.closeConncetion(null, stmt, null);
		}
	}
	public String getDocumentIndex(String tinyUrl,Connection conn) throws Exception {
		PreparedStatement stmt = null;	
		String documentIndex=null;
		ResultSet rs = null;
		try {
			String querry = "SELECT document_index FROM logger_qauser.tiny_urls WHERE short_url= ?";
			stmt = conn.prepareStatement(querry);
			stmt.setString(1, tinyUrl);
			rs = stmt.executeQuery();
			if(rs.next())
				documentIndex = rs.getString("document_index");
		} catch (SQLException e) {
			throw e;
		}finally {
			DBConn.closeConncetion(null, stmt, rs);
		}
		
		return documentIndex;
		
	}
	public void insertLog(LogDTO dto, Connection conn) throws Exception {
		int i = 0;
		PreparedStatement stmt = null;
		try {
			
				String querry = "INSERT INTO logger_qauser.device_log(pdf,date_time,ip_address,device_info) VALUES(?,?,?,?)";
				stmt = conn.prepareStatement(querry);
				stmt.setString(++i, dto.getLongUrl());
				stmt.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
				stmt.setString(++i, dto.getIpAddress());
				stmt.setString(++i, dto.getDeviceInfo());
				stmt.execute();
		} catch (Exception e) {
			throw e;
		}finally {
			DBConn.closeConncetion(null, stmt, null);
		}
		
	}
	public String getLongUrl(String tinyURL,Connection conn) throws Exception {
		PreparedStatement stmt = null;	
		String longUrl=null;
		ResultSet rs = null;
		String querry = "SELECT long_url FROM logger_qauser.tiny_urls WHERE short_url = ?";
		//String querry = "SELECT long_url FROM dbo.tiny_urls WHERE short_url = ?";
		try {
			stmt = conn.prepareStatement(querry);
			stmt.setString(1, tinyURL);
			rs = stmt.executeQuery();
			if(rs.next())
			 longUrl = rs.getString("long_url");
		} catch (SQLException e) {
			throw e;
		}finally {
			DBConn.closeConncetion(null, stmt, rs);
		}
		
		return longUrl;	
	}
	public int getFlag(String tinyURL,Connection conn) throws SQLException {
		PreparedStatement stmt = null;	
		int documentIndex = 0;
		ResultSet rs = null;
		try {
			String querry = "SELECT flag FROM logger_qauser.tiny_urls WHERE short_url= ?";
			stmt = conn.prepareStatement(querry);
			stmt.setString(1, tinyURL);
			rs = stmt.executeQuery();
			if(rs.next())
				documentIndex = rs.getInt("flag");
		} catch (SQLException e) {
			throw e;
		}finally {
			DBConn.closeConncetion(null, stmt, rs);
		}
		
		return documentIndex;
	}
	public int setFlag(String tinyURL,Connection conn) throws SQLException {
		PreparedStatement stmt = null;	
		int documentIndex = 0;
		ResultSet rs = null;
		try {
			String querry = "UPDATE TABLE logger_qauser.tiny_urls SET flag=0 WHERE ";
			stmt = conn.prepareStatement(querry);
			stmt.setString(1, tinyURL);
		} catch (SQLException e) {
			throw e;
		}finally {
			DBConn.closeConncetion(null, stmt, rs);
		}
		
		return documentIndex;
	}

}
