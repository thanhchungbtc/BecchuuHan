package com.btc.model;

import com.btc.repositoty.EmployeeRepository;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by 130708 on 2015/11/23.
 */
public class Zangyou {
   private int id;
   private String employeeID;
   private Date startDate;
   private Date toDate;
   private Float duration;

   private Employee employee;

   public float getDuration() {
      if (duration != null) return duration;
      Date start = getStartDate();
      Date end = getToDate();
      Calendar calendar1 = Calendar.getInstance();
      Calendar calendar2 = Calendar.getInstance();
      calendar1.setTime(start);
      calendar2.setTime(end);
      long diffHour = calendar2.get(Calendar.HOUR) - calendar1.get(Calendar.HOUR);
      long diffMin = (calendar2.get(Calendar.MINUTE) - calendar1.get(Calendar.MINUTE));
      long minute = 60 * diffHour + (diffMin / 30) * 30;
      this.duration = (float) minute / 60;
      return duration;
   }

   public Date getToDate() {
      return toDate;
   }

   public void setToDate(Date toDate) {
      this.toDate = toDate;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getEmployeeID() {
      return employeeID;
   }

   public void setEmployeeID(String employeeID) {
      this.employee = null;
      this.employeeID = employeeID;
   }

   public Employee getEmployee() {
      if (this.employee == null) this.employee = EmployeeRepository.Instance().getById(this.employeeID);
      return employee;
   }

   public Date getStartDate() {
      return startDate;
   }

   public void setStartDate(Date startDate) {
      this.startDate = startDate;
   }
}
