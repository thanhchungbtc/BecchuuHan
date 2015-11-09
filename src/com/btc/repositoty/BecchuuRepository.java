package com.btc.repositoty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.btc.DAL.ConnectionUtils;
import com.btc.model.Becchuu;
import com.btc.model.Bukken;

public class BecchuuRepository {
	private List<Becchuu> accounts;
	private Connection connection;
	BukkenRepository groupRepository;
	
	public BecchuuRepository(int groupID ) {
		accounts = new LinkedList<Becchuu>();
		groupRepository = new BukkenRepository();
		String sql = "SELECT * FROM Account";
		if (groupID != 0)
			sql = sql + " WHERE account_type = " + groupID;
		     try {
		         try {
		        	 ResultSet rs = ConnectionUtils.executeQuery (sql);		         
			         while (rs.next()) {
			        	 String userName = rs.getString(1);
			        	 String url = rs.getString(3);
			        	 String notes = rs.getString(4);
			        	 String title = rs.getString(5);
			        	 int group_id = rs.getInt(6);
			        	 
			        	 Becchuu account = new Becchuu();
			        	 account.userName = userName;
			        	 account.url = url;
			        	 account.notes = notes;
			        	 account.title = title;	           
			           accounts.add(account);
			         }
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} 
		         
		      }
		      catch(SQLException e){
		         System.out.println("SQL exception occured" + e);
		      }    
	}
	
	public List<Becchuu> getList() {
		return this.accounts;
	}
	
	public Becchuu insert(Becchuu account) throws SQLException {
		 String sql = "INSERT INTO Account (username, password, url, notes, title, account_type) VALUES(?, ?, ?, ?, ?, ?)";
	        try {
	          connection = ConnectionUtils.getConnection();
	        } catch (ClassNotFoundException ex) {
	          Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        
		    PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		    statement.setString(1, account.userName);
		    statement.setString(2, account.password);
		    statement.setString(3, account.url);
		    statement.setString(4, account.notes);
		    statement.setString(5, account.title);
		    statement.executeUpdate();
		    ResultSet key = statement.getGeneratedKeys();
		    
		    statement.close();
		    statement = null;
		    accounts.add(account);
		    connection.close();
		    return account;
	}
	
	public Becchuu update(Becchuu account) throws SQLException {
		 String sql = "UPDATE Account SET password = ?, url = ?, notes = ?, title = ? "
		 		+ "WHERE account_type = ? AND userName = ?";
		 
	        try {
	          connection = ConnectionUtils.getConnection();
	        } catch (ClassNotFoundException ex) {
	          Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        
		    PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		   
		    statement.setString(1, account.password);
		    statement.setString(2, account.url);
		    statement.setString(3, account.notes);
		    statement.setString(4, account.title);
		    statement.setString(6, account.userName);
		    statement.executeUpdate();
		    ResultSet key = statement.getGeneratedKeys();
		    System.out.println(statement.toString());
		    statement.close();
		    statement = null;
		    accounts.add(account);
		    connection.close();
		    return account;
	}
	
	
	public boolean delete(Becchuu account) throws SQLException {
		if(account == null) {
			return false;
		}	
		String sql = "DELETE FROM Account WHERE account_type = " + account.group.id + " AND userName = '" + account.userName + "'";	
		System.out.println(sql);
        try {
          connection = ConnectionUtils.getConnection();
        } catch (ClassNotFoundException ex) {
          Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
		Statement st = connection.createStatement();
		accounts.remove(account);
        boolean result = st.execute(sql);
        connection.close();
	    return result;
	  }
}
