package com.assetslookup.data.db.entities;

import com.google.gson.annotations.SerializedName;

public class Quote {
  private String symbol;
  private Double open;
  private Double high;
  private Double low;
  private Double price;
  private Double volume;
  @SerializedName("lastest_trading_day")
  private String lastestTradingDay;
  @SerializedName("previous_close")
  private Double previousClose;
  private Double change;
  @SerializedName("change_percent")
  private Double changePercent;

  public Quote(String symbol, Double open, Double high, Double low, Double price, Double volume, String lastestTradingDay, Double previousClose, Double change, Double changePercent) {
    this.symbol = symbol;
    this.open = open;
    this.high = high;
    this.low = low;
    this.price = price;
    this.volume = volume;
    this.lastestTradingDay = lastestTradingDay;
    this.previousClose = previousClose;
    this.change = change;
    this.changePercent = changePercent;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Double getOpen() {
    return open;
  }

  public void setOpen(Double open) {
    this.open = open;
  }

  public Double getHigh() {
    return high;
  }

  public void setHigh(Double high) {
    this.high = high;
  }

  public Double getLow() {
    return low;
  }

  public void setLow(Double low) {
    this.low = low;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Double getVolume() {
    return volume;
  }

  public void setVolume(Double volume) {
    this.volume = volume;
  }

  public String getLastestTradingDay() {
    return lastestTradingDay;
  }

  public void setLastestTradingDay(String lastestTradingDay) {
    this.lastestTradingDay = lastestTradingDay;
  }

  public Double getPreviousClose() {
    return previousClose;
  }

  public void setPreviousClose(Double previousClose) {
    this.previousClose = previousClose;
  }

  public Double getChange() {
    return change;
  }

  public void setChange(Double change) {
    this.change = change;
  }

  public Double getChangePercent() {
    return changePercent;
  }

  public void setChangePercent(Double changePercent) {
    this.changePercent = changePercent;
  }
}
