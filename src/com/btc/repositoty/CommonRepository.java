package com.btc.repositoty;

import com.btc.DAL.ConnectionUtils;
import com.btc.model.BecchuuStatus;
import com.btc.model.BecchuuType;
import com.btc.model.BukkenType;
import com.btc.model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CommonRepository {
   private static List<BecchuuType> _becchuuTypes;
   private static List<BukkenType> _BukkenTypes;
   private static List<Employee> _employees;
   private static List<Employee> _becchuuHandEmployees;

   private static BecchuuStatus[] _status = {
         new BecchuuStatus(0, ""),
         new BecchuuStatus(1, "依頼中"),
         new BecchuuStatus(2, "処理済み")
   };


   public static BecchuuStatus[] getBecchuuStatus() {
      return _status;
   }

   public static List<BecchuuType> getBecchuuTypes() {
      if (_becchuuTypes != null) return _becchuuTypes;

      _becchuuTypes = new LinkedList<BecchuuType>();
      try {
         try {
            ResultSet rs = ConnectionUtils.executeQuery("SELECT * FROM BecchuuType");
            while (rs.next()) {
               int id = rs.getInt(1);
               String name = rs.getString(2);

               BecchuuType becchuuType = new BecchuuType(id, name);
               _becchuuTypes.add(becchuuType);
            }
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         }

      } catch (SQLException e) {
         System.out.println("SQL exception occured" + e);
      }
      return _becchuuTypes;
   }

   public static BecchuuType getBecchuuTypeByID(int id) {
      List<BecchuuType> becchuuTypes = getBecchuuTypes();
      for (BecchuuType becchuuType : becchuuTypes) {
         if (becchuuType.getId() == id) {
            return becchuuType;
         }
      }
      return null;
   }

   public static List<Employee> getEmployees() {
      if (_employees != null) return _employees;

      _employees = new LinkedList<Employee>();
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
               _employees.add(employee);
            }
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         }

      } catch (SQLException e) {
         System.out.println("SQL exception occured" + e);
      }
      return _employees;
   }

   public static List<Employee> getBecchuuHandEmployees() {
      List<Employee> employees = CommonRepository.getEmployees();
      List<Employee> results = new LinkedList<>();
      for (Employee employee : employees) {
         if (employee.getDepartmentID().equals("BC")) {
            results.add(employee);
         }
      }
      return results;
   }

   public static Employee getBecchuuEmployeeByID(String employeeID) {
      List<Employee> employees = CommonRepository.getBecchuuHandEmployees();
      for (Employee employee : employees) {
         if (employee.getId().equals(employeeID)) {
            return employee;
         }
      }
      return null;
   }

   public static BecchuuStatus getBecchuuStatusWithID(int statusID) {
      for (BecchuuStatus status : _status) {
         if (status.getId() == statusID) {
            return status;
         }
      }
      return null;
   }
}
