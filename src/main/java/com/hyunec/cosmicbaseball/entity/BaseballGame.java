package com.hyunec.cosmicbaseball.entity;

import com.hyunec.cosmicbaseball.handler.ResultHandler;
import com.hyunec.cosmicbaseball.handler.ResultHandler;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class BaseballGame {
    private int strikeCount;
    private int ballCount;
    private final Random random;

    private final ResultHandler resultHandler;


    private BaseballGame(int strikeCount, int ballCount, Random random, ResultHandler resultHandler) {
        this.strikeCount = strikeCount;
        this.ballCount = ballCount;
        this.random = random;
        this.resultHandler = resultHandler;
    }

    //스윙했을 때 돌아올 수 있는 값들 담아놓음.
    public String swing() {
        List<String> possibleResults = Arrays.asList("스트라이크", "볼", "안타");
        int index = random.nextInt(possibleResults.size());
        return possibleResults.get(index);
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
        return strikeCount == 3;
    }

    public boolean isFourBalls() {
        return ballCount == 4;
    }

    public String handleResult() {
        if (isThreeStrikes()) {
            return resultHandler.handleResult("s");
        } else if (isFourBalls()) {
            return resultHandler.handleResult("b");
        } else {
            return resultHandler.handleResult("a");
        }
    }
    private void processStrike() {
        strikeCount++;
    }

    private void processBall() {
        ballCount++;
    }
}
