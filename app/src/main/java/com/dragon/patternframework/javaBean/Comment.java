package com.dragon.patternframework.javaBean;

public class Comment  {

  private long id;
  private long useId;
  private String message;
  private java.sql.Timestamp time;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getUseId() {
    return useId;
  }

  public void setUseId(long useId) {
    this.useId = useId;
  }


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  public java.sql.Timestamp getTime() {
    return time;
  }

  public void setTime(java.sql.Timestamp time) {
    this.time = time;
  }

}
