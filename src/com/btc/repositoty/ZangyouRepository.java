package com.btc.repositoty;

import com.btc.DAL.ConnectionUtils;
import com.btc.model.Zangyou;
import com.btc.supports.Helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by 130708 on 2015/11/23.
 */
public class ZangyouRepository extends GenericRepository<Zangyou> {

   @Override
   protected void initData() {
      this.data = new ArrayList<Zangyou>();
      try {
         try {
            ResultSet rs = ConnectionUtils.executeQuery("SELECT * FROM Zangyou ORDER BY id DESC");
            while (rs.next()) {
               Zangyou zangyou = new Zangyou();
               int valIndex = 1;
               zangyou.setId(rs.getInt(valIndex++));
               zangyou.setStartDate(Helpers.fullDateFromString(rs.getString(valIndex++)));
               zangyou.setToDate(Helpers.fullDateFromString(rs.getString(valIndex++)));
               zangyou.setEmployeeID(rs.getString(valIndex++));
               this.data.add(zangyou);
            }
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         }
      } catch (SQLException e) {
         System.out.println("SQL exception occured" + e);
      }
   }

   @Override
   public Zangyou getById(Object id) {
      int identity = Integer.parseInt(id.toString());
      List<Zangyou> data = getListWithRefresh(false);
      for (Zangyou zangyou : data) {
         if (zangyou.getId() == identity) {
            return zangyou;
         }
      }
      return null;
   }

   @Override
   public Zangyou insert(Zangyou zangyou) throws SQLException {
      String sql = "INSERT INTO Zangyou (start_date, to_date, employee_id) VALUES(?, ?, ?)";
      try {
         connection = ConnectionUtils.getConnection();
      } catch (ClassNotFoundException ex) {
         Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
      }

      int valIndex = 1;
      PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

      statement.setString(valIndex++, Helpers.stringFromFullDate(zangyou.getStartDate()));
      statement.setString(valIndex++, Helpers.stringFromFullDate(zangyou.getToDate()));
      statement.setString(valIndex++, zangyou.getEmployeeID());
      int affectedRows = statement.executeUpdate();
      if (affectedRows == 0) {
         throw new SQLException("追加失敗しました。");
      }

      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
         if (generatedKeys.next()) {
            zangyou.setId(generatedKeys.getInt(1));
         } else throw new SQLException("追加失敗しました。Could not fetch generated identity");
      }

      statement.close();
      statement = null;
      connection.close();
      connection = null;
      if (data != null)
         data.add(zangyou);
      return zangyou;
   }

   @Override
   public Zangyou update(Zangyou zangyou) throws SQLException {
      String sql = "UPDATE Zangyou SET start_date = ?, to_date = ?, employee_id = ? "
          + " WHERE id = ?";
      try {
         connection = ConnectionUtils.getConnection();
      } catch (ClassNotFoundException ex) {
         System.out.println("Could not connect to database");
         Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
      }
      int valIndex = 1;
      PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
      statement.setString(valIndex++, Helpers.stringFromFullDate(zangyou.getStartDate()));
      statement.setString(valIndex++, Helpers.stringFromFullDate(zangyou.getToDate()));
      statement.setString(valIndex++, zangyou.getEmployeeID());
      statement.setInt(valIndex++, zangyou.getId());

      statement.executeUpdate();
      statement.close();
      statement = null;

      connection.close();
      connection = null;

      return zangyou;
   }

   @Override
   public boolean delete(Zangyou zangyou) throws SQLException {
      return false;
   }
}
