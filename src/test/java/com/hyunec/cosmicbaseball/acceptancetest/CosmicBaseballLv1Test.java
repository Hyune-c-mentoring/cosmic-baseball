package com.hyunec.cosmicbaseball.acceptancetest;

import com.hyunec.cosmicbaseball.entity.BaseballGame;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class CosmicBaseballLv1Test {
    @Autowired
    private BaseballGame baseballGame;

    @DisplayName("타격 결과는 모두 같은 확률을 가집니다.")
    @Test
    void testSwing() {

        // Given
        int numberOfSwings                = 10000; // 스윙 횟수
        Map<String, Integer> resultCounts = new HashMap<>();
        resultCounts.put("스트라이크", 0);
        resultCounts.put("볼", 0);
        resultCounts.put("안타", 0);

        // When : 스윙 시뮬레이션
        for (int i = 0; i < numberOfSwings; i++) {
            String result = baseballGame.swing();
            resultCounts.put(result, resultCounts.get(result) + 1);
        }

        // Then
        int expectedCount = numberOfSwings / 3;
        int tolerance     = (int) (numberOfSwings * 0.05); // 허용 오차: 5%

        Assertions.assertThat(resultCounts.get("스트라이크"))
                .isCloseTo(expectedCount, Assertions.withinPercentage(5));
        Assertions.assertThat(resultCounts.get("볼"))
                .isCloseTo(expectedCount, Assertions.withinPercentage(5));
        Assertions.assertThat(resultCounts.get("안타"))
                .isCloseTo(expectedCount, Assertions.withinPercentage(5));
    }

    @DisplayName("타격 결과는 strike, ball, hit, double_ball, double_strike 입니다.")
    @Test
    void testPlayGame() {

        // Given
        Map<String , Boolean> resultOccurred = new HashMap<>();
        resultOccurred.put("스트라이크", false);
        resultOccurred.put("볼", false);
        resultOccurred.put("안타", false);
        resultOccurred.put("더블스트라이크", false);
        resultOccurred.put("더블볼", false);
        
        // When
        String previousResult = "";
        for(int i = 0; i < 100; i++) {
            String result = baseballGame.swing();

            // Check for Double Strike or Double Ball
            if (previousResult.equals("스트라이크") && result.equals("스트라이크")) {
                result = "더블스트라이크";
            } else if (previousResult.equals("볼") && result.equals("볼")) {
                result = "더블볼";
            }

            resultOccurred.put(result, true);
            previousResult = result;
        }
        
        // Then
        Assertions.assertThat(resultOccurred.get("스트라이크")).isTrue();
        Assertions.assertThat(resultOccurred.get("볼")).isTrue();
        Assertions.assertThat(resultOccurred.get("안타")).isTrue();
        Assertions.assertThat(resultOccurred.get("더블스트라이크")).isTrue();
        Assertions.assertThat(resultOccurred.get("더블볼")).isTrue();
    }
}
