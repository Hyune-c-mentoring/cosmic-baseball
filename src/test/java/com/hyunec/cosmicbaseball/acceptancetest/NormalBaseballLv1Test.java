package com.hyunec.cosmicbaseball.acceptancetest;

import com.hyunec.cosmicbaseball.controller.BaseballController;
import com.hyunec.cosmicbaseball.entity.BaseballGame;
import com.hyunec.cosmicbaseball.handler.StrikeHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class NormalBaseballLv1Test {

    @Autowired
    private StrikeHandler strikeHandler;
    @Autowired
    private BaseballGame baseballGame;

    @Test
    @DisplayName("타격 결과는 모두 같은 확률을 가집니다.")
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

                assertTrue(Math.abs(resultCounts.get("스트라이크") - expectedCount) <= tolerance,
                        "스트라이크 횟수가 허용 오차 범위 내에 있지 않습니다.");
                assertTrue(Math.abs(resultCounts.get("볼") - expectedCount) <= tolerance,
                        "볼 횟수가 허용 오차 범위 내에 있지 않습니다.");
                assertTrue(Math.abs(resultCounts.get("안타") - expectedCount) <= tolerance,
                        "안타 횟수가 허용 오차 범위 내에 있지 않습니다.");
    }

    @Test
    @DisplayName("타격 결과는 strike, ball, hit 입니다.")
    void testPlayGame() {

        // Given
        Map<String, Boolean> resultOccurred = new HashMap<>();
        resultOccurred.put("스트라이크", false);
        resultOccurred.put("볼", false);
        resultOccurred.put("안타", false);

        // When
        for (int i = 0; i < 100; i++) {
            String result = baseballGame.swing();
            if (resultOccurred.containsKey(result)) {
                resultOccurred.put(result, true);
            } else {
                throw new AssertionError("스트라이크, 볼, 안타가 아닌 결과 " + result);
            }
        }

        // Then
        assertTrue(resultOccurred.get("스트라이크"), "스트라이크 결과가 발생하지 않음.");
        assertTrue(resultOccurred.get("볼"), "볼 결과가 발생하지 않음.");
        assertTrue(resultOccurred.get("안타"), "안타 결과가 발생하지 않음.");

    }
}
