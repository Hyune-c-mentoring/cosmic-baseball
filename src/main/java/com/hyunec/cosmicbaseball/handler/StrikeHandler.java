package com.hyunec.cosmicbaseball.handler;

import org.springframework.stereotype.Component;

@Component
public class StrikeHandler implements GameResultHandler{

    @Override
    public String handleResult(String result) {
        if (result.equals("s")) {
            return "스트라이크 3개로 아웃되었습니다.";
        } else if (result.equals("b")) {
            return "볼넷! 출루하였습니다. 게임이 종료됩니다.";
        } else {
            return "안타! 출루하였습니다. 게임이 종료됩니다.";
        }
    }

    @Override
    public String handleOnceResult(String result) {
        return "타격 결과는 " + result + " 입니다.";
    }
}
