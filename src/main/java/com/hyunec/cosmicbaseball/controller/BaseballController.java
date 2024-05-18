package com.hyunec.cosmicbaseball.controller;

import com.hyunec.cosmicbaseball.entity.BaseballGame;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ResponseBody
@RestController
public class BaseballController {

    private final BaseballGame baseballGame;

    public BaseballController(BaseballGame baseballGame) {
        this.baseballGame = baseballGame;
    }

    @GetMapping("/api/playGame")
    public String playGame() {
        while (!baseballGame.isGameEnd()) {
            String result = baseballGame.swing();
            baseballGame.processResult(result);
            return "타격 결과입니담 : " + result;
        }

        return baseballGame.handleResult();

    }
}