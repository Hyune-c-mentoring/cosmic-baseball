package com.hyunec.cosmicbaseball.controller;

import com.hyunec.cosmicbaseball.entity.BaseballGame;
import com.hyunec.cosmicbaseball.handler.StrikeHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ResponseBody
@RestController
public class BaseballController {

    private final BaseballGame baseballGame;
    private final StrikeHandler strikeHandler;

    public BaseballController(BaseballGame baseballGame, StrikeHandler strikeHandler) {
        this.baseballGame = baseballGame;
        this.strikeHandler = strikeHandler;
    }

    @GetMapping("/api/playGame")
    public String playGame() {
        while (!baseballGame.isGameEnd()) {
            String result = baseballGame.swing();
            baseballGame.processResult(result);
            return strikeHandler.handleOnceResult(result);
        }

        return baseballGame.handleResult();

    }
}