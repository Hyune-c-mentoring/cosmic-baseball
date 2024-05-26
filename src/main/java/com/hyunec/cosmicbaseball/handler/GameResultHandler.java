package com.hyunec.cosmicbaseball.handler;

import org.springframework.stereotype.Component;

public interface GameResultHandler {
    String handleResult(String result);

    String handleOnceResult(String result, boolean isGameEnd);
}