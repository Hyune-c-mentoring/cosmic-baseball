package com.hyunec.cosmicbaseball.entity;

import com.hyunec.cosmicbaseball.handler.ResultHandler;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class BaseballGame {
    private int strikeCount;
    private int ballCount;
    private String lastResult;
    private final Random random;

    private final ResultHandler resultHandler;

    private BaseballGame(ResultHandler resultHandler) {
        this.strikeCount    = 0;
        this.ballCount      = 0;
        this.lastResult     = "";
        this.random         = new Random();
        this.resultHandler  = resultHandler;
    }

    //스윙했을 때 돌아올 수 있는 값들 담아놓음.
    public String swing() {
        List<String> possibleResults = Arrays.asList("스트라이크", "안타", "볼");
        int index = random.nextInt(possibleResults.size());
        return possibleResults.get(index);
    }

    public String processResult(String result) {

        if (result.equals("스트라이크")) {
            if (lastResult.equals("스트라이크")) {
                result = "더블스트라이크";
                processDoubleStrike();
                return result;
            }else {
                processStrike();
            }
        } else if (result.equals("볼")) {
            if (lastResult.equals("볼")) {
                result = "더블볼";
                processDoubleBall();
                return result;
            }else {
                processBall();
            }
        }
        lastResult = result;
        return result;
    }


    //게임이 끝났는지 확인
    public boolean isGameEnd() {
        //스트라이크가 3개이거나, 볼이 4개인지 확인
        return isThreeStrikes() || isFourBalls();
    }

    public boolean isThreeStrikes() {
        return strikeCount >= 3;
    }

    public boolean isFourBalls() {
        return ballCount >= 4;
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
    public void incrementStrikeCount() {
        strikeCount++;
    }

    public void incrementStrikeCount(int count) {
        strikeCount += count;
    }

    public void incrementBallCount() {
        ballCount ++;
    }
    public void incrementBallCount(int count) {
        ballCount += count;
    }

    private void processStrike() {
        incrementStrikeCount();
    }

    private void processBall() {
        incrementBallCount();
    }


    private void processDoubleStrike() {
        incrementStrikeCount(2);
    }

    private void processDoubleBall() {
        incrementBallCount(2);
    }

}
