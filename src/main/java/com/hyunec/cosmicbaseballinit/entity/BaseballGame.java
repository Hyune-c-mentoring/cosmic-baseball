package com.hyunec.cosmicbaseballinit.entity;

import com.hyunec.cosmicbaseballinit.handler.StrikeHandler;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class BaseballGame {

    private int strikes;
    private int balls;
    private Random random;

    private final StrikeHandler strikeHandler;


    public BaseballGame(StrikeHandler strikeHandler) {
        this.strikeHandler = strikeHandler;
        this.strikes    = 0;
        this.balls      = 0;
        this.random     = new Random();
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
        } //else if (result.equals("더블 스트라이크")) {
//            processDoubleStrike();
//        } else if (result.equals("더블 볼")) {
//            processDoubleBall();
//        }
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

    public String handleResult() {
        if (isThreeStrikes()) {
            return strikeHandler.handleResult("s");
        } else if (isFourBalls()) {
            return strikeHandler.handleResult("b");
        } else {
            return strikeHandler.handleResult("a");
        }
    }
    private void processStrike() {
        strikes++;
    }

    private void processBall() {
        balls++;
    }

//    private void processDoubleStrike() { strikes += 2;}
//
//    private void processDoubleBall() { balls += 2;}


}
