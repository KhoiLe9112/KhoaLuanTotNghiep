package com.dhkh.config;

import java.sql.Connection;
import java.sql.DriverManager;

import com.dhkh.util.Constant;

public class ConnectionProvider {
	/**
	 * Function to connect oracle DB
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection(Constant.CONNECT_URL, Constant.CONNECT_USERNAME, Constant.CONNECT_PASSWORD);
	}
}