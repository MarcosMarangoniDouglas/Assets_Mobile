package com.assetslookup.data.db.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *     user_id: {
 *       type: mongoose.Schema.Types.ObjectId,
 *       required: [true, 'A valid user is necessary'],
 *       ref: 'user',
 *     },
 *     name: {
 *       type: String,
 *       default: '',
 *     },
 *     code: {
 *       type: String, required: [true, 'A code is necessary'],
 *       max: [20, 'Sorry you reached the maximum number of characters']
 *     },
 *     autorefresh: { type: Boolean, default: false },
 *     balance: { type: Number, default: 0 },
 *     unit: { type: Number, default: 0 },
 *     irr: Number,
 *     group_a: { type: String, default: '' },
 *     group_b: { type: String, default: '' },
 *     group_c: { type: String, default: '' },
 *
 *     movements: [{ type: MovementSchema, required: [true, 'A movement is needed'] }],
 */

public class Asset {
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

  List<Movement> movements;

  public Asset(String userId, String name, String code, boolean autorefresh, Double balance, Double irr, String groupA, String groupB, String groupC, List<Movement> movements) {
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
