package br.pucpr.table;

public enum Theme {
  LIGHT("-"),
  NORMAL("="),
  DARK("#");

  private final String borderChar;

  Theme(String borderChar) {
    this.borderChar = borderChar;
  }

  public String getBorderChar() {
    return borderChar;
  }
}
