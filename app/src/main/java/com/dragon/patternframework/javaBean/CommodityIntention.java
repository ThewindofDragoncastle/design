package com.dragon.patternframework.javaBean;

import android.support.annotation.NonNull;

import java.util.List;

public class CommodityIntention {
  private List<Comment> commentc;
  private long id;
  private java.sql.Timestamp cTime;
  private String message;
  private long Com_id;
  private String com_name;
  private long commentAcount;
  private long ilike;
  private long useId;
  private String comId;
  private String images;
  private String nickName;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public java.sql.Timestamp getCTime() {
    return cTime;
  }

  public void setCTime(java.sql.Timestamp cTime) {
    this.cTime = cTime;
  }


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  public long getCommentAcount() {
    return commentAcount;
  }

  public void setCommentAcount(long commentAcount) {
    this.commentAcount = commentAcount;
  }


  public long getIlike() {
    return ilike;
  }

  public void setIlike(long ilike) {
    this.ilike = ilike;
  }


  public long getUseId() {
    return useId;
  }

  public void setUseId(long useId) {
    this.useId = useId;
  }


  public String getComId() {
    return comId;
  }

  public void setComId(String comId) {
    this.comId = comId;
  }

  public String getImages() {
    return images;
  }

  public void setImages(String images) {
    this.images = images;
  }



  public List<Comment> getCommentc() {
    return commentc;
  }

  public void setCommentc(List<Comment> commentc) {
    this.commentc = commentc;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public long getCom_id() {
    return Com_id;
  }

  public void setCom_id(long com_id) {
    Com_id = com_id;
  }


  public String getCom_name() {
    return com_name;
  }

  public void setCom_name(String com_name) {
    this.com_name = com_name;
  }


}
