package com.dragon.patternframework.javaBean;

public class Message  {

  private long id;
  private String message;
  private long useId;
  private long isSys;
  private long isRead;
  private String nickname;
  private String image;
  private long fromId;
  private long isHan;
  private long ordId;
  public Message() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  public long getUseId() {
    return useId;
  }

  public void setUseId(long useId) {
    this.useId = useId;
  }


  public long getIsSys() {
    return isSys;
  }

  public void setIsSys(long isSys) {
    this.isSys = isSys;
  }

  public long getIsRead() {
    return isRead;
  }

  public void setIsRead(long isRead) {
    this.isRead = isRead;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }


  public long getIsHan() {
    return isHan;
  }

  public void setIsHan(long isHan) {
    this.isHan = isHan;
  }



  public long getOrdId() {
    return ordId;
  }

  public void setOrdId(long ordId) {
    this.ordId = ordId;
  }

  public long getFromId() {
    return fromId;
  }

  public void setFromId(long fromId) {
    this.fromId = fromId;
  }
}
