package com.btc.viewModel;

import com.btc.model.Becchuu;
import com.btc.model.Bukken;
import com.btc.repositoty.BecchuuRepository;
import com.btc.supports.Helpers;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class BecchuuTableModel extends AbstractTableModel {
   private static String[] COLUMN_NAMES = {"別注記号", "分類", "依頼日", "作成者", "別注枚数", "作成状況", "検収状況", "別注ＤＢアップ"};
   private List<Becchuu> data = new ArrayList<>();
   private BecchuuRepository repository;
   private Bukken bukken;

   public void setBukken(Bukken bukken) {
      if (bukken == null) return;
      this.bukken = bukken;
      this.data = bukken.getListBecchuu();
      fireTableDataChanged();
   }

   public BecchuuTableModel(BecchuuRepository repository) {
//      this.repository = BecchuuRepository.Instance();
      this.repository = repository;
   }

   public void refresh() {
      setBukken(this.bukken);
      fireTableDataChanged();
   }
//	
//	
//	
//	public Bukken insertBukken(Bukken bukken) {
//		try {			
//			bukken = repositoty.insert(bukken);
//		
//			
//            fireTableRowsInserted(data.size() - 1, data.size() - 1);
//            
//			return bukken;
//		} catch (SQLException e) {			
//			DialogHelpers.showError("エラー", "工事番号は既に存在しています！");
//		}	
//		return null;
//	}
//	
//	public Bukken updateBukken(Bukken bukken, int rowIndex) {
//		try {			
//			bukken = repositoty.update(bukken);	
//			fireTableRowsUpdated(rowIndex, rowIndex);
//            
//			return bukken;
//		} catch (SQLException e) {			
//			DialogHelpers.showError("エラー", "今物件編集ができない！");
//		}	
//		return null;
//	}
//	
//	public void deleteBukken(Bukken group) {
//		try {			
//			int row = data.indexOf(group);
//			if (row == -1) return;
//			if (DialogHelpers.showConfirmMessage("Delete group", 
//					"Do you want to delete this group\n id: " + String.valueOf(group.id) + "\nname: " + group.name, 1 ) == JOptionPane.YES_OPTION);
//			{
//				repositoty.delete(group);
//				fireTableRowsDeleted(row, row);
//			}			
//		} catch (Exception exception) {
//			DialogHelpers.showError("Error occured", exception);
//		}		
//	}

   @Override
   public String getColumnName(int column) {
      return COLUMN_NAMES[column];
   }

   @Override
   public int getColumnCount() {
      return COLUMN_NAMES.length;
   }

   @Override
   public int getRowCount() {
      return data.size();
   }

   @Override
   public Object getValueAt(int row, int col) {
      try {
         Becchuu becchuu = data.get(row);
         switch (col) {
            case 0: // 別注記号
               return becchuu.getBecchuuKigou();
            case 1: //　別注タイプ
               return becchuu.getBecchuuType().getName();
            case 2: //　依頼日
               return Helpers.stringFromDate(becchuu.getIraibi());
            case 3: //　作成者
               return becchuu.getSakuseiSha().getName();
            case 4: //　別注枚数
               int maisuu = becchuu.getBecchuuMaisu();
               if (maisuu < 0) return "";
               return becchuu.getBecchuuMaisu();
            case 5: //　作成状況
               return becchuu.getSakuseiStatus();
            case 6: // 研修状況
               return becchuu.getKenshuuStatus();
            case 7: //　別注DBアップ状況
               return becchuu.getUploadStatus();
            default:
               return "Error";
         }
      } catch (Exception e) {
         return "";
      }
   }

   public void clearAll() {
      try {
         for (int i = 0; i < data.size(); i++) {
            fireTableRowsDeleted(i, i);

         }
         data.clear();
      } catch (Exception exception) {

      }
   }

}
