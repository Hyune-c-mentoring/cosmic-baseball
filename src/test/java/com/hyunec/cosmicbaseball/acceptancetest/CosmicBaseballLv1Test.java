package com.hyunec.cosmicbaseball.acceptancetest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hyunec.cosmicbaseball.entity.BaseballGame;
import com.hyunec.cosmicbaseball.handler.ResultHandler;
import com.hyunec.cosmicbaseball.util.BattingResult;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CosmicBaseballLv1Test {

  @Autowired
  private BaseballGame baseballGame;

  @DisplayName("타격 결과는 모두 같은 확률을 가집니다.")
  @Test
  void testSwing() {

    final int testCount = 100000;

    Map<BattingResult, Integer> resultCounts = new HashMap<>();
    for (BattingResult result : BattingResult.values()) {
      resultCounts.put(result, 0);
    }

    // 테스트할 BaseballGame 인스턴스 생성
    BaseballGame baseballGame = new BaseballGame(new ResultHandler(), null);

    // 랜덤한 결과를 testCount만큼 생성 + 맵에 결과 누적
    for (int i = 0; i < testCount; i++) {
      BattingResult result = baseballGame.swing();
      resultCounts.put(result, resultCounts.get(result) + 1);
    }

    // 균일성 확인 (결과)
    int expectedCountPerResult = testCount / BattingResult.values().length;
    for (int count : resultCounts.values()) {
      assertTrue(Math.abs(count - expectedCountPerResult) < 0.05 * testCount,
          "균일한 확률이 아님");
    }
  }

  @DisplayName("타격 결과는 strike, ball, hit, double_ball, double_strike 입니다.")
  @Test
  void testPlayGame() {

    // Given
    Set<String> validResults = Set.of("STRIKE", "BALL", "DOUBLESTRIKE", "DOUBLEBALL", "HIT");

    // When
    for (int i = 0; i < 100; i++) {
      BattingResult BR = baseballGame.swing();
      String result = baseballGame.processResult(BR);

      // Then
      assertTrue(validResults.contains(result), () -> "Invalid batting result: " + result);
    }
  }
}