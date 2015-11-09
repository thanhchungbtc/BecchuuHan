package com.btc.repositoty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.btc.DAL.ConnectionUtils;
import com.btc.model.Bukken;

public class BukkenRepository {

	private List<Bukken> data;
	private Connection connection;
	
	public BukkenRepository() {
		data = new LinkedList<Bukken>();		    
		     try {
		         try {
		        	 ResultSet rs = ConnectionUtils.executeQuery ("SELECT * FROM Bukken");		         
			         while (rs.next()) {
			           String id = rs.getString(1);
			           String name = rs.getString(2);
			           Bukken bukken = new Bukken();
			           bukken.id = id;
			           bukken.name = name;			           
			           data.add(bukken);
			         }
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} 
		         
		      }
		      catch(SQLException e){
		         System.out.println("SQL exception occured" + e);
		      }    
	}
	
	public List<Bukken> getList() {
		return this.data;
	}
	
	public Bukken insert(Bukken accountType) throws SQLException {
		 String sql = "INSERT INTO AccountType (name) VALUES(?)";
	        try {
	          connection = ConnectionUtils.getConnection();
	        } catch (ClassNotFoundException ex) {
	          Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        
		    PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		    statement.setString(1, accountType.name);
		    statement.executeUpdate();
		    ResultSet key = statement.getGeneratedKeys();
		    int id = 0;
		    while (key.next()) {
		    	id = key.getInt(1);
		    }
		    statement.close();
		    statement = null;
		    data.add(accountType);
		    connection.close();
		    return accountType;
	}
	
	public boolean delete(Bukken group) throws SQLException {
		if(group == null) {
			return false;
		}	
		String sql = "DELETE FROM AccountType WHERE id = " + group.id + "";		
        try {
          connection = ConnectionUtils.getConnection();
        } catch (ClassNotFoundException ex) {
          Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
		Statement st = connection.createStatement();
		data.remove(group);
        boolean result = st.execute(sql);
        connection.close();
	    return result;
	  }
	
	
	
}
