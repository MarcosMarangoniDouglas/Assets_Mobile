package com.assetslookup.data.db.entities;

public class Movement {

  private String date;
  private String kind;
  private Double value;
  private String comment;

  public Movement(String date, String kind, Double value, String comment) {
    this.date = date;
    this.kind = kind;
    this.value = value;
    this.comment = comment;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
