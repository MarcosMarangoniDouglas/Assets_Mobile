package com.assetslookup.data.db.entities;

public class SearchQuote {
  private String code;
  private String name;
  private String currency;

  public SearchQuote(String code, String name, String currency) {
    this.code = code;
    this.name = name;
    this.currency = currency;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }
}
