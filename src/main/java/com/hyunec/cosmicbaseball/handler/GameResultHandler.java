package com.hyunec.cosmicbaseball.handler;

import com.hyunec.cosmicbaseball.util.ResultType;

public interface GameResultHandler {

  ResultType handleResult(String result);

  ResultType handleOnceResult(String result, boolean isGameEnd);
}