package com.hyunec.cosmicbaseball.controller;

import com.hyunec.cosmicbaseball.entity.BaseballGame;
import com.hyunec.cosmicbaseball.handler.ResultHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;

@RestController
public class BaseballController {

    private final BaseballGame baseballGame;
    private final ResultHandler resultHandler;

    public BaseballController(BaseballGame baseballGame, ResultHandler resultHandler) {
        this.baseballGame = baseballGame;
        this.resultHandler = resultHandler;
    }

    @GetMapping("/api/playGame")
    public String playGame() {
        while (!baseballGame.isGameEnd()) {
            String result = baseballGame.swing();
            String processedResult = baseballGame.processResult(result);
            return resultHandler.handleOnceResult(processedResult, baseballGame.isGameEnd());
        }

        return baseballGame.handleResult();

    }
}
