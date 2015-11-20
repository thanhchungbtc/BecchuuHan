package com.btc.repositoty;

import com.btc.DAL.ConnectionUtils;
import com.btc.model.Bukken;
import com.btc.model.Employee;
import com.btc.supports.Helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by BTC on 11/15/15.
 */
public class EmployeeRepository {
   private List<Employee> data;
   private Connection connection;

   private static EmployeeRepository _instance;
   
   private EmployeeRepository() {
   }

   public static EmployeeRepository Instance() {
      if (_instance == null) _instance = new EmployeeRepository();
      return _instance;
   }

   public Employee login(String userID, String password) {
      List<Employee> employees = CommonRepository.getEmployees();
      for (Employee employee : employees) {
         if (employee.getId().equals(userID) && employee.getPassword().equals(password)) {
            return employee;
         }
      }
      return null;
   }
   
   public void updatePassword(String userID, String newPassword) {
	   String sql = "UPDATE Employee SET password = ? WHERE id = ?";
	      try {
	         connection = ConnectionUtils.getConnection();
	         PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
	 	      statement.setString(1, newPassword);
            statement.setString(2, userID);
		      statement.executeUpdate();
		      statement.close();
		      statement = null;

		      connection.close();
		      connection = null;
	      } catch (ClassNotFoundException ex) {
	         Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
	      } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
   }
   
   public Employee insert(Employee employee) throws SQLException {
	      String sql = "INSERT INTO Employee (id, name, department_id) VALUES(?, ?, ?)";
	      try {
	         connection = ConnectionUtils.getConnection();
	      } catch (ClassNotFoundException ex) {
	         Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
	      }

	      int valIndex = 1;
	      PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
	      statement.setString(valIndex++, employee.getId());
	      statement.setString(valIndex++, employee.getName());	    
	      statement.setString(valIndex++, employee.getDepartmentID());	
	      statement.executeUpdate();
	      statement.close();
	      statement = null;

	      connection.close();
	      connection = null;
	      //data.add(employee);
	      return employee;
	   }

	   public Employee update(Employee employee) throws SQLException {
	      String sql = "UPDATE Employee name = ? WHERE id = ?";
	      try {
	         connection = ConnectionUtils.getConnection();
	      } catch (ClassNotFoundException ex) {
	         System.out.println("Could not connect to database");
	         Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
	      }
	      int valIndex = 1;
	      PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
	      statement.setString(valIndex++, employee.getName());
	      statement.setString(valIndex++, employee.getId());
	      statement.executeUpdate();
	      statement.close();
	      statement = null;

	      connection.close();
	      connection = null;

	      return employee;
	   }

	   public List<Employee> getEmployeesWithRefresh(boolean refresh) {
		      if (data != null) {
		    	  if (!refresh) {
		    		  return this.data;		    		  
		    	  } else {
		    		  data.clear();
		    	  }
		      }
		      

		      data = new LinkedList<Employee>();
		      try {
		         try {
		            ResultSet rs = ConnectionUtils.executeQuery("SELECT * FROM Employee");
		            while (rs.next()) {
		               int valIndex = 1;
		               Employee employee = new Employee(rs.getString(valIndex++), rs.getString(valIndex++), rs.getString(valIndex++));
		               employee.setHonshaBangou(rs.getString(valIndex++));
		               employee.setEmail(rs.getString(valIndex++));
		               employee.setPassword(rs.getString(valIndex++));
		               employee.setRoleID(rs.getString(valIndex++));
		               data.add(employee);
		            }
		         } catch (ClassNotFoundException e) {
		            e.printStackTrace();
		         }

		      } catch (SQLException e) {
		         System.out.println("SQL exception occured" + e);
		      }
		      return data;
		   }
}
