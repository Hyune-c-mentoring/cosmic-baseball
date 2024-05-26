package com.hyunec.cosmicbaseball.handler;

import org.springframework.stereotype.Component;

@Component
public class ResultHandler implements GameResultHandler{

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
    public String handleOnceResult(String result, boolean isGameEnd) {
        if(result.equals("더블볼")) {
            if(isGameEnd) {
                return "쓰리볼입니다. ! ";
            }
            return "타격 결과는 볼 입니다. 더블 볼로 추가 2점입니다. !";
        } else if(result.equals("더블스트라이크")) {
            return "타격 결과는 스트라이크 입니다. 더블 스트라이크로 추가 2점입니다. !";
        }
        return "타격 결과는 " + result + " 입니다.";
    }
}
