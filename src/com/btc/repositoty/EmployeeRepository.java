package com.btc.repositoty;

import com.btc.DAL.ConnectionUtils;
import com.btc.model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by BTC on 11/15/15.
 */
public class EmployeeRepository extends GenericRepository<Employee> {

   private static EmployeeRepository _instance;

   private EmployeeRepository() {
   }

   public static final EmployeeRepository Instance() {
      if (_instance == null) _instance = new EmployeeRepository();
      return _instance;
   }

   @Override
   protected void initData() {
      data = new ArrayList<Employee>();
      try {
         try {
            ResultSet rs = ConnectionUtils.executeQuery("SELECT * FROM Employee");
            while (rs.next()) {
               int valIndex = 1;
               Employee employee = new Employee(rs.getString(valIndex++), rs.getString(valIndex++), null);
               data.add(employee);
            }
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         }

      } catch (SQLException e) {
         System.out.println("SQL exception occured" + e);
      }
   }

   @Override
   public Employee getById(Object id) {
      List<Employee> data = getListWithRefresh(false);
      for (Employee employee : data) {
         if (employee.getId().equals(id)) {
            return employee;
         }
      }
      return null;
   }

   @Override
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
      if (data != null)
         data.add(employee);
      return employee;
   }

   @Override
   public Employee update(Employee employee) throws SQLException {
      String sql = "UPDATE Employee SET name = ? WHERE id = ?";
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

   @Override
   public boolean delete(Employee employee) throws SQLException {
      if (employee == null) {
         return false;
      }
      String sql = "DELETE FROM Employee WHERE id = " + employee.getId() + "";
      try {
         connection = ConnectionUtils.getConnection();
      } catch (ClassNotFoundException ex) {
         Logger.getLogger(BukkenRepository.class.getName()).log(Level.SEVERE, null, ex);
      }
      Statement st = connection.createStatement();
      if (data != null)
         data.remove(employee);
      boolean result = st.execute(sql);
      connection.close();
      return result;
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

   public List<Employee> getBecchuuHandEmployees() {
      List<Employee> employees = CommonRepository.getEmployees();
      List<Employee> results = new LinkedList<>();
      for (Employee employee : employees) {
         if (employee.getDepartmentID().equals("BC")) {
            results.add(employee);
         }
      }
      return results;
   }
}