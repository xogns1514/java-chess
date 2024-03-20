package domain.chessboard;

import domain.position.File;
import domain.position.Position;
import domain.position.Rank;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SquareTest {
    @DisplayName("같은 위치에 있는 Square 객체는 동일한 Square 객체이다.")
    @Test
    void generateSquare() {
        Square square = new Square(new Position(new File('a'), new Rank(0)));
        Assertions.assertThat(square)
                .isEqualTo(new Square(new Position(new File('a'), new Rank(0))));
    }
}