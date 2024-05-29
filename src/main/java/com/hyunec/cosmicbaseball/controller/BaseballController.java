package com.hyunec.cosmicbaseball.controller;

import com.hyunec.cosmicbaseball.entity.BaseballGame;
import com.hyunec.cosmicbaseball.handler.ResultHandler;
import com.hyunec.cosmicbaseball.util.BattingResult;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseballController {

  private final Logger logger = LoggerFactory.getLogger(BaseballController.class);

  private final BaseballGame baseballGame;
  private final ResultHandler resultHandler;

  public BaseballController(BaseballGame baseballGame,
      ResultHandler resultHandler) {
    this.baseballGame = baseballGame;
    this.resultHandler = resultHandler;
  }

  @GetMapping("/api/play-game")
  public String playGame() {

    List<String> results = new ArrayList<>();

    while (!baseballGame.isGameEnd()) {

      BattingResult BR = baseballGame.swing();
      String processedResult = baseballGame.processResult(BR);
      results.add(resultHandler.handleOnceResult(processedResult, baseballGame.isGameEnd()));
      logger.debug("results: {}", results);
    }

    return baseballGame.handleResult();

  }
}