package com.btc.controllers.KujouKanriForm;

import com.btc.controllers.DialogHelpers;
import com.btc.model.Kujou;
import com.btc.repositoty.KujouRepository;
import com.btc.supports.Helpers;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by BTC on 11/21/15.
 */
public class KujouKanriTableModel extends AbstractTableModel {

   private final String[] COLUMN_NAMES = {"id", "担当者", "工事番号", "施主名", "苦情タイプ", "発生日"};
   private List<Kujou> data;
   private KujouRepository repository;

   public KujouKanriTableModel(KujouRepository repository) {
      this.repository = repository;
      this.data = repository.getListWithRefresh(false);
   }

   @Override
   public int getRowCount() {
      return data.size();
   }

   @Override
   public int getColumnCount() {
      return COLUMN_NAMES.length;
   }

   @Override
   public String getColumnName(int column) {
      return COLUMN_NAMES[column];
   }

   @Override
   public Object getValueAt(int rowIndex, int columnIndex) {
      Kujou kujou = data.get(rowIndex);
      try {
         switch (columnIndex) {
            case 0:
               return kujou.getId();
            case 1:
               return kujou.getKenshuuSha().getName();
            case 2:
               return kujou.getBecchuu().getKoujibangou();
            case 3:
               return kujou.getBecchuu().getBukken().getName();
            case 4:
               return kujou.getKujouType().getName();
            case 5:
               return Helpers.stringFromDate(kujou.getReceiptDate());
            default:
               return "Error";
         }
      } catch (Exception e) {
         return "";
      }
   }

   public Kujou insert(Kujou kujou) {
      try {
         kujou = repository.insert(kujou);
         fireTableRowsInserted(data.size() - 1, data.size() - 1);
         DialogHelpers.showAlert("成功", "追加成功！");
         return kujou;

      } catch (SQLException e) {
         DialogHelpers.showError("エラー", "この苦情は既に存在しています！" + e.getMessage());
      }
      return null;
   }

   public Kujou update(Kujou kujou, int rowIndex) {
      try {
         kujou = repository.update(kujou);
         fireTableRowsUpdated(rowIndex, rowIndex);
         DialogHelpers.showAlert("成功", "更新成功！");
         return kujou;

      } catch (SQLException e) {
         e.printStackTrace();
         DialogHelpers.showError("エラー", "エラー発生しました！" + e.getMessage());
      }
      return null;
   }

   public boolean delete(Kujou kujou) {

      int row = data.indexOf(kujou);
      if (row == -1) return false;
      if (DialogHelpers.showConfirmMessage("削除", "削除してよろしいですか。", 1) == JOptionPane.YES_OPTION) {
         try {
            repository.delete(kujou);
            fireTableRowsDeleted(row, row);
            return true;
         } catch (SQLException exception) {
            DialogHelpers.showError("Error occured", exception);
         }
      }
      return false;
   }

}
