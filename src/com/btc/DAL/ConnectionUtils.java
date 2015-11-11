package com.btc.DAL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteConnection;

public class ConnectionUtils {

	private static Connection _connection;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (_connection == null || _connection.isClosed())
			_connection = SqliteConnectionUtils.getConnection();
		return _connection;
	}
	
	public static ResultSet executeQuery(String sql) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionUtils.getConnection();
		Statement stmt = conn.createStatement();
		
        return  stmt.executeQuery (sql);	         
	}
	
	public static void main(String[] args) throws SQLException,
    ClassNotFoundException {
		
		System.out.println("Get connection ... ");
		
		Connection conn = ConnectionUtils.getConnection();
		executeQuery("SELECT * FROM Bukken");
		System.out.println("Get connection " + conn);
		
		System.out.println("Done!");
	}
}
