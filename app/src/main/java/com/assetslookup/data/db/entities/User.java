package com.assetslookup.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "user_table")
public class User implements Serializable {
  @SerializedName("id")
  @PrimaryKey(autoGenerate = false)
  private Long id;

  @SerializedName("_id")
  private String userId;

  @SerializedName("username")
  private String username;

  @SerializedName("password")
  private String password;

  @SerializedName("first_name")
  private String firstName;

  @SerializedName("last_name")
  private String lastName;

  @SerializedName("forgot_password_token")
  private String forgotPasswordToken;

  @Ignore
  public User() { }

  public User(String userId, String username, String firstName, String lastName, String password, String forgotPasswordToken) {
    this.id = 0L;
    this.userId = userId;
    this.forgotPasswordToken = forgotPasswordToken;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getForgotPasswordToken() {
    return forgotPasswordToken;
  }

  public void setForgotPasswordToken(String forgotPasswordToken) {
    this.forgotPasswordToken = forgotPasswordToken;
  }
}
