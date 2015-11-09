package com.btc.viewModel;

import java.nio.file.attribute.GroupPrincipal;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.btc.controllers.DialogHelpers;
import com.btc.model.Becchuu;
import com.btc.model.Bukken;
import com.btc.repositoty.BecchuuRepository;
import com.btc.repositoty.BukkenRepository;

public class BecchuuTableModel extends AbstractTableModel{
	private static String[] COLUMN_NAMES = {"Title", "User Name", "Password", "URL", "Notes" };

	private List<Becchuu> accounts;
	private BecchuuRepository repositoty;
	private int groupId;

	public BecchuuTableModel() {
		repositoty = new BecchuuRepository(0);
		this.accounts = repositoty.getList();
	}

	public BecchuuTableModel(int groupId){
		this.groupId = groupId;
		repositoty = new BecchuuRepository(groupId);
		this.accounts = repositoty.getList();
	}

	public Becchuu getAccountAtIndex(int row) {
		return this.accounts.get(row);
	}

	public Becchuu insertAccount(Becchuu account) {
		try {			
			account = repositoty.insert(account);

			// DialogHelpers.showAlert("Congratulations!", "Success");
			fireTableRowsInserted(accounts.size(), accounts.size());

			return account;
		} catch (SQLException e) {			
			DialogHelpers.showError("Error occured", e);
		}	
		return null;
	}

	public void deleteAccount(Becchuu account) {
		try {			
			int row = accounts.indexOf(account);
			if (row == -1) return;
			if (DialogHelpers.showConfirmMessage("Delete Account", 
					"Do you want to delete this account\n id: " + String.valueOf(account.userName) + "\nname: " + account.title, 1 ) == JOptionPane.YES_OPTION);
			{
				repositoty.delete(account);
				fireTableRowsDeleted(row, row);
			}			
		} catch (Exception exception) {
			DialogHelpers.showError("Error occured", exception);
		}		
	}

	public void removeRow(int row) {
		fireTableRowsDeleted(row, row);
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
		return accounts.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Becchuu account = accounts.get(row);
		switch (col) {

		case 0:
			return account.title;			
		case 1:
			return account.userName;
		case 2:
			return "Encrypted";
		case 3:
			return account.url;
		case 4:
			return account.notes;					
		default:
			break;
		}
		return "Error";
	}

	public void clearAll() {
		try{

			for (int i = 0; i < accounts.size(); i++) {
				fireTableRowsDeleted(i, i);

			}
			accounts.clear();
		} catch (Exception exception) {

		}
	}
}
