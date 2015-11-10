package com.btc.repositoty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.btc.DAL.ConnectionUtils;
import com.btc.model.BecchuuType;
import com.btc.model.Bukken;
import com.btc.model.Employee;
import com.btc.supports.Helpers;

public class CommonRepository {
	private static List<BecchuuType> _becchuuTypes;
	private static List<Employee> _employees;
	private static List<Employee> _becchuuHandEmployees;
	
	public static List<BecchuuType> getBecchuuTypes() {
		if (_becchuuTypes != null) return _becchuuTypes;
		
		_becchuuTypes = new LinkedList<BecchuuType>();	    
		try {
			try {
				ResultSet rs = ConnectionUtils.executeQuery ("SELECT * FROM BecchuuType");		         
				while (rs.next()) {
					int id = rs.getInt(1);
					String name = rs.getString(2);

					BecchuuType becchuuType = new BecchuuType(name);
					becchuuType.setId(id);	    		           

					_becchuuTypes.add(becchuuType);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
			
		}
		catch(SQLException e){
			System.out.println("SQL exception occured" + e);
		}
		return _becchuuTypes;
	}
	
	public static List<Employee> getEmployees() {
		if (_employees != null) return _employees;
		
		_employees = new LinkedList<Employee>();	    
		try {
			try {
				ResultSet rs = ConnectionUtils.executeQuery ("SELECT * FROM Employee");		         
				while (rs.next()) {
					Employee employee = new Employee(rs.getString(1), rs.getString(2), rs.getString(3));          

					_employees.add(employee);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 

		}
		catch(SQLException e){
			System.out.println("SQL exception occured" + e);
		}
		return _employees;
	}
	
	public static List<Employee> getBecchuuHandEmployees() {
		List<Employee> employees = CommonRepository.getEmployees();
		List<Employee> results = new LinkedList<>();
		for (Employee employee: employees) {
			if (employee.getDepartmentID().equals("BC")) {
				results.add(employee);
			}
		}
		return results;
	}
}
