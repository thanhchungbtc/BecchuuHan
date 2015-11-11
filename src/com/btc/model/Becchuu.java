package com.btc.model;

import java.util.Date;

import com.btc.repositoty.BukkenRepository;
import com.btc.repositoty.CommonRepository;

public class Becchuu {
	// primary key
	private String becchuuKigou; 
	
	private String becchuuParameter;
	private String becchuuNaiyou;
	private String motozuKigou;
	private String motozuParameter;
	private String bikou;
	private Date iraibi;
	private Date sakuseiBi;
	private int misu;
	private int becchuuMaisu;
	private int sakuseiStatus;
	private int kenshuuStatus;
	private int uploadStatus;	
	
	// foreign key
	private String koujibangou;
	private String sakuseiShaID;
	private String kenshuShaID;
	private int becchuuTypeID;
	private String iraiShaID;
	
	public Employee sakuseiSha;
	public Employee kenshuuSha;
	public BecchuuType becchuuType;
	public Bukken bukken;
	
	public Bukken getBukken() {
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
		return misu;
	}
	public void setMisu(int misu) {
		this.misu = misu;
	}
	public int getBecchuuMaisu() {
		return becchuuMaisu;
	}
	public void setBecchuuMaisu(int becchuuMaisu) {
		this.becchuuMaisu = becchuuMaisu;
	}
	public int getSakuseiStatus() {
		return sakuseiStatus;
	}
	public void setSakuseiStatus(int sakuseiStatus) {
		this.sakuseiStatus = sakuseiStatus;
	}
	public int getKenshuuStatus() {
		return kenshuuStatus;
	}
	public void setKenshuuStatus(int kenshuuStatus) {
		this.kenshuuStatus = kenshuuStatus;
	}
	public int getUploadStatus() {
		return uploadStatus;
	}
	public void setUploadStatus(int uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	public String getKoujibangou() {
		return koujibangou;
	}
	public void setKoujibangou(String koujibangou) {
		this.koujibangou = koujibangou;
		
	}
	public String getSakuseiShaID() {
		return sakuseiShaID;
	}
	public void setSakuseiShaID(String sakuseiShaID) {
		this.sakuseiShaID = sakuseiShaID;
		this.sakuseiSha = CommonRepository.getBecchuuEmployeeByID(sakuseiShaID);
	}
	public String getKenshuShaID() {
		return kenshuShaID;
	}
	public void setKenshuShaID(String kenshuShaID) {
		this.kenshuShaID = kenshuShaID;
		this.kenshuuSha = CommonRepository.getBecchuuEmployeeByID(kenshuShaID);
	}
	public int getBecchuuTypeID() {
		return becchuuTypeID;
	}
	public void setBecchuuTypeID(int becchuuTypeID) {
		this.becchuuTypeID = becchuuTypeID;
		this.becchuuType = CommonRepository.getBecchuuTypeByID(becchuuTypeID);
	}
	public Employee getSakuseiSha() {
		return sakuseiSha;
	}
	public Employee getKenshuuSha() {
		return kenshuuSha;
	}
	public BecchuuType getBecchuuType() {
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
	
}
