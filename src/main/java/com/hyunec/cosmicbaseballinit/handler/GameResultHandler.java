package com.hyunec.cosmicbaseballinit.handler;

import org.springframework.stereotype.Component;

@Component
public interface GameResultHandler {
    String handleResult(String result);
}
