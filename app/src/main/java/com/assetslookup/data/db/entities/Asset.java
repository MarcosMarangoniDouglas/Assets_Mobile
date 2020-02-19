package com.assetslookup.data.db.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Asset {
  @SerializedName("_id")
  private  String id;
  @SerializedName("user_id")
  private String userId;
  private String name;
  private String code;
  private boolean autorefresh;
  private Double balance;
  private Double irr;
  @SerializedName("group_a")
  private String groupA;
  @SerializedName("group_b")
  private String groupB;
  @SerializedName("group_c")
  private String groupC;

  List<Movement> movements = new ArrayList<>();

  public Asset(String id, String userId, String name, String code, boolean autorefresh, Double balance, Double irr, String groupA, String groupB, String groupC, List<Movement> movements) {
    this.id = id;
    this.userId = userId;
    this.name = name;
    this.code = code;
    this.autorefresh = autorefresh;
    this.balance = balance;
    this.irr = irr;
    this.groupA = groupA;
    this.groupB = groupB;
    this.groupC = groupC;
    this.movements = movements;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public boolean isAutorefresh() {
    return autorefresh;
  }

  public void setAutorefresh(boolean autorefresh) {
    this.autorefresh = autorefresh;
  }

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public Double getIrr() {
    return irr;
  }

  public void setIrr(Double irr) {
    this.irr = irr;
  }

  public String getGroupA() {
    return groupA;
  }

  public void setGroupA(String groupA) {
    this.groupA = groupA;
  }

  public String getGroupB() {
    return groupB;
  }

  public void setGroupB(String groupB) {
    this.groupB = groupB;
  }

  public String getGroupC() {
    return groupC;
  }

  public void setGroupC(String groupC) {
    this.groupC = groupC;
  }

  public List<Movement> getMovements() {
    return movements;
  }

  public void setMovements(List<Movement> movements) {
    this.movements = movements;
  }
}
