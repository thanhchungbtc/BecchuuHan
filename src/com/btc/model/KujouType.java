package com.btc.model;

/**
 * Created by BTC on 11/21/15.
 */
public class KujouType {
   int id;
   String name;
   int point;

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

   public int getPoint() {
      return point;
   }

   public void setPoint(int point) {
      this.point = point;
   }

   @Override
   public String toString() {
      return this.getName();
   }
}
