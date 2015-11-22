package com.btc.model;

/**
 * Created by BTC on 11/21/15.
 */
public class KujouType {
   int id;
   String name;

   public KujouType(int id, String name) {
      this.id = id;
      this.name = name;
   }

   public int getId() {
      return id;
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
      return this.getName();
   }
}
