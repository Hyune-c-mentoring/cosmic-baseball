package com.hyunec.cosmicbaseballinit.entity;

import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class BaseballGame {

    //객체 선언
    private int strikes;
    private int balls;
    private Random random;

    public BaseballGame() {
        this.strikes = 0;
        this.balls = 0;
        this.random = new Random();
    }

    //스윙했을 때 돌아올 수 있는 값들 담아놓음.
    public String swing() {
        String[] possibleResults = {"스트라이크", "볼", "안타"};
        int index = random.nextInt(possibleResults.length);
        return possibleResults[index];
    }

    public void processResult(String result) {
        if (result.equals("스트라이크")) {
            processStrike();
        } else if (result.equals("볼")) {
            processBall();
        }
    }

    //게임이 끝났는지 확인
    public boolean isGameEnd() {
        //스트라이크가 3개이거나, 볼이 4개인지 확인
        return isThreeStrikes() || isFourBalls();
    }

    public boolean isThreeStrikes() {
        return strikes == 3;
    }

    public boolean isFourBalls() {
        return balls == 4;
    }

    private void processStrike() {
        strikes++;
    }

    private void processBall() {
        balls++;
    }

}
