package com.btc.model;

import com.btc.repositoty.BecchuuRepository;
import com.btc.repositoty.BukkenRepository;
import com.btc.supports.Config;

import java.util.Date;
import java.util.List;

public class Bukken {

   private String id;
   private String name;
   private String depsf;
   private String shiten;

   public String getYobikata() {
      return yobikata;
   }

   public void setYobikata(String yobikata) {
      this.yobikata = yobikata;
   }

   private Date nouki;
   private String yobikata;

   private int type; // 0 is XEVO, 1 is Σ
   private String bukkenType;

   public String getBukkenType() {
      if (bukkenType == null) bukkenType = new BukkenRepository().geBukkenTypeWithID(type);
      return bukkenType;
   }

   public Bukken() {

   }

   public Bukken(String id, String name, String depsf, int type, String shiten, Date nouki) {
      this.id = id;
      this.name = name;
      this.depsf = depsf;
      this.type = type;
      this.shiten = shiten;
      this.nouki = nouki;
   }

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

   public String getDepsf() {
      return depsf;
   }

   public void setDepsf(String depsf) {
      this.depsf = depsf;
   }

   public int getType() {
      return type;
   }

   public void setType(int type) {
      this.type = type;
   }

   public String getShiten() {
      return shiten;
   }

   public void setShiten(String shiten) {
      this.shiten = shiten;
   }

   public Date getNouki() {
      return nouki;
   }

   public void setNouki(Date nouki) {
      this.nouki = nouki;
   }

   public String getBecchuuDBURL() {
      String depsf = this.getDepsf().replaceAll("-", "");
      String link = "http://sv04plemia.osaka.daiwahouse.co.jp/BzkWeb/search.aspx?zwid=" + depsf.trim() + "&user=" + Config.HonShaBangou;
      return link;
   }

   public List<Becchuu> getListBecchuu() {
      return new BecchuuRepository().getBecchuuOfBukken(this.getId());
   }
}
