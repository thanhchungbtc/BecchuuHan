package com.btc.repositoty;

import com.btc.DAL.ConnectionUtils;
import com.btc.model.Bukken;
import com.btc.model.BukkenType;
import com.btc.supports.Helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BukkenRepository {

   private List<Bukken> data;
   private Connection connection;

   public BukkenRepository() {

   }

   public List<Bukken> getListWithRefresh(boolean refresh) {
      if (data != null) {
         if (refresh) data.clear();
         else return data;
      }
      data = new ArrayList<Bukken>();
      try {
         try {
            ResultSet rs = ConnectionUtils.executeQuery("SELECT * FROM Bukken ORDER BY nouki DESC");
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
               bukken.setYobikata(rs.getString(7));
               data.add(bukken);
            }
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         }

      } catch (SQLException e) {
         System.out.println("SQL exception occured" + e);
      }
      return this.data;
   }

   public Bukken getBukkenWithID(String id) {
      List<Bukken> data = getListWithRefresh(false);
      for (Bukken bukken : data) {
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
      for (Bukken bukken : data) {
         if (bukken.getId().equals(koujibangou)) {
            return bukken;
         }
      }
      return null;
   }

   public Bukken insert(Bukken bukken) throws SQLException {
      String sql = "INSERT INTO Bukken (id, name, depsf, shiten, type, nouki, yobikata) VALUES(?, ?, ?, ?, ?, ?, ?)";
      try {
         connection = ConnectionUtils.getConnection();
      } catch (ClassNotFoundException ex) {
         Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
      }

      int valIndex = 1;
      PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
      statement.setString(valIndex++, bukken.getId());
      statement.setString(valIndex++, bukken.getName());
      statement.setString(valIndex++, bukken.getDepsf());
      statement.setString(valIndex++, bukken.getShiten());
      statement.setInt(valIndex++, bukken.getType());
      statement.setString(valIndex++, Helpers.stringFromDate(bukken.getNouki()));
      statement.setString(valIndex++, bukken.getYobikata());
      statement.executeUpdate();
      statement.close();
      statement = null;

      connection.close();
      connection = null;
      data.add(bukken);
      return bukken;
   }

   public Bukken update(Bukken bukken) throws SQLException {
      String sql = "UPDATE Bukken SET id = ?, name = ?, depsf = ?, shiten = ?, type = ?, nouki = ?, yobikata = ? "
          + " WHERE id = ?";
      try {
         connection = ConnectionUtils.getConnection();
      } catch (ClassNotFoundException ex) {
         System.out.println("Could not connect to database");
         Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
      }
      int valIndex = 1;
      PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
      statement.setString(valIndex++, bukken.getId());
      statement.setString(valIndex++, bukken.getName());
      statement.setString(valIndex++, bukken.getDepsf());
      statement.setString(valIndex++, bukken.getShiten());
      statement.setInt(valIndex++, bukken.getType());
      statement.setString(valIndex++, Helpers.stringFromDate(bukken.getNouki()));
      statement.setString(valIndex++, bukken.getYobikata());
      statement.setString(valIndex++, bukken.getId());
      statement.executeUpdate();
      statement.close();
      statement = null;

      connection.close();
      connection = null;

      return bukken;
   }

   public List<BukkenType> getBukkenTypes() {
      List<BukkenType> bukkenTypes = new LinkedList<>();
      bukkenTypes.add(new BukkenType(0, "XEVO"));
      bukkenTypes.add(new BukkenType(1, "Σ"));
      return bukkenTypes;
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
