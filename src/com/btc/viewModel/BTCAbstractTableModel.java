package com.btc.viewModel;

import com.btc.controllers.DialogHelpers;
import com.btc.repositoty.GenericRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by BTC on 11/22/15.
 */
public abstract class BTCAbstractTableModel<T> extends AbstractTableModel{

   protected String[] columnNames;
   protected List<T> data;
   protected GenericRepository<T> repository;

   protected String[] getColumnNames() {
      if (columnNames == null) columnNames = initColumnNames();
      return columnNames;
   }

   protected abstract String[] initColumnNames();

   protected void initializeData() {
      this.data = repository.getListWithRefresh(false);
   }

   public BTCAbstractTableModel(GenericRepository<T> repository) {
      this.repository = repository;
      initializeData();
   }

   @Override
   public int getRowCount() {
      return data.size();
   }

   @Override
   public int getColumnCount() {
      return getColumnNames().length;
   }

   @Override
   public String getColumnName(int column) {
      return getColumnNames()[column];
   }

   @Override
   public abstract Object getValueAt(int rowIndex, int columnIndex);

   public T insert(T t) {
      try {
         t = repository.insert(t);
         fireTableRowsInserted(data.size() - 1, data.size() - 1);
         DialogHelpers.showAlert("成功", "追加成功！");
         return t;

      } catch (SQLException e) {
         DialogHelpers.showError("エラー", "この苦情は既に存在しています！" + e.getMessage());
      }
      return null;
   }

   public T update(T t, int rowIndex) {
      try {
         t = repository.update(t);
         fireTableRowsUpdated(rowIndex, rowIndex);
         DialogHelpers.showAlert("成功", "更新成功！");
         return t;

      } catch (SQLException e) {
         e.printStackTrace();
         DialogHelpers.showError("エラー", "エラー発生しました！" + e.getMessage());
      }
      return null;
   }

   public boolean delete(T t) {

      int row = data.indexOf(t);
      if (row == -1) return false;
      if (DialogHelpers.showConfirmMessage("削除", "削除してよろしいですか。", 1) == JOptionPane.YES_OPTION)
      {
         try {
            repository.delete(t);
            fireTableRowsDeleted(row, row);
            return true;
         } catch (SQLException exception) {
            DialogHelpers.showError("Error occured", exception);
         }
      }
      return false;
   }
}
