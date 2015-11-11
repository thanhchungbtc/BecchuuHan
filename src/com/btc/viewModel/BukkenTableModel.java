package com.btc.viewModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import com.btc.controllers.DialogHelpers;
import com.btc.model.Becchuu;
import com.btc.model.Bukken;
import com.btc.repositoty.BukkenRepository;
import com.btc.supports.BukkenType;
import com.btc.supports.Helpers;

public class BukkenTableModel extends AbstractTableModel {
	
	private static String[] COLUMN_NAMES = {"納期", "工事番号", "施主名", "タイプ", "システムへ"};
	private List<Bukken> data;
	private BukkenRepository repository;	
	
	public BukkenTableModel(BukkenRepository repository) {
		this.repository = repository;
		this.data = repository.getList();
	}
	
	public void refresh() {
		
		this.data = repository.getList();
		fireTableDataChanged();
	}
	
	public Bukken insertBukken(Bukken bukken) {
		try {			
			bukken = repository.insert(bukken);
		
			
            fireTableRowsInserted(data.size() - 1, data.size() - 1);
            
			return bukken;
		} catch (SQLException e) {			
			DialogHelpers.showError("エラー", "工事番号は既に存在しています！");
		}	
		return null;
	}
	
	public Bukken updateBukken(Bukken bukken, int rowIndex) {
		try {			
			bukken = repository.update(bukken);	
			fireTableRowsUpdated(rowIndex, rowIndex);
            
			return bukken;
		} catch (SQLException e) {			
			DialogHelpers.showError("エラー", "今物件編集ができない！");
		}	
		return null;
	}
	
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
		Bukken bukken = data.get(row);
		switch (col) {
		case 0:
			return Helpers.stringFromDate(bukken.getNouki());
		case 1:
			return bukken.getId();			
		case 2:
			return bukken.getName();
		case 3:
			return BukkenType.getType(bukken.getType());
		case 4:
			return "<html><FONT color=\"#000099\"><u>別注ＤＢへ</u></html> ";
		default:
			return "Error";
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
