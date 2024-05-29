package com.hyunec.cosmicbaseball.entity;

import com.hyunec.cosmicbaseball.handler.ResultHandler;
import com.hyunec.cosmicbaseball.util.BattingResult;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class BaseballGame {

  private int strikeCount;
  private int ballCount;
  private String lastResult;
  private final Random random;
  private List<String> possibleResults;

  private final ResultHandler resultHandler;

  public BaseballGame(ResultHandler resultHandler, List<String> possibleResults
  ) {
    this.strikeCount = 0;
    this.ballCount = 0;
    this.lastResult = "";
    this.random = new Random();
    this.resultHandler = resultHandler;
    this.possibleResults = possibleResults;
  }

  public BattingResult swing() {
    //스윙하는 행위만 담기
    return BattingResult.getRandomResult();
  }

  //스윙 결과
  public String processResult(BattingResult BR) {
    String result = BR.processResult(lastResult);
    updateGameStats(result);
    lastResult = result;
    return result;
  }

  //게임 결과 update
  private void updateGameStats(String result) {
    switch (result) {
      case "STRIKE" -> strikeCount++;
      case "DOUBLESTRIKE" -> processDoubleStrike();
      case "BALL" -> ballCount++;
      case "DOUBLEBALL" -> processDoubleBall();
    }
  }

  //게임이 끝났는지 확인
  public boolean isGameEnd() {
    //스트라이크가 3개이거나, 볼이 4개인지 확인
    return isThreeStrikes() || isFourBalls();
  }

  public boolean isThreeStrikes() {
    return strikeCount >= 3;
  }

  public boolean isFourBalls() {
    return ballCount >= 4;
  }

  public String handleResult() {
    if (isThreeStrikes()) {
      return resultHandler.handleResult("s");
    } else if (isFourBalls()) {
      return resultHandler.handleResult("b");
    } else {
      return resultHandler.handleResult("a");
    }
  }

  public String getLastResult() {
    return lastResult;
  }

  public void incrementStrikeCount(int count) {
    strikeCount += count;
  }

  public void incrementBallCount(int count) {
    ballCount += count;
  }

  private void processDoubleStrike() {
    incrementStrikeCount(2);
  }

  private void processDoubleBall() {
    incrementBallCount(2);
  }

}