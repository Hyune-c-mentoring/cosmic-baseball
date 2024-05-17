package com.hyunec.cosmicbaseballinit.controller;

import com.hyunec.cosmicbaseballinit.entity.BaseballGame;
import com.hyunec.cosmicbaseballinit.handler.StrikeHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class BaseballControllerTest {

    @Autowired
    private StrikeHandler strikeHandler;

    @Test
    void testSwing() {
        //반환되는 결과가 null이 아닌 게임 결과인지 확인
        BaseballGame baseballGame = new BaseballGame(strikeHandler);
        String result = baseballGame.swing();
        assertNotNull(result);
        assertTrue(result.equals("스트라이크") || result.equals("안타") ||result.equals("볼")||result.equals("더블 스트라이크")||result.equals("더블 볼"));
    }

    @Test
    void testIsGameEnd() {
        //게임이 종료되는지 확인
        BaseballGame baseballGame = new BaseballGame(strikeHandler);
        assertFalse(baseballGame.isGameEnd());
        baseballGame.processResult("스트라이크");
        baseballGame.processResult("스트라이크");
        baseballGame.processResult("스트라이크");
        assertTrue(baseballGame.isGameEnd());
    }
}
