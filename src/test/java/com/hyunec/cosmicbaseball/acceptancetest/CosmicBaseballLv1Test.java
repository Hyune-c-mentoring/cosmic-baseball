package com.hyunec.cosmicbaseball.acceptancetest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@Disabled
class CosmicBaseballLv1Test {
    @DisplayName("타격 결과는 모두 같은 확률을 가집니다.")
    @Test
    void t1() {
        throw new RuntimeException("Not yet implemented");
    }

    @DisplayName("타격 결과는 strike, ball, hit, double_ball, double_strike 입니다.")
    @Test
    void t2() {
        throw new RuntimeException("Not yet implemented");
    }
}
