package com.assetslookup.data.db.entities;

import com.google.gson.annotations.SerializedName;

public class Movement {

  @SerializedName("_id")
  private String id;
  private String date;
  private String kind;
  private Double value;
  private String comment;

  public Movement(String id, String date, String kind, Double value, String comment) {
    this.id = id;
    this.date = date;
    this.kind = kind;
    this.value = value;
    this.comment = comment;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
