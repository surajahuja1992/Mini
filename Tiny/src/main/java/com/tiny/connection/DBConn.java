package com.tiny.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConn {

	protected Connection dbConnection(String[]...strings) throws Exception {
		Connection con = null;
		String connectionUrl = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			if(strings != null && strings.length >0)
				connectionUrl = "jdbc:sqlserver://localhost;databaseName=Mini;user=sa;password=india@123";
			else
				//connectionUrl = "jdbc:sqlserver://localhost;databaseName=Mini;user=sa;password=india@123";
				connectionUrl = "jdbc:sqlserver://10.80.49.71:21445;databaseName=logger_QA;user=logger_qauser;password=logger_qauser$321";
			con = DriverManager.getConnection(connectionUrl);
			System.out.println("Connection ==>"+con);
		} catch (Exception e) {
			throw e;
		} 
		return con;
	}
	
	public static void closeConncetion(Connection con,PreparedStatement stmt,ResultSet rs) {
		try {
			if(con != null)
				con.close();
			if(stmt != null)
				stmt.close();
			if(rs != null)
				rs.close();
		} catch (Exception e) {
		}
	}

}
