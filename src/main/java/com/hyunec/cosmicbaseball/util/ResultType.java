package com.hyunec.cosmicbaseball.util;

public enum ResultType {

  BALL("BALL", "볼 입니다."),
  STRIKE("STRIKE", "스트라이크 입니다."),
  FOUR_BALL("FOUR BALL", "볼넷! 출루하였습니다. 게임이 종료됩니다."),
  HIT("HIT", "안타! 출루하였습니다. 게임이 종료됩니다."),
  DOUBLE_BALL("BALL", "더블 볼로 추가 2점입니다.!"),
  DOUBLE_STRIKE("STRIKE", "더블 스트라이크로 추가 2점입니다.!"),
  THREE_STRIKE("STRIKE", "스트라이크 3개로 아웃되었습니다"),
  OUT("OUT", "아웃되었습니다."),
  ADVANCE("ADVANCE", "진루하였습니다."),
  UNKNOWN("", "타격 결과는 %s 입니다.");

  private final String type;
  private final String message;

  ResultType(String type, String message) {
    this.type = type;
    this.message = message;
  }

  public String getType() {
    return type;
  }

  public String getMessage() {
    return message;
  }

  public String getMessage(String result) {
    if (this == UNKNOWN) {
      return String.format(message, result);
    }
    return message;
  }


}
