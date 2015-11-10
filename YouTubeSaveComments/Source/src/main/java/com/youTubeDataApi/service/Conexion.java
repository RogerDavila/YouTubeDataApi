package com.youTubeDataApi.service;


import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class Conexion{
	
	public Connection Conectar(){
		Connection conn = null; 
	try {
		
		conn = (Connection) DriverManager.getConnection("jdbc:mysql://107.170.221.16:3306/YOUTUBE1_V2?" +
	                                   "user=root&password=");

	} catch (SQLException ex) {
	    // handle any errors
	    System.out.println("SQLException: " + ex.getMessage());
	    System.out.println("SQLState: " + ex.getSQLState());
	    System.out.println("VendorError: " + ex.getErrorCode());
	}
	return conn;
	
	}



}
