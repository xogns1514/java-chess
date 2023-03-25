package chess.domain.piece.type;

import chess.domain.board.Column;
import chess.domain.board.Position;
import chess.domain.board.Rank;
import chess.domain.piece.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SuppressWarnings({"NonAsciiCharacters","SpellCheckingInspection"})
class KnightTest {

    private final Knight knight = new Knight(Color.BLACK);

    @ParameterizedTest
    @CsvSource(value = {"D, FIVE" , "D, THREE", "C, FOUR","E,FOUR"})
    void Knight가_갈수없는_방향으로_이동하려고_하면_예외(Column endColumn, Rank endRank) {
        Position start = Position.of(Column.D, Rank.FOUR);
        Position end = Position.of(endColumn, endRank);
        Color colorOfDestination = Color.NONE;

        assertThatThrownBy(() -> knight.checkMovable(start, end, colorOfDestination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Knight가 이동할 수 있는 방향이 아닙니다");

    }

    @Test
    void 도착점에_기물의_색깔이_아군인_경우_예외() {
        Position start = Position.of(Column.D, Rank.FOUR);
        Position end = Position.of(Column.C, Rank.SIX);
        Color colorOfDestination = Color.BLACK;

        assertThatThrownBy(() -> knight.checkMovable(start, end, colorOfDestination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Knight는 도착점에 아군이 있으면 이동할 수 없습니다");

    }

    @Test
    void 모든_이동_조건을_충족하면_true를_반환한다() {
        Position start = Position.of(Column.D, Rank.FOUR);
        Position end = Position.of(Column.C, Rank.SIX);
        Color colorOfDestination = Color.NONE;

        assertDoesNotThrow(() -> knight.checkMovable(start, end, colorOfDestination));

    }

}