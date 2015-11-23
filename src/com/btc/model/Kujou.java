package com.btc.model;

import com.btc.repositoty.BecchuuRepository;
import com.btc.repositoty.CommonRepository;
import com.btc.repositoty.KujouRepository;

import java.util.Date;

/**
 * Created by BTC on 11/21/15.
 */
public class Kujou {
   int id;
   Date receiptDate;
   String description;
   String bikou;

   int kujouTypeId;
   int becchuuId;

   Employee kenshuuSha;
   KujouType kujouType;
   Becchuu becchuu;

   public int getBecchuuId() {
      return becchuuId;
   }

   public void setBecchuuId(int becchuuId) {
      this.becchuu = null;
      this.becchuuId = becchuuId;
   }

   public String getBikou() {
      return bikou;
   }

   public Date getReceiptDate() {
      return receiptDate;
   }

   public void setReceiptDate(Date receiptDate) {
      this.receiptDate = receiptDate;
   }

   public void setBikou(String bikou) {
      this.bikou = bikou;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getKujouTypeId() {
      return kujouTypeId;
   }

   public void setKujouTypeId(int kujouTypeId) {
      this.kujouType = null;
      this.kujouTypeId = kujouTypeId;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Employee getKenshuuSha() {
      if (this.kenshuuSha == null)
         this.kenshuuSha = CommonRepository.getBecchuuEmployeeByID(this.getBecchuu().getKenshuShaID());
      return kenshuuSha;
   }

   public KujouType getKujouType() {
      if (this.kujouType == null) this.kujouType = new KujouRepository().getKujouTypeByID(this.getKujouTypeId());
      return kujouType;
   }

   public Becchuu getBecchuu() {
      if (this.becchuu == null) this.becchuu = new BecchuuRepository().getBecchuuByID(this.getBecchuuId());
      return becchuu;
   }
}
