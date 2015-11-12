package com.btc.controllers.BecchuuKanriForm;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.btc.model.Becchuu;
import com.btc.repositoty.BecchuuRepository;
import com.btc.repositoty.CommonRepository;
import com.btc.supports.Helpers;

public class BecchuuKanriFormTableModel extends AbstractTableModel {
	private static String[] COLUMN_NAMES = {"別注記号", "分類", "工事番号", "施主名", "納期", "作成者", "別注枚数", "作成状況", "ミス件数","研修者", "検収状況", "ＤＢアップ状況"};
	private List<Becchuu> data;
	private BecchuuRepository repository;	

	public BecchuuKanriFormTableModel(BecchuuRepository repository) {
		this.repository = repository;
		this.data = repository.getListWithRefresh(false);
	}
	
	public void refresh() {		
		this.data = repository.getListWithRefresh(true);
		fireTableDataChanged();
	}

	
	
	// MARK: - AbstractTableModel Overrides	
	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}
	
	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		Becchuu becchuu = data.get(row);
		try{
			switch (col) {
			case 0: // 別注記号
				return becchuu.getBecchuuKigou();
			case 1: //　別注タイプ						
				return becchuu.getBecchuuType().getName();
			case 2: // 工事番号
				return becchuu.getKoujibangou();
			case 3: //施主名
				return becchuu.getBukken().getName();
			case 4: //　納期
				 return Helpers.stringFromDate(becchuu.getBukken().getNouki());
			case 5: // 作成者
				return becchuu.getSakuseiSha().getName();
			case 6: // 枚数
				int maisuu = becchuu.getBecchuuMaisu();
				if (maisuu < 0) return "";
				return becchuu.getBecchuuMaisu();
			case 7: //　作成状況
				return becchuu.getSakuseiStatus();
			case 8: // ミス件数
				int misu = becchuu.getMisu();
				if (misu < 0) return "";
				return becchuu.getMisu();
			case 9:
				return becchuu.getKenshuuSha().getName();
			case 10: // 研修状況
				return becchuu.getKenshuuStatus();
			case 11: //　別注DBアップ状況
				return becchuu.getUploadStatus();  
			default:
				return "Error";
			}
		}catch (Exception exception){
			return "";
		}
	}
}
