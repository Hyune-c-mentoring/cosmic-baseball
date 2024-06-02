package com.hyunec.cosmicbaseball.handler;

import com.hyunec.cosmicbaseball.util.ResultType;
import org.springframework.stereotype.Component;

@Component
public class ResultHandler implements GameResultHandler {

  @Override
  public ResultType handleResult(String result) {
    switch (result) {
      case "s":
        return ResultType.THREE_STRIKE;
      case "b":
        return ResultType.FOUR_BALL;
    }
    return ResultType.HIT;
  }

  @Override
  public ResultType handleOnceResult(String result, boolean isGameEnd) {
    switch (result) {
      case "DOUBLEBALL":
        return ResultType.DOUBLE_BALL;
      case "DOUBLESTRIKE":
        return ResultType.DOUBLE_STRIKE;
    }
    return ResultType.UNKNOWN;
  }
}