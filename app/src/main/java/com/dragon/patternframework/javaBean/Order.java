package com.dragon.patternframework.javaBean;

import java.io.Serializable;
import java.sql.Timestamp;

public class Order implements Serializable {
  private long id;
  private long comId;
  private String note;
  private java.sql.Timestamp startTime;
  private java.sql.Timestamp endTime;
  private String place;
  private long status;
  private java.sql.Timestamp ordStart;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getComId() {
    return comId;
  }

  public void setComId(long comId) {
    this.comId = comId;
  }


  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }


  public java.sql.Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(java.sql.Timestamp startTime) {
    this.startTime = startTime;
  }


  public java.sql.Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(java.sql.Timestamp endTime) {
    this.endTime = endTime;
  }


  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }

  public Timestamp getOrdStart() {
    return ordStart;
  }

  public void setOrdStart(Timestamp ordStart) {
    this.ordStart = ordStart;
  }
}
