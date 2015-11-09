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
import com.btc.supports.Helpers;

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
			           bukken.setId(id);
			           bukken.setName(name);	
			           
			           bukken.setDepsf(rs.getString(3));
			           bukken.setShiten(rs.getString(4));
			           bukken.setType(rs.getInt(5));
			           
			           bukken.setNouki(Helpers.dateFromString(rs.getString(6)));
			           
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
	
	public Bukken insert(Bukken bukken) throws SQLException {
		 String sql = "INSERT INTO Bukken (id, name, depsf, shiten, type) VALUES(?, ?, ?, ?, ?)";
	        try {
	          connection = ConnectionUtils.getConnection();
	        } catch (ClassNotFoundException ex) {
	          Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        
		    PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
		    statement.setString(1, bukken.getId());
		    statement.setString(2, bukken.getName());
		    statement.setString(3, bukken.getDepsf());
		    statement.setString(4, bukken.getShiten());
		    statement.setInt(5, bukken.getType());
		    
		    statement.executeUpdate();
		    statement.close();
		    statement = null;
		    
		    connection.close();
		    connection = null;
		    data.add(bukken);
		    return bukken;
	}
	
//	public boolean delete(Bukken group) throws SQLException {
//		if(group == null) {
//			return false;
//		}	
//		String sql = "DELETE FROM AccountType WHERE id = " + group.id + "";		
//        try {
//          connection = ConnectionUtils.getConnection();
//        } catch (ClassNotFoundException ex) {
//          Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
//        }
//		Statement st = connection.createStatement();
//		data.remove(group);
//        boolean result = st.execute(sql);
//        connection.close();
//	    return result;
//	  }
//	
	
	
}
