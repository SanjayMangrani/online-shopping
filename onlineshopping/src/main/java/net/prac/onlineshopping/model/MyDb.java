package net.prac.onlineshopping.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDb {
	public Connection con;
	
	public Connection getCon() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/myusers", "root", "user");
		return con;
	}
}
