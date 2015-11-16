package com.btc.repositoty;

import com.btc.DAL.ConnectionUtils;
import com.btc.model.Employee;
import com.btc.supports.Helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

}
