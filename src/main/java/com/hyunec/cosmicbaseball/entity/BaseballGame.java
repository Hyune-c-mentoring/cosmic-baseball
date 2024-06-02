package com.hyunec.cosmicbaseball.entity;

import com.hyunec.cosmicbaseball.handler.ResultHandler;
import com.hyunec.cosmicbaseball.util.BattingResult;
import com.hyunec.cosmicbaseball.util.PlateAppearanceResult;
import com.hyunec.cosmicbaseball.util.ResultType;
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
  private boolean atBatInProgress;

  public BaseballGame(ResultHandler resultHandler, List<String> possibleResults) {
    this.strikeCount = 0;
    this.ballCount = 0;
    this.lastResult = "";
    this.random = new Random();
    this.resultHandler = resultHandler;
    this.possibleResults = possibleResults;
    this.atBatInProgress = false;
  }

  public BattingResult batting() {
    if (atBatInProgress) {
      throw new IllegalStateException("진행 중인 타석이 있는 상태에서 새로운 타석을 진행할 수 없습니다.");
    }
    atBatInProgress = true;
    return BattingResult.getRandomResult();
  }

  //스윙 결과
  public PlateAppearanceResult processResult(BattingResult battingResult) {
    String result = battingResult.processResult(lastResult);
    updateGameStats(result);
    lastResult = result;
    PlateAppearanceResult plateAppearanceResult = determinePlateAppearanceResult();
    if (plateAppearanceResult != null) {
      atBatInProgress = false;
    }
    return plateAppearanceResult();
  }

  private PlateAppearanceResult plateAppearanceResult() {
    if (hasReachedStrikeLimit()) {
      return PlateAppearanceResult.OUT;
    } else if (hasReachedBallLimit() || lastResult.equals(BattingResult.HIT)) {
      return PlateAppearanceResult.ADVANCE;
    } else {
      return null;
    }
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

  private PlateAppearanceResult determinePlateAppearanceResult() {
    if (hasReachedStrikeLimit()) {
      return PlateAppearanceResult.OUT;
    } else if (hasReachedBallLimit() || lastResult.equals(BattingResult.HIT)) {
      return PlateAppearanceResult.ADVANCE;
    } else {
      return null;
    }
  }


  //게임이 끝났는지 확인
  public boolean isGameEnd() {
    //스트라이크가 3개이거나, 볼이 4개인지 확인
    return hasReachedStrikeLimit() || hasReachedBallLimit();
  }

  public boolean hasReachedStrikeLimit() {
    return strikeCount >= 3;
  }

  public boolean hasReachedBallLimit() {
    return ballCount >= 4;
  }

  public ResultType handleResult() {
    if (hasReachedStrikeLimit()) {
      return resultHandler.handleResult("s");
    } else if (hasReachedBallLimit()) {
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