package com.ashish.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RestDBUtil {

	private static String driver = "org.apache.derby.jdbc.ClientDriver";
	private static String url = "jdbc:derby://localhost:1527/restdb";

	static {
		try {
			Class.forName(driver);
			System.out.println("Driver Loaded...");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getCon() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			System.out.println("Connection to restdb establish...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void close(Connection con) {
		if (con != null) {
			try {
				con.close();
				System.out.println("rest db connection close...");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
