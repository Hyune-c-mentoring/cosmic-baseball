package com.hyunec.cosmicbaseballinit.acceptancetest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CosmicBaseballLv1Test {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("타격 결과는 모두 같은 확률을 가집니다.") //테스트의 목적을 설명하는 어노테이션
    @Test
    void t1() throws Exception {
        mockMvc.perform(get("/api/playGame"))
                .andExpect(status().isOk());
    }

    @DisplayName("타격 결과는 strike, ball, hit, double_ball, double_strike 입니다.")
    @Test
    void t2() {
        throw new RuntimeException("Not yet implemented");
    }

    // 아직 구현이 안돼서 RuntimeException을 날림
}
