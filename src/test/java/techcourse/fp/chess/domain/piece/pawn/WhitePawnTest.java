package techcourse.fp.chess.domain.piece.pawn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static techcourse.fp.chess.domain.PieceFixtures.BLACK_PAWN;
import static techcourse.fp.chess.domain.PieceFixtures.EMPTY;
import static techcourse.fp.chess.domain.PieceFixtures.WHITE_PAWN;
import static techcourse.fp.chess.domain.PositionFixtures.A1;
import static techcourse.fp.chess.domain.PositionFixtures.A2;
import static techcourse.fp.chess.domain.PositionFixtures.A3;
import static techcourse.fp.chess.domain.PositionFixtures.A4;
import static techcourse.fp.chess.domain.PositionFixtures.A5;
import static techcourse.fp.chess.domain.PositionFixtures.B2;
import static techcourse.fp.chess.domain.PositionFixtures.C3;
import static techcourse.fp.chess.domain.PositionFixtures.H2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import techcourse.fp.chess.domain.piece.EmptyPiece;

public class WhitePawnTest {

    private final WhitePawn whitePawn = WhitePawn.create();

    @DisplayName("file이 다르면 예외가 발생한다.")
    @Test
    void fail_test() {
        assertThatThrownBy(() -> whitePawn.findPath(A2, H2, WhitePawn.create()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("폰이 해당 지점으로 이동할 수 없습니다.");
    }

    @DisplayName("같은 좌표는 예외가 발생한다.")
    @Test
    void fail_test2() {
        assertThatThrownBy(() -> whitePawn.findPath(A2, A2, WhitePawn.create()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("폰이 해당 지점으로 이동할 수 없습니다.");
    }

    @DisplayName("rank의 차이가 3 이상이면 예외가 발생한다.")
    @Test
    void fail_test3() {
        assertThatThrownBy(() -> whitePawn.findPath(A2, A5, WhitePawn.create()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("폰이 해당 지점으로 이동할 수 없습니다.");
    }

    @DisplayName("백 폰은 아래로 이동할 수 없다.")
    @Test
    void fail_test4() {
        assertThatThrownBy(() -> whitePawn.findPath(A2, A1, EMPTY))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("폰이 해당 지점으로 이동할 수 없습니다.");
    }

    @DisplayName("한 칸 전진이 ")
    @Nested
    class OneStepMove {

        @DisplayName("성공한다.")
        @Test
        void fail_test4() {
            assertThat(whitePawn.findPath(A2, A3, EmptyPiece.create())).isEmpty();
        }

        @DisplayName("실패한다 - 도착지에 아군 기물 존재")
        @Test
        void fail_test5() {
            assertThatThrownBy(() -> whitePawn.findPath(A2, A3, WhitePawn.create()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("폰이 해당 지점으로 이동할 수 없습니다.");
        }

        @DisplayName("실패한다 - 도착지에 적 기물 존재")
        @Test
        void fail_test6() {
            assertThatThrownBy(() -> whitePawn.findPath(A2, A3, BlackPawn.create()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("폰이 해당 지점으로 이동할 수 없습니다.");
        }

    }

    @DisplayName("두 칸 전진이 ")
    @Nested
    class TwoStepMove {

        @DisplayName("성공한다.")
        @Test
        void fail_test7() {
            assertThat(whitePawn.findPath(A2, A4, EmptyPiece.create())).containsExactly(A3);
        }

        @DisplayName("실패 한다. - 도착지에 아군 기물 존재")
        @Test
        void fail_test8() {
            assertThatThrownBy(() -> whitePawn.findPath(A2, A4, WhitePawn.create()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("폰이 해당 지점으로 이동할 수 없습니다.");
        }

        @DisplayName("실패 한다. - 도착지에 적 기물 존재")
        @Test
        void fail_test9() {
            assertThatThrownBy(() -> whitePawn.findPath(A2, A4, BlackPawn.create()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("폰이 해당 지점으로 이동할 수 없습니다.");
        }

        @DisplayName("실패 한다. - 폰이 최초 이동이 아닌 경우")
        @Test
        void fail_by_start_position() {
            assertThatThrownBy(() -> whitePawn.findPath(A3, A5, EmptyPiece.create()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("폰이 해당 지점으로 이동할 수 없습니다.");
        }
    }

    @DisplayName("백 폰의 공격이")
    @Nested
    class Attack {

        @DisplayName("성공한다. - 좌측 상단을 공격하는 경우")
        @Test
        void success_left() {
            assertThat(whitePawn.findPath(B2, A3, BLACK_PAWN)).isEmpty();
        }

        @DisplayName("성공한다. - 우측 상단을 공격하는 경우")
        @Test
        void success_right() {
            assertThat(whitePawn.findPath(B2, C3, BLACK_PAWN)).isEmpty();
        }

        @DisplayName("실패한다. - 공격 지점에 아군이 있는 경우")
        @Test
        void fail_by_ally() {
            assertThatThrownBy(() -> whitePawn.findPath(B2, A3, WHITE_PAWN))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("폰이 해당 지점으로 이동할 수 없습니다.");
        }

        @DisplayName("실패한다. - 공격 지점이 비어 있는 경우")
        @Test
        void fail_by_empty() {
            assertThatThrownBy(() -> whitePawn.findPath(B2, A3, EMPTY))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("폰이 해당 지점으로 이동할 수 없습니다.");
        }
    }
}