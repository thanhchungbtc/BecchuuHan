package com.btc.controllers.SeisekiHoukokuForm;

/**
 * Created by BTC on 11/22/15.
 */
public class PersonSeisekiViewModel {
   private String name;
   private String employeeID;
   private String datePattern;

   public String getDatePattern() {
      return datePattern;
   }

   public void setDatePattern(String datePattern) {
      this.datePattern = datePattern;
   }

   public String getEmployeeID() {
      return employeeID;
   }

   public void setEmployeeID(String employeeID) {
      this.employeeID = employeeID;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }
}
