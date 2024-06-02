package com.hyunec.cosmicbaseball.controller;

import com.hyunec.cosmicbaseball.entity.BaseballGame;
import com.hyunec.cosmicbaseball.handler.ResultHandler;
import com.hyunec.cosmicbaseball.util.BattingResult;
import com.hyunec.cosmicbaseball.util.PlateAppearanceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseballController {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private final BaseballGame baseballGame;
  private final ResultHandler resultHandler;
  
  public BaseballController(BaseballGame baseballGame, ResultHandler resultHandler) {
    this.baseballGame = baseballGame;
    this.resultHandler = resultHandler;
  }

  @GetMapping("/api/play-game")
  public PlateAppearanceResult playGame() {

    PlateAppearanceResult plateAppearanceResult = null;
    while (!baseballGame.isGameEnd()) {

      BattingResult battingResult = baseballGame.batting();
      plateAppearanceResult = baseballGame.processResult(battingResult);

      if (plateAppearanceResult != null) {
        break;
      }
    }

    return plateAppearanceResult;
  }

}