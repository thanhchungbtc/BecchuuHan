package com.btc.model;

public class BukkenType {
   public final static int XEVO = 0;
   public final static int SHIGUMA = 1;
   public final static String[] data = {"XEVO", "Σ"};

   public final static String getType(int id) {
      if (id < 0 || id > BukkenType.data.length - 1) {
         System.out.println(id);
         throw new IndexOutOfBoundsException();
      }
      return BukkenType.data[id];
   }
}
