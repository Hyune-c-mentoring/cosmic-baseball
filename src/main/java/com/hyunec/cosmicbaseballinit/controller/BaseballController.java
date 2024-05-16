package com.hyunec.cosmicbaseballinit.controller;

import com.hyunec.cosmicbaseballinit.entity.BaseballGame;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Scanner;

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
            return "타격 결과입니당 : " + result;
        }

        if (baseballGame.isThreeStrikes()) {
            return "스트라이크 3개로 아웃되었습니다.";
        } else if (baseballGame.isFourBalls()) {
            return "볼넷! 출루하였습니다. 게임이 종료됩니다.";
        } else {
            return "안타! 출루하였습니다. 게임이 종료됩니다.";
        }
    }
}