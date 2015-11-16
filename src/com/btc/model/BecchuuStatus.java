package com.btc.model;

public class BecchuuStatus {
   private int id;
   private String name;

   public int getId() {
      return id;
   }

   public BecchuuStatus(int id, String name) {
      this.id = id;
      this.name = name;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Override
   public String toString() {
      return this.name;
   }

}
