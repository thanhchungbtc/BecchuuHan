package com.btc.controllers.EmployeeManagementForm;

import java.sql.SQLException;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.btc.controllers.DialogHelpers;
import com.btc.model.Becchuu;
import com.btc.model.Bukken;
import com.btc.model.BukkenType;
import com.btc.model.Employee;
import com.btc.repositoty.BukkenRepository;
import com.btc.repositoty.CommonRepository;
import com.btc.repositoty.EmployeeRepository;
import com.btc.supports.Helpers;

public class EmployeeViewTableModel extends AbstractTableModel {
	 private static String[] COLUMN_NAMES = {"DHVN番号", "名前"};
	   private List<Employee> data;	   

	   public EmployeeViewTableModel() {	      
	      this.data =  EmployeeRepository.Instance().getEmployeesWithRefresh(false);
	   }

	   public void refresh() {

	      this.data = EmployeeRepository.Instance().getEmployeesWithRefresh(true);
	      fireTableDataChanged();
	   }

	   public Employee insertEmployee(Employee employee) {
	      try {
	    	  employee = EmployeeRepository.Instance().insert(employee);
	    	  
	         fireTableRowsInserted(data.size() - 1, data.size() - 1);
	         
	         return employee;
	      } catch (SQLException e) {
	         DialogHelpers.showError("エラー", "社員番号は既に存在しています！");
	      }
	      return null;
	   }

	   public Employee updateEmployee(Employee employee, int rowIndex) {
	      try {
	    	  employee = EmployeeRepository.Instance().update(employee);
	         fireTableRowsUpdated(rowIndex, rowIndex);

	         return employee;
	      } catch (SQLException e) {
	         DialogHelpers.showError("エラー", "今社員編集ができない！");
	      }
	      return null;
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
	      Employee employee = data.get(row);
	      switch (col) {
	         case 0:
	            return employee.getId();
	         case 1:
	            return employee.getName();
	         case 2:	        	        
	         default:
	            return "Error";
	      }
	   }


}
