package com.btc.repositoty;

import com.btc.DAL.ConnectionUtils;
import com.btc.model.Kujou;
import com.btc.model.KujouType;
import com.btc.supports.Helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by BTC on 11/21/15.
 */
public class KujouRepository extends GenericRepository<Kujou> {

   @Override
   protected void initData() {
      this.data = new ArrayList<Kujou>();
      try {
         try {
            ResultSet rs = ConnectionUtils.executeQuery("SELECT * FROM Kujou ORDER BY id DESC");
            while (rs.next()) {
               Kujou kujou = new Kujou();
               int valIndex = 1;
               kujou.setId(rs.getInt(valIndex++));
               kujou.setReceiptDate(Helpers.dateFromString(rs.getString(valIndex++)));
               kujou.setDescription(rs.getString(valIndex++));
               kujou.setKujouTypeId(rs.getInt(valIndex++));
               kujou.setBikou(rs.getString(valIndex++));
               kujou.setBecchuuId(rs.getInt(valIndex++));
               this.data.add(kujou);
            }
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         }
      } catch (SQLException e) {
         System.out.println("SQL exception occured" + e);
      }
   }

   @Override
   public Kujou getById(Object id) {
      int identity = Integer.parseInt(id.toString());
      List<Kujou> data = getListWithRefresh(false);
      for (Kujou kujou : data) {
         if (kujou.getId() == identity) {
            return kujou;
         }
      }
      return null;
   }

   @Override
   public Kujou insert(Kujou kujou) throws SQLException {
      String sql = "INSERT INTO Kujou (receip_date, description, kujou_type_id, bikou, becchuu_id) VALUES(?, ?, ?, ?, ?)";
      try {
         connection = ConnectionUtils.getConnection();
      } catch (ClassNotFoundException ex) {
         Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
      }

      int valIndex = 1;
      PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

      statement.setString(valIndex++, Helpers.stringFromDate(kujou.getReceiptDate()));
      statement.setString(valIndex++, kujou.getDescription());
      statement.setInt(valIndex++, kujou.getKujouTypeId());
      statement.setString(valIndex++, kujou.getBikou());
      statement.setInt(valIndex++, kujou.getBecchuuId());
      int affectedRows = statement.executeUpdate();
      if (affectedRows == 0) {
         throw new SQLException("追加失敗しました。");
      }

      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
         if (generatedKeys.next()) {
            kujou.setId(generatedKeys.getInt(1));
         } else throw new SQLException("追加失敗しました。Could not fetch generated identity");
      }

      statement.close();
      statement = null;

      connection.close();
      connection = null;
      data.add(kujou);
      return kujou;
   }

   @Override
   public Kujou update(Kujou kujou) throws SQLException {
      String sql = "UPDATE Kujou SET receip_date = ?, description = ?, kujou_type_id = ?, bikou = ?, becchuu_id = ? "
          + " WHERE id = ?";
      try {
         connection = ConnectionUtils.getConnection();
      } catch (ClassNotFoundException ex) {
         System.out.println("Could not connect to database");
         Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
      }
      int valIndex = 1;
      PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
      statement.setString(valIndex++, Helpers.stringFromDate(kujou.getReceiptDate()));
      statement.setString(valIndex++, kujou.getDescription());
      statement.setInt(valIndex++, kujou.getKujouTypeId());
      statement.setString(valIndex++, kujou.getBikou());
      statement.setInt(valIndex++, kujou.getBecchuuId());
      statement.setInt(valIndex++, kujou.getId());
      statement.executeUpdate();
      statement.close();
      statement = null;

      connection.close();
      connection = null;

      return kujou;
   }

   public List<KujouType> getListKujouType() {
      List<KujouType> kujous = new ArrayList<KujouType>();
      try {
         try {
            ResultSet rs = ConnectionUtils.executeQuery("SELECT * FROM kujouType ORDER BY id DESC");
            while (rs.next()) {
               int valIndex = 1;
               KujouType kujou = new KujouType(rs.getInt(valIndex++), rs.getString(valIndex++));
               kujou.setPoint(rs.getInt(valIndex++));
               kujous.add(kujou);
            }
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         }

      } catch (SQLException e) {
         System.out.println("SQL exception occured" + e);
      }
      return kujous;
   }

   public KujouType getKujouTypeByID(int id) {
      KujouType kujou = null;
      try {
         ResultSet rs = ConnectionUtils.executeQuery("SELECT * FROM kujouType WHERE id = " + id);
         while (rs.next()) {
            int valIndex = 1;
            kujou = new KujouType(rs.getInt(valIndex++), rs.getString(valIndex++));
            kujou.setPoint(rs.getInt(valIndex++));
         }
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return kujou;
   }

   public List<Kujou> getKujouByEmployeeID(String employeeID) {
      List<Kujou> kujous = new ArrayList<>();
      List<Kujou> d = getListWithRefresh(false);
      for (Kujou kujou : d) {
         try {
            if (kujou.getBecchuu().getKenshuShaID().equals(employeeID)) {
               kujous.add(kujou);
            }
         } catch (NullPointerException e) {
            continue;
         }
      }
      return kujous;
   }

   @Override
   public boolean delete(Kujou kujou) throws SQLException {
      if (kujou == null) {
         return false;
      }
      String sql = "DELETE FROM Kujou WHERE id = " + kujou.getId() + "";
      try {
         connection = ConnectionUtils.getConnection();
      } catch (ClassNotFoundException ex) {
         Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
      }
      Statement st = connection.createStatement();
      data.remove(kujou);
      boolean result = st.execute(sql);
      connection.close();
      return result;
   }

}
