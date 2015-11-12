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

	private static BecchuuRepository _instance;

	private BecchuuRepository() {

	}
	
	public static BecchuuRepository Instance() {
		if (_instance == null) _instance = new BecchuuRepository();
		return _instance;
	}

	public Becchuu getBecchuuByID(String koujibangou, String becchuuKigou) {
		List<Becchuu> becchuus = getListWithRefresh(false);
		for (Becchuu becchuu: becchuus) {
			if (becchuu.getKoujibangou().equals(koujibangou) && becchuu.getBecchuuKigou().equals(becchuuKigou)) {
				return becchuu;
			}
		}
		return null;
	}
		
	public List<Becchuu> getListWithRefresh(boolean refresh) {
		if(data != null) {
			if (refresh) data.clear();
			else return data;
		}
		
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
				becchuu.setKenshuShaID(rs.getString(10));
				becchuu.setMisu(rs.getInt(11));
				becchuu.setBecchuuMaisu(rs.getInt(12));
				becchuu.setSakuseiStatusID(rs.getInt(13));
				becchuu.setKenshuuStatusID(rs.getInt(14));
				becchuu.setUploadStatusID(rs.getInt(15));
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

	public void insert(Becchuu becchuu) throws SQLException {
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

	public Becchuu update(Becchuu becchuu) throws SQLException {		
		 String sql = "UPDATE Becchuu SET becchuu_parameter = ?, becchuu_naiyou = ?, motozu_kigou = ?, motozu_parameter = ?, "
		 		+ " sakuseibi = ?, sakusei_sha_id = ?, kenshuu_sha_id = ?, misu = ?, becchuu_maisu = ?, "
		 		+ " sakusei_status = ?, kenshuu_status = ?, "
		 		+ "upload_status = ?, becchuu_type_id = ?, irai_sha = ?, notes = ?"
		 		+ " WHERE becchuu_kigou = ? AND koujibangou = ?";
	        try {
	        	connection = ConnectionUtils.getConnection();
	        } catch (ClassNotFoundException ex) {
	        	System.out.println("Could not connect to database");
	        	Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        
		    PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);	
		    
		    statement.setString(1, becchuu.getBecchuuParameter());
		    statement.setString(2, becchuu.getBecchuuNaiyou());
		    statement.setString(3, becchuu.getMotozuKigou());
		    statement.setString(4, becchuu.getMotozuParameter());		    
		    statement.setString(5, Helpers.stringFromDate(becchuu.getSakuseiBi()));
		    statement.setString(6, becchuu.getSakuseiShaID());
		    statement.setInt(7, becchuu.getKenshuuStatusID());
		    statement.setInt(8, becchuu.getMisu());
		    statement.setInt(9, becchuu.getBecchuuMaisu());
		    statement.setInt(10, becchuu.getSakuseiStatusID());
		    statement.setInt(11, becchuu.getKenshuuStatusID());
		    statement.setInt(12, becchuu.getUploadStatusID());
		    
		    
//		    statement.setInt(13, becchuu.getBecchuuTypeID());
		    statement.setInt(13, 2);
		 
		    
		    statement.setString(14, becchuu.getIraiShaID());
		    statement.setString(15, becchuu.getBikou());
		    statement.setString(16, becchuu.getBecchuuKigou());
		    statement.setString(17, becchuu.getKoujibangou());
		    
		    System.out.println(statement.toString());
		    statement.executeUpdate();
		    statement.close();
		    statement = null;
		    
		    connection.close();
		    connection = null;
		    
		    return becchuu;
	}

}
