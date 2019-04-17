package com.dragon.patternframework.javaBean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Commodity implements Serializable {
  private long id;
  private User user;
  private long useId;
  private String comLabel="";
  private String note="";
  private long comStatus=0;
  @SerializedName("noexists")
  private java.sql.Timestamp comTime;
  @SerializedName("comTime")
  private String time;
  private double price=0;
  private String email="";
  private long corder;
  private long amount=1;
  private String tab;
  private long isDispatch=0;
  private long isBargain=0;
  private String intId="";
  private String qq="";
  private String images="";
  private String phone="";
  private String notice="";
  private String deal="";


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getComLabel() {
    return comLabel;
  }

  public void setComLabel(String comLabel) {
    this.comLabel = comLabel;
  }


  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }


  public long getComStatus() {
    return comStatus;
  }

  public void setComStatus(long comStatus) {
    this.comStatus = comStatus;
  }


  public java.sql.Timestamp getComTime() {
    return comTime;
  }

  public void setComTime(java.sql.Timestamp comTime) {
    this.comTime = comTime;
  }


  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public long getCorder() {
    return corder;
  }

  public void setCorder(long corder) {
    this.corder = corder;
  }


  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }

  public long getIsDispatch() {
    return isDispatch;
  }

  public void setIsDispatch(long isDispatch) {
    this.isDispatch = isDispatch;
  }


  public long getIsBargain() {
    return isBargain;
  }

  public void setIsBargain(long isBargain) {
    this.isBargain = isBargain;
  }


  public String getQq() {
    return qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }


  public String getImages() {
    return images;
  }

  public void setImages(String images) {
    this.images = images;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getNotice() {
    return notice;
  }

  public void setNotice(String notice) {
    this.notice = notice;
  }


  public String getDeal() {
    return deal;
  }

  public void setDeal(String deal) {
    this.deal = deal;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getTab() {
    return tab;
  }

  public void setTab(String tab) {
    this.tab = tab;
  }


  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public long getUseId() {
    return useId;
  }

  public void setUseId(long useId) {
    this.useId = useId;
  }

  public String getIntId() {
    return intId;
  }

  public void setIntId(String intId) {
    this.intId = intId;
  }
}
