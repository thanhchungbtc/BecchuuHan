package com.btc.model;

public class Employee {
   private String id;

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   private String name;
   private String departmentID;

   public String getRoleID() {
      return roleID;
   }

   public void setRoleID(String roleID) {
      this.roleID = roleID;
   }

   private String honshaBangou;
   private String password;
   private String email;
   private String roleID;
   public String getHonshaBangou() {
      return honshaBangou;
   }

   public void setHonshaBangou(String honshaBangou) {
      this.honshaBangou = honshaBangou;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Employee(String id, String name, String departmentID) {
      super();
      this.id = id;
      this.name = name;
      this.departmentID = departmentID;
   }

   public Employee() { }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDepartmentID() {
      return departmentID;
   }

   public void setDepartmentID(String departmentID) {
      this.departmentID = departmentID;
   }

   @Override
   public String toString() {
      return this.getName();
   }

   @Override
   public boolean equals(Object arg0) {
      if (arg0 instanceof Employee) {
         Employee other = (Employee) arg0;
         return (this.getId().equals(other.getId()));
      }
      return false;
   }

   @Override
   public int hashCode() {
      // TODO Auto-generated method stub
      return super.hashCode();
   }

}
