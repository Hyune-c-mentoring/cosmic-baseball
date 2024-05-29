package com.hyunec.cosmicbaseball.util;

import java.util.Random;
import java.util.function.Function;

public enum BattingResult {
  STRIKE(lastResult -> lastResult.equals("STRIKE") ? "DOUBLESTRIKE" : "STRIKE"),
  BALL(lastResult -> lastResult.equals("BALL") ? "DOUBLEBALL" : "BALL"),
  HIT(lastResult -> "HIT");

  private static final Random RANDOM = new Random();
  private final Function<String, String> resultProcessor;

  BattingResult(Function<String, String> resultProcessor) {
    this.resultProcessor = resultProcessor;
  }

  public static BattingResult getRandomResult() {
    return values()[RANDOM.nextInt(values().length)];
  }

  public String processResult(String lastResult) {
    return resultProcessor.apply(lastResult);
  }
}