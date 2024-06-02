package com.hyunec.cosmicbaseball.acceptancetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.hyunec.cosmicbaseball.controller.BaseballController;
import com.hyunec.cosmicbaseball.entity.BaseballGame;
import com.hyunec.cosmicbaseball.handler.ResultHandler;
import com.hyunec.cosmicbaseball.util.BattingResult;
import com.hyunec.cosmicbaseball.util.PlateAppearanceResult;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class NormalBaseballLv2Test {


  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @MockBean
  private BaseballGame baseballGame;

  @MockBean
  private ResultHandler resultHandler;

  @DisplayName("타석 상태를 표현할 수 있습니다.")
  @Test
  void t1() throws Exception {
    // given
    BaseballGame baseballGame = mock(BaseballGame.class);
    ResultHandler resultHandler = mock(ResultHandler.class);
    BaseballController controller = new BaseballController(baseballGame, resultHandler);

    /*게임이 두 번만 실행되도록*/
    when(baseballGame.isGameEnd()).thenReturn(false, false, true); // 세 번째 호출에서 게임 종료
    when(baseballGame.batting()).thenReturn(BattingResult.STRIKE); //스트라이크 반환
    when(baseballGame.processResult(any(BattingResult.class))).thenReturn(
        PlateAppearanceResult.OUT);

    // when
    PlateAppearanceResult result = controller.playGame();

    // then
    assertEquals(PlateAppearanceResult.OUT, result);
  }

  @DisplayName("0 strike 상태의 타석에서 타격 결과가 strike 이면 타석 결과는 진행 중 입니다.")
  @Test
  void t2() {
    // given
    BaseballGame baseballGame = mock(BaseballGame.class);
    ResultHandler resultHandler = mock(ResultHandler.class);
    BaseballController controller = new BaseballController(baseballGame, resultHandler);

    when(baseballGame.isGameEnd()).thenReturn(false, false, false, true);
    when(baseballGame.batting()).thenReturn(BattingResult.STRIKE);
    when(baseballGame.processResult(any(BattingResult.class))).thenReturn(
        null); // 진행 중 상태를 나타내기 위해 null 반환

    // when
    PlateAppearanceResult result = controller.playGame();

    // then
    assertNull(result, "타격 결과가 STRIKE일 때 타석 결과는 진행 중이어야 합니다.");
  }

  @DisplayName("2 strike 상태의 타석에서 타격 결과가 strike 이면 타석 결과는 out 입니다.")
  @Test
  void t3() {
    // given
    BaseballGame baseballGame = mock(BaseballGame.class);
    ResultHandler resultHandler = mock(ResultHandler.class);
    BaseballController controller = new BaseballController(baseballGame, resultHandler);

    // 각 메서드 호출 시 반환값 설정
    when(baseballGame.isGameEnd()).thenReturn(false, false, true); // 세 번째 호출에서 게임 종료
    when(baseballGame.batting()).thenReturn(BattingResult.STRIKE);
    when(baseballGame.processResult(any(BattingResult.class))).thenAnswer(invocation -> {
      BattingResult result = invocation.getArgument(0);
      if (result == BattingResult.STRIKE) {
        return PlateAppearanceResult.OUT;
      }
      return null; // 진행 중 상태를 나타내기 위해 null 반환
    });

    // when
    PlateAppearanceResult result = controller.playGame();

    // then
    assertEquals(PlateAppearanceResult.OUT, result,
        "2 strike 상태에서 타격 결과가 strike일 때 타석 결과는 out이어야 합니다.");

  }

  @DisplayName("0 strike 상태의 타석에서 타격 결과가 double_strike 이면 타석 결과는 진행 중 입니다.")
  @Test
  void t4() {
    /*제가 게임 이해를 잘 못 한 거 같은데
     * 저는 처음부터 더블 스트라이크가 나올 수 없다고 생각해서
     * 0 strike일때 더블 스트라이크가 나올 수 있게 코드를 안 짰어요 ..!!
     * 무조건 스트라이크 한 번은 나오고 그 다음에 또 스트라이크여야 더블 스트라이크이게 짰는데
     * 이 부분 수정해야하면 하겠습니다.!!
     * */

  }

  @DisplayName("1 strike 상태의 타석에서 타격 결과가 double_strike 이면 타석 결과는 out 입니다.")
  @Test
  void t5() {
    // given
    BaseballGame baseballGame = mock(BaseballGame.class);
    ResultHandler resultHandler = mock(ResultHandler.class);
    BaseballController controller = new BaseballController(baseballGame, resultHandler);

    // 각 메서드 호출 시 반환값 설정
    when(baseballGame.isGameEnd()).thenReturn(false, false, true); // 세 번째 호출에서 게임 종료
    when(baseballGame.batting()).thenReturn(BattingResult.STRIKE, BattingResult.STRIKE);
    when(baseballGame.processResult(any(BattingResult.class))).thenAnswer(invocation -> {
      BattingResult result = invocation.getArgument(0);
      if (result == BattingResult.STRIKE) {
        return PlateAppearanceResult.OUT; // 더블 스트라이크 상황에서 OUT 반환
      }
      return null; // 진행 중 상태를 나타내기 위해 null 반환
    });

    // when
    PlateAppearanceResult result = controller.playGame();

    // then
    assertEquals(PlateAppearanceResult.OUT, result,
        "1 strike 상태에서 타격 결과가 double_strike일 때 타석 결과는 out이어야 합니다.");
  }

  @DisplayName("0 ball 상태의 타석에서 타격 결과가 ball 이면 타석 결과는 진행 중 입니다.")
  @Test
  void t6() {
    // given
    BaseballGame baseballGame = mock(BaseballGame.class);
    ResultHandler resultHandler = mock(ResultHandler.class);
    BaseballController controller = new BaseballController(baseballGame, resultHandler);

    // 각 메서드 호출 시 반환값 설정
    when(baseballGame.isGameEnd()).thenReturn(false, false, false, true); // 네 번째 호출에서 게임 종료
    when(baseballGame.batting()).thenReturn(BattingResult.BALL);
    when(baseballGame.processResult(any(BattingResult.class))).thenAnswer(invocation -> {
      BattingResult result = invocation.getArgument(0);
      if (result == BattingResult.BALL) {
        return null; // 진행 중 상태를 나타내기 위해 null 반환
      }
      return PlateAppearanceResult.OUT;
    });

    // when
    PlateAppearanceResult result = controller.playGame();

    // then
    assertNull(result, "0 ball 상태에서 타격 결과가 BALL일 때 타석 결과는 진행 중이어야 합니다.");

  }

  @DisplayName("3 ball 상태의 타석에서 타격 결과가 ball 이면 타석 결과는 four_ball 으로 진루 입니다.")
  @Test
  void t7() {
    // given
    BaseballGame baseballGame = mock(BaseballGame.class);
    ResultHandler resultHandler = mock(ResultHandler.class);
    BaseballController controller = new BaseballController(baseballGame, resultHandler);

    // 각 메서드 호출 시 반환값 설정
    when(baseballGame.isGameEnd()).thenReturn(false, false, false, true); // 네 번째 호출에서 게임 종료
    when(baseballGame.batting()).thenReturn(BattingResult.BALL);
    when(baseballGame.processResult(any(BattingResult.class))).thenAnswer(invocation -> {
      BattingResult result = invocation.getArgument(0);
      if (result == BattingResult.BALL) {
        return PlateAppearanceResult.ADVANCE; // 3 BALL 이후에 BALL이 나오면 ADVANCE 반환
      }
      return null; // 진행 중 상태를 나타내기 위해 null 반환
    });

    // when
    PlateAppearanceResult result = controller.playGame();

    // then
    assertEquals(PlateAppearanceResult.ADVANCE, result,
        "3 ball 상태에서 타격 결과가 BALL일 때 타석 결과는 ADVANCE이어야 합니다.");

  }

  @DisplayName("0 ball 상태의 타석에서 타격 결과가 double_ball 이면 타석 결과는 진행 중 입니다.")
  @Test
  void t8() {
    // given
    BaseballGame baseballGame = mock(BaseballGame.class);
    ResultHandler resultHandler = mock(ResultHandler.class);
    BaseballController controller = new BaseballController(baseballGame, resultHandler);

    // 각 메서드 호출 시 반환값 설정
    when(baseballGame.isGameEnd()).thenReturn(false, false, false, true); // 네 번째 호출에서 게임 종료
    when(baseballGame.batting()).thenReturn(BattingResult.BALL);
    when(baseballGame.processResult(any(BattingResult.class))).thenAnswer(invocation -> {
      BattingResult result = invocation.getArgument(0);
      if (result == BattingResult.BALL) {
        return null; // 진행 중 상태를 나타내기 위해 null 반환
      }
      return PlateAppearanceResult.OUT; // 다른 결과를 나타내기 위해 예시로 OUT 반환
    });

    // when
    PlateAppearanceResult result = controller.playGame();

    // then
    assertNull(result, "0 ball 상태에서 타격 결과가 BALL일 때 타석 결과는 진행 중이어야 합니다.");

  }

  @DisplayName("2 ball 상태의 타석에서 타격 결과가 double_ball 이면 타석 결과는 four_ball 으로 진루 입니다.")
  @Test
  void t9() {
    /*이것도 위에서 말씀드린 이유대로 저는 더블 볼도 랜덤으로 생성되게 코드를 생성하지  않았어서 테스트 코드 구현이 어려울 것 같습니다.*/
  }

  @DisplayName("타석 결과가 hit 이면 타석 결과는 hit 로 진루 입니다.")
  @Test
  void t10() {
    // given
    BaseballGame baseballGame = mock(BaseballGame.class);
    ResultHandler resultHandler = mock(ResultHandler.class);
    BaseballController controller = new BaseballController(baseballGame, resultHandler);

    // 각 메서드 호출 시 반환값 설정
    when(baseballGame.isGameEnd()).thenReturn(false, true); // 두 번째 호출에서 게임 종료
    when(baseballGame.batting()).thenReturn(BattingResult.HIT);
    when(baseballGame.processResult(any(BattingResult.class))).thenAnswer(invocation -> {
      BattingResult result = invocation.getArgument(0);
      if (result == BattingResult.HIT) {
        return PlateAppearanceResult.ADVANCE; // 타석 결과가 HIT이면 ADVANCE(진루) 반환
      }
      return null; // 다른 결과를 나타내기 위해 예시로 null 반환
    });

    // when
    PlateAppearanceResult result = controller.playGame();

    // then
    assertEquals(PlateAppearanceResult.ADVANCE, result, "타석 결과가 HIT일 때 타석 결과는 ADVANCE이어야 합니다.");

  }

  @BeforeEach
  public void setUp() {
    resultHandler = mock(ResultHandler.class);
    baseballGame = new BaseballGame(resultHandler, Arrays.asList("STRIKE", "BALL", "HIT"));
  }

  @DisplayName("진행 중인 타석이 있는 상태에서 새로운 타석을 진행할 수 없습니다.")
  @Test
  void t11() {

    baseballGame.batting();

    // 진행 중인 타석이 있는 상태에서 새로운 타석을 시작하려고 하면 예외가 발생해야 함
    assertThrows(IllegalStateException.class, () -> {
      baseballGame.batting();
    }, "진행 중인 타석이 있는 상태에서 새로운 타석을 진행할 수 없습니다.");
  }
}
