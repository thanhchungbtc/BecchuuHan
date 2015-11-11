package com.btc.repositoty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.btc.DAL.ConnectionUtils;
import com.btc.model.Becchuu;
import com.btc.model.Bukken;
import com.btc.supports.Helpers;

public class BecchuuRepository {
	private List<Becchuu> data;
	private Connection connection;

	

	public BecchuuRepository() {

	}

	public List<Becchuu> getList() {
		if(data != null) data.clear();
		
		data = new ArrayList<Becchuu>();
		try {
			ResultSet rs = ConnectionUtils.executeQuery ("SELECT * FROM Becchuu");		         
			while (rs.next()) {
				Becchuu becchuu = new Becchuu();
				becchuu.setBecchuuKigou(rs.getString(1));
				becchuu.setBecchuuParameter(rs.getString(2));
				becchuu.setBecchuuNaiyou(rs.getString(3));
				becchuu.setMotozuKigou(rs.getString(4));
				becchuu.setMotozuParameter(rs.getString(5));

				becchuu.setIraibi(Helpers.dateFromString(rs.getString(6)));
				becchuu.setSakuseiBi(Helpers.dateFromString(rs.getString(7)));

				becchuu.setKoujibangou(rs.getString(8));
				becchuu.setSakuseiShaID(rs.getString(9));
				becchuu.setSakuseiShaID(rs.getString(10));
				becchuu.setMisu(rs.getInt(11));
				becchuu.setBecchuuMaisu(rs.getInt(12));
				becchuu.setSakuseiStatus(rs.getInt(13));
				becchuu.setKenshuuStatus(rs.getInt(14));
				becchuu.setUploadStatus(rs.getInt(15));
				becchuu.setBecchuuTypeID(rs.getInt(16));
				becchuu.setIraiShaID(rs.getString(17));
				becchuu.setBikou(rs.getString(18));

				data.add(becchuu);
			}
		}
		catch (ClassNotFoundException exception) {
			System.out.println("ClassNotFound exception occured" + exception);
		}
		catch(SQLException e){
			System.out.println("SQL exception occured" + e);
		}    		
		return data;
	}

	public static void insert(Becchuu becchuu) throws SQLException {
		String sql = "INSERT INTO Becchuu ("
				+ "becchuu_kigou, becchuu_parameter, becchuu_naiyou,"
				+ " motozu_kigou, motozu_parameter,"
				+ " iraibi, koujibangou, irai_sha) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		Connection connection = null;
		try {
			connection = ConnectionUtils.getConnection();
			PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
			statement.setString(1, becchuu.getBecchuuKigou());
			statement.setString(2, becchuu.getBecchuuParameter());
			statement.setString(3, becchuu.getBecchuuNaiyou());
			statement.setString(4, becchuu.getMotozuKigou());
			statement.setString(5, becchuu.getMotozuParameter());		
			statement.setString(6, Helpers.stringFromDate(becchuu.getIraibi()));
			statement.setString(7, becchuu.getKoujibangou());
			statement.setString(8, becchuu.getIraiShaID());		

			statement.executeUpdate();
			statement.close();
			statement = null;

			connection.close();
			connection = null;
			//data.add(becchuu);
			//return bukken;
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	//	public List<Becchuu> getBecchuusOfBukken(String koujibangou) {
	//		List<Becchuu> data = new LinkedList<>();
	//		try {
	//			ResultSet rs = ConnectionUtils.executeQuery ("SELECT * FROM Becchuu WHERE koujibangou = `" + koujibangou + "`");		         
	//			while (rs.next()) {
	//				Becchuu becchuu = new Becchuu();
	//				becchuu.setBecchuuKigou(rs.getString(1));
	//				becchuu.setBecchuuParameter(rs.getString(2));
	//				becchuu.setBecchuuNaiyou(rs.getString(3));
	//				becchuu.setBecchuuNaiyou(rs.getString(4));
	//				
	//				becchuu.setIraibi(Helpers.dateFromString(rs.getString(5)));
	//				becchuu.setSakuseiBi(Helpers.dateFromString(rs.getString(6)));
	//				
	//				becchuu.setKoujibangou(rs.getString(7));
	//				becchuu.setSakuseiShaID(rs.getString(8));
	//				becchuu.setSakuseiShaID(rs.getString(9));
	//				becchuu.setMisu(rs.getInt(10));
	//				becchuu.setBecchuuMaisu(rs.getInt(11));
	//				becchuu.setSakuseiStatus(rs.getInt(12));
	//				becchuu.setKenshuuStatus(rs.getInt(13));
	//				becchuu.setUploadStatus(rs.getInt(14));
	//				becchuu.setBecchuuTypeID(rs.getInt(15));
	//				becchuu.setIraiShaID(rs.getInt(16));
	//				becchuu.setBikou(rs.getString(17));
	//				
	//				data.add(becchuu);
	//			}
	//		}
	//		catch (ClassNotFoundException exception) {
	//			System.out.println("ClassNotFound exception occured" + exception);
	//		}
	//		catch(SQLException e){
	//			System.out.println("SQL exception occured" + e);
	//		}    
	//		return data;
	//	}

	//	
	//	public Bukken getBukkenWithID(String id) {
	//		 for (Bukken bukken: data) {
	//			 if (bukken.getId().equals(id)) {
	//				 return bukken;
	//			 }
	//		 }
	//		 return null;
	//	}
	//	
	//	public Bukken insert(Bukken bukken) throws SQLException {
	//		 String sql = "INSERT INTO Bukken (id, name, depsf, shiten, type, nouki) VALUES(?, ?, ?, ?, ?, ?)";
	//	        try {
	//	          connection = ConnectionUtils.getConnection();
	//	        } catch (ClassNotFoundException ex) {
	//	          Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
	//	        }
	//	        
	//		    PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
	//		    statement.setString(1, bukken.getId());
	//		    statement.setString(2, bukken.getName());
	//		    statement.setString(3, bukken.getDepsf());
	//		    statement.setString(4, bukken.getShiten());
	//		    statement.setInt(5, bukken.getType());
	//		    statement.setString(6, Helpers.stringFromDate(bukken.getNouki()));
	//		    
	//		    statement.executeUpdate();
	//		    statement.close();
	//		    statement = null;
	//		    
	//		    connection.close();
	//		    connection = null;
	//		    data.add(bukken);
	//		    return bukken;
	//	}
	//	
	//	public Bukken update(Bukken bukken) throws SQLException {
	//		 String sql = "UPDATE Bukken SET id = ?, name = ?, depsf = ?, shiten = ?, type = ?, nouki = ? "
	//		 		+ " WHERE id = ?";
	//	        try {
	//	        	connection = ConnectionUtils.getConnection();
	//	        } catch (ClassNotFoundException ex) {
	//	        	System.out.println("Could not connect to database");
	//	        	Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
	//	        }
	//	        
	//		    PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
	//		    statement.setString(1, bukken.getId());
	//		    statement.setString(2, bukken.getName());
	//		    statement.setString(3, bukken.getDepsf());
	//		    statement.setString(4, bukken.getShiten());
	//		    statement.setInt(5, bukken.getType());
	//		    statement.setString(6, Helpers.stringFromDate(bukken.getNouki()));
	//		    
	//		    statement.setString(7, bukken.getId());
	//		    statement.executeUpdate();
	//		    statement.close();
	//		    statement = null;
	//		    
	//		    connection.close();
	//		    connection = null;
	//		    
	//		    return bukken;
	//	}

}
