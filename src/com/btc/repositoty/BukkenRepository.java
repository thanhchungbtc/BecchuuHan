package com.btc.repositoty;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.btc.DAL.ConnectionUtils;
import com.btc.model.Becchuu;
import com.btc.model.Bukken;
import com.btc.model.BukkenType;
import com.btc.supports.Helpers;

public class BukkenRepository {

	private List<Bukken> data;
	private Connection connection;
	private static BukkenRepository _instance;
	
	private BukkenRepository() {
		  
	}

	public static BukkenRepository Instance() {
		if (_instance == null) _instance = new BukkenRepository();
		return _instance;
	}
	public List<Bukken> getListWithRefresh(boolean refresh) {
		if(data != null) {
			if (refresh) data.clear();
			else return data;
		}
		data = new ArrayList<Bukken>();
		try {
			try {
				ResultSet rs = ConnectionUtils.executeQuery ("SELECT * FROM Bukken ORDER BY nouki DESC");		         
				while (rs.next()) {
					String id = rs.getString(1);
					String name = rs.getString(2);

					Bukken bukken = new Bukken();
					bukken.setId(id);
					bukken.setName(name);				           
					bukken.setDepsf(rs.getString(3));
					bukken.setShiten(rs.getString(5));
					bukken.setType(rs.getInt(4));			           
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
		return this.data;
	}
	
	public Bukken getBukkenWithID(String id) {
		List<Bukken> data = getListWithRefresh(false);
		 for (Bukken bukken: data) {
			 if (bukken.getId().equals(id)) {
				 return bukken;
			 }
		 }
		 return null;
	}
	
	public String geBukkenTypeWithID(int id) {
		return BukkenType.getType(id);
	}

	public Bukken contains(String koujibangou) {
		List<Bukken> data = getListWithRefresh(false);
		for (Bukken bukken: data) {
			if (bukken.getId().equals(koujibangou)) {
				return bukken;
			}
		}
		return null;
	}
	
	public Bukken insert(Bukken bukken) throws SQLException {
		 String sql = "INSERT INTO Bukken (id, name, depsf, shiten, type, nouki) VALUES(?, ?, ?, ?, ?, ?)";
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
		    statement.setString(6, Helpers.stringFromDate(bukken.getNouki()));
		    
		    statement.executeUpdate();
		    statement.close();
		    statement = null;
		    
		    connection.close();
		    connection = null;
		    data.add(bukken);
		    return bukken;
	}
	
	public Bukken update(Bukken bukken) throws SQLException {
		 String sql = "UPDATE Bukken SET id = ?, name = ?, depsf = ?, shiten = ?, type = ?, nouki = ? "
		 		+ " WHERE id = ?";
	        try {
	        	connection = ConnectionUtils.getConnection();
	        } catch (ClassNotFoundException ex) {
	        	System.out.println("Could not connect to database");
	        	Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        
		    PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
		    statement.setString(1, bukken.getId());
		    statement.setString(2, bukken.getName());
		    statement.setString(3, bukken.getDepsf());
		    statement.setString(4, bukken.getShiten());
		    statement.setInt(5, bukken.getType());
		    statement.setString(6, Helpers.stringFromDate(bukken.getNouki()));
		    
		    statement.setString(7, bukken.getId());
		    statement.executeUpdate();
		    statement.close();
		    statement = null;
		    
		    connection.close();
		    connection = null;
		    
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
