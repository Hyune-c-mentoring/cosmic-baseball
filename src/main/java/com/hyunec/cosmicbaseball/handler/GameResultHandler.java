package com.hyunec.cosmicbaseball.handler;

import org.springframework.stereotype.Component;

@Component
public interface GameResultHandler {
    String handleResult(String result);
}
