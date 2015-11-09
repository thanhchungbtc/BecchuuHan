package com.btc.viewModel;

import java.sql.SQLException;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.btc.controllers.DialogHelpers;
import com.btc.model.Becchuu;
import com.btc.model.Bukken;
import com.btc.repositoty.BukkenRepository;

public class BukkenTableModel extends AbstractTableModel {
	
	private static String[] COLUMN_NAMES = {"çHéñî‘çÜ", "é{éÂñº"};
	private List<Bukken> data;
	private BukkenRepository repositoty;	
	
	public BukkenTableModel(BukkenRepository repository) {
		this.repositoty = repository;
		this.data = repositoty.getList();
	}
	
	public Bukken getGroupAtIndex(int row) {
		if (row < 0 || row >= data.size())
			return null;
		return this.data.get(row);
	}
	
	public Bukken insertGroup(Bukken group) {
		try {			
			group = repositoty.insert(group);
			
			// DialogHelpers.showAlert("Congratulations!", "Success");
            fireTableRowsInserted(data.size(), data.size());
            
			return group;
		} catch (SQLException e) {			
			DialogHelpers.showError("Error occured", e);
		}	
		return null;
	}
	
	public void deleteGroup(Bukken group) {
		try {			
			int row = data.indexOf(group);
			if (row == -1) return;
			if (DialogHelpers.showConfirmMessage("Delete group", 
					"Do you want to delete this group\n id: " + String.valueOf(group.id) + "\nname: " + group.name, 1 ) == JOptionPane.YES_OPTION);
			{
				repositoty.delete(group);
				fireTableRowsDeleted(row, row);
			}			
		} catch (Exception exception) {
			DialogHelpers.showError("Error occured", exception);
		}		
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
	public int getRowCount() {		
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Bukken bukken = data.get(row);
		switch (col) {
		case 0:
			return bukken.id;			
		case 1:
			return bukken.name;						
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
