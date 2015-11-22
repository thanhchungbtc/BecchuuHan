package com.btc.model;

public class BukkenType {
   public final static int XEVO = 0;
   public final static int SHIGUMA = 1;
   public final static String[] data = {"XEVO", "Σ"};

   int id;
   String name;

   public BukkenType(int id, String name) {
      this.id = id;
      this.name = name;
   }

   public final static String getType(int id) {
      if (id < 0 || id > BukkenType.data.length - 1) {
         throw new IndexOutOfBoundsException();
      }
      return BukkenType.data[id];
   }

   public String getName() {
      return name;
   }

   @Override
   public String toString() {
      return this.getName();

   }
}
