package com.btc.repositoty;

import com.Exception.BecchuuExistsException;
import com.btc.DAL.ConnectionUtils;
import com.btc.model.Becchuu;
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

public class BecchuuRepository {
   private List<Becchuu> data;
   private Connection connection;

//   private static BecchuuRepository _instance;

   public BecchuuRepository() {

   }

//   public static BecchuuRepository Instance() {
//      if (_instance == null) _instance = new BecchuuRepository();
//      return _instance;
//   }

   public Becchuu getBecchuuByID(String koujibangou, String becchuuKigou) {
      List<Becchuu> becchuus = getListWithRefresh(false);
      for (Becchuu becchuu : becchuus) {
         if (becchuu.getKoujibangou().equals(koujibangou) && becchuu.getBecchuuKigou().equals(becchuuKigou)) {
            return becchuu;
         }
      }
      return null;
   }

   public List<Becchuu> getBecchuuOfBukken(String koujibangou) {
      List<Becchuu> becchuus = new LinkedList<>();
      for (Becchuu becchuu : getListWithRefresh(true)) {
         if (becchuu.getKoujibangou().equals(koujibangou)) {
            becchuus.add(becchuu);
         }
      }
      return becchuus;
   }

   public List<Becchuu> getListWithRefresh(boolean refresh) {
      if (data != null) {
         if (refresh) data.clear();
         else return data;
      }

      data = new ArrayList<Becchuu>();
      try {
         ResultSet rs = ConnectionUtils.executeQuery("SELECT * FROM Becchuu ORDER BY id DESC");
         while (rs.next()) {
            Becchuu becchuu = new Becchuu();
            int varNumber = 1;
            becchuu.setBecchuuKigou(rs.getString(varNumber++));
            becchuu.setBecchuuParameter(rs.getString(varNumber++));
            becchuu.setBecchuuNaiyou(rs.getString(varNumber++));
            becchuu.setMotozuKigou(rs.getString(varNumber++));
            becchuu.setMotozuParameter(rs.getString(varNumber++));

            becchuu.setIraibi(Helpers.dateFromString(rs.getString(varNumber++)));
            becchuu.setSakuseiBi(Helpers.dateFromString(rs.getString(varNumber++)));

            becchuu.setKoujibangou(rs.getString(varNumber++));
            becchuu.setSakuseiShaID(rs.getString(varNumber++));
            becchuu.setKenshuShaID(rs.getString(varNumber++));
            becchuu.setMisu(rs.getInt(varNumber++));
            becchuu.setBecchuuMaisu(rs.getInt(varNumber++));
            becchuu.setSakuseiStatusID(rs.getInt(varNumber++));
            becchuu.setKenshuuStatusID(rs.getInt(varNumber++));
            becchuu.setUploadStatusID(rs.getInt(varNumber++));
            becchuu.setBecchuuTypeID(rs.getInt(varNumber++));
            becchuu.setIraiShaID(rs.getString(varNumber++));
            becchuu.setBikou(rs.getString(varNumber++));
            becchuu.setId(rs.getInt(varNumber++));
            becchuu.setHinCode(rs.getString(varNumber++));
            data.add(becchuu);
         }
      } catch (ClassNotFoundException exception) {
         System.out.println("ClassNotFound exception occured" + exception);
      } catch (SQLException e) {
         System.out.println("SQL exception occured" + e);
      }
      return data;
   }

   public Becchuu insert(Becchuu becchuu) throws SQLException {
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
         // data.add(becchuu);
         return becchuu;
      } catch (ClassNotFoundException ex) {
         Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
   }

   public Becchuu update(Becchuu becchuu) throws SQLException, BecchuuExistsException {
//      if (!checkBeforeUpdate(becchuu)) throw new BecchuuExistsException("This becchuu is already exists!");

      String sql = "UPDATE Becchuu SET becchuu_kigou = ?, becchuu_parameter = ?, becchuu_naiyou = ?, motozu_kigou = ?, motozu_parameter = ?, "
            + " sakuseibi = ?, sakusei_sha_id = ?, kenshuu_sha_id = ?, misu = ?, becchuu_maisu = ?, "
            + " sakusei_status = ?, kenshuu_status = ?, "
            + "upload_status = ?, becchuu_type_id = ?, irai_sha = ?, notes = ?, hin_code = ? "
            + " WHERE id = ?";
      try {
         connection = ConnectionUtils.getConnection();
      } catch (ClassNotFoundException ex) {
         System.out.println("Could not connect to database");
         Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
      }

      PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
      int varIndex = 1;
      statement.setString(varIndex++, becchuu.getBecchuuKigou());
      statement.setString(varIndex++, becchuu.getBecchuuParameter());
      statement.setString(varIndex++, becchuu.getBecchuuNaiyou());
      statement.setString(varIndex++, becchuu.getMotozuKigou());
      statement.setString(varIndex++, becchuu.getMotozuParameter());
      statement.setString(varIndex++, Helpers.stringFromDate(becchuu.getSakuseiBi()));
      statement.setString(varIndex++, becchuu.getSakuseiShaID());
      statement.setString(varIndex++, becchuu.getKenshuShaID());
      statement.setInt(varIndex++, becchuu.getMisu());
      statement.setInt(varIndex++, becchuu.getBecchuuMaisu());
      statement.setInt(varIndex++, becchuu.getSakuseiStatusID());
      statement.setInt(varIndex++, becchuu.getKenshuuStatusID());
      statement.setInt(varIndex++, becchuu.getUploadStatusID());

      statement.setInt(varIndex++, becchuu.getBecchuuTypeID());

      statement.setString(varIndex++, becchuu.getIraiShaID());
      statement.setString(varIndex++, becchuu.getBikou());
      statement.setString(varIndex++, becchuu.getHinCode());
      statement.setInt(varIndex++, becchuu.getId());


      System.out.println(statement.toString());
      statement.executeUpdate();
      statement.close();
      statement = null;

      connection.close();
      connection = null;

      return becchuu;
   }

   private boolean checkBeforeUpdate(Becchuu becchuu) {
      List<Becchuu> becchuus = this.getListWithRefresh(false);
      for (Becchuu becchuu2 : becchuus) {
         if (becchuu2.getBecchuuKigou().equals(becchuu.getBecchuuKigou()) && becchuu2.getId() != becchuu.getId()) {
            return false;
         }
      }
      return true;

   }

}
