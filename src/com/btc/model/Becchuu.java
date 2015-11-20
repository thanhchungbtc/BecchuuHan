package com.btc.model;

import com.btc.repositoty.BukkenRepository;
import com.btc.repositoty.CommonRepository;

import java.util.Date;

public class Becchuu {
   // primary key
   private int id;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   private String becchuuKigou;

   private String becchuuParameter;
   private String becchuuNaiyou;
   private String motozuKigou;
   private String motozuParameter;
   private String bikou;
   private Date iraibi;
   private Date sakuseiBi;

   public String getHinCode() {
      return hinCode;
   }

   public void setHinCode(String hinCode) {
      this.hinCode = hinCode;
   }

   private int misu;
   private int becchuuMaisu;
   private String iraiShaID;
   private String hinCode;

   // foreign key
   private int sakuseiStatusID;
   private int kenshuuStatusID;
   private int uploadStatusID;
   private int becchuuTypeID;
   private String koujibangou;
   private String sakuseiShaID;
   private String kenshuShaID;

   // use for foreignkey
   private Employee sakuseiSha;
   private Employee kenshuuSha;
   private Employee iraiSha;
   private BecchuuType becchuuType;
   private Bukken bukken;
   private BecchuuStatus sakuseiStatus;

   public Employee getIraiSha() {
      if (iraiSha == null) this.iraiSha = CommonRepository.getEmployeeByID(iraiShaID);
      return iraiSha;
   }

   private BecchuuStatus kenshuuStatus;
   private BecchuuStatus uploadStatus;

   public BecchuuStatus getSakuseiStatus() {
      if (sakuseiStatus == null) sakuseiStatus = CommonRepository.getBecchuuStatusWithID(this.sakuseiStatusID);
      return sakuseiStatus;
   }

   public BecchuuStatus getKenshuuStatus() {
      if (kenshuuStatus == null) kenshuuStatus = CommonRepository.getBecchuuStatusWithID(this.kenshuuStatusID);
      return kenshuuStatus;
   }

   public BecchuuStatus getUploadStatus() {
      if (uploadStatus == null) uploadStatus = CommonRepository.getBecchuuStatusWithID(this.uploadStatusID);
      return uploadStatus;
   }


   public Becchuu() {
   }

   public Bukken getBukken() {
      if (this.bukken == null) this.bukken = BukkenRepository.Instance().getBukkenWithID(this.getKoujibangou());
      return bukken;
   }

   public String getBecchuuKigou() {
      return becchuuKigou;
   }

   public void setBecchuuKigou(String becchuuKigou) {
      this.becchuuKigou = becchuuKigou;
   }

   public String getBecchuuParameter() {
      return becchuuParameter;
   }

   public void setBecchuuParameter(String becchuuParameter) {
      this.becchuuParameter = becchuuParameter;
   }

   public String getBecchuuNaiyou() {
      return becchuuNaiyou;
   }

   public void setBecchuuNaiyou(String becchuuNaiyou) {
      this.becchuuNaiyou = becchuuNaiyou;
   }

   public String getMotozuKigou() {
      return motozuKigou;
   }

   public void setMotozuKigou(String motozuKigou) {
      this.motozuKigou = motozuKigou;
   }

   public String getMotozuParameter() {
      return motozuParameter;
   }

   public void setMotozuParameter(String motozuParameter) {
      this.motozuParameter = motozuParameter;
   }

   public String getBikou() {
      return bikou;
   }

   public void setBikou(String bikou) {
      this.bikou = bikou;
   }

   public Date getIraibi() {
      return iraibi;
   }

   public void setIraibi(Date iraibi) {
      this.iraibi = iraibi;
   }

   public Date getSakuseiBi() {
      return sakuseiBi;
   }

   public void setSakuseiBi(Date sakuseiBi) {
      this.sakuseiBi = sakuseiBi;
   }

   public int getMisu() {
      //if (misu < 0) return 0; // throw new NumberFormatException("Becchuu Misu not a valid number");
      return misu;
   }

   public void setMisu(int misu) {
      this.misu = misu;
   }

   public int getBecchuuMaisu() {
      //if (becchuuMaisu < 0) return 0;// throw new NumberFormatException("Becchuu Maisuu not a valid number");
      return becchuuMaisu;
   }

   public void setBecchuuMaisu(int becchuuMaisu) {
      this.becchuuMaisu = becchuuMaisu;
   }

   public int getSakuseiStatusID() {
      return sakuseiStatusID;
   }

   public void setSakuseiStatusID(int sakuseiStatusID) {
      this.sakuseiStatus = null;
      this.sakuseiStatusID = sakuseiStatusID;
   }

   public int getKenshuuStatusID() {
      return kenshuuStatusID;
   }

   public void setKenshuuStatusID(int kenshuuStatusID) {
      this.kenshuuStatus = null;
      this.kenshuuStatusID = kenshuuStatusID;
   }

   public int getUploadStatusID() {
      return uploadStatusID;
   }

   public void setUploadStatusID(int uploadStatusID) {
      this.uploadStatus = null;
      this.uploadStatusID = uploadStatusID;
   }

   public String getKoujibangou() {
      return koujibangou;
   }

   public void setKoujibangou(String koujibangou) {
      this.bukken = null;
      this.koujibangou = koujibangou;
   }

   public String getSakuseiShaID() {
      return sakuseiShaID;
   }

   public void setSakuseiShaID(String sakuseiShaID) {
      this.sakuseiSha = null;
      this.sakuseiShaID = sakuseiShaID;
   }

   public String getKenshuShaID() {
      return kenshuShaID;
   }

   public void setKenshuShaID(String kenshuShaID) {
      this.kenshuuSha = null;
      this.kenshuShaID = kenshuShaID;
   }

   public int getBecchuuTypeID() {
      return becchuuTypeID;
   }

   public void setBecchuuTypeID(int becchuuTypeID) {
      this.becchuuType = null;
      this.becchuuTypeID = becchuuTypeID;
   }


   public Employee getSakuseiSha() {
      if (this.sakuseiSha == null) this.sakuseiSha = CommonRepository.getBecchuuEmployeeByID(sakuseiShaID);
      return sakuseiSha;
   }

   public Employee getKenshuuSha() {
      if (kenshuuSha == null) this.kenshuuSha = CommonRepository.getBecchuuEmployeeByID(kenshuShaID);
      return kenshuuSha;
   }

   public BecchuuType getBecchuuType() {
      if (this.becchuuType == null) this.becchuuType = CommonRepository.getBecchuuTypeByID(becchuuTypeID);
      return becchuuType;
   }

   public String getIraiShaID() {
      return iraiShaID;
   }

   public void setIraiShaID(String iraiSha_id) {
      this.iraiShaID = iraiSha_id;
   }

   @Override
   public String toString() {
      // TODO Auto-generated method stub
      return "becchuuKigou: " + becchuuKigou
            + "motozuKigou: " + motozuKigou;
   }

   public String getBecchuuDBURL() {
      Bukken bukken = this.getBukken();
      String depsf = bukken.getDepsf().replaceAll("-", "");
      return "http://sv04plemia.osaka.daiwahouse.co.jp/BzkWeb/download/ZumenDL.aspx?type=0&item=" + depsf.trim() + "," + this.getHinCode().trim();      
   }
}
