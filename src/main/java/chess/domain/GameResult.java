package chess.domain;

import chess.domain.piece.Piece;
import chess.domain.piece.Pieces;
import chess.domain.position.File;
import chess.domain.position.Position;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameResult {

    private final Set<Pieces> piecesByFile;

    private GameResult(final Set<Pieces> piecesByFile) {
        this.piecesByFile = piecesByFile;
    }

    public static GameResult from(final Map<Position, Piece> piecesByPosition) {
        return new GameResult(collectPiecesByFile(piecesByPosition));
    }

    private static Set<Pieces> collectPiecesByFile(final Map<Position, Piece> piecesByPosition) {
        return IntStream.rangeClosed(File.min(), File.max())
            .mapToObj(number -> Position.of(number, number))
            .map(target -> findPiecesInSameFile(target, piecesByPosition))
            .collect(Collectors.toSet());
    }

    private static Pieces findPiecesInSameFile(final Position target,
        final Map<Position, Piece> piecesByPosition) {
        return new Pieces(piecesByPosition.keySet()
            .stream()
            .filter(position -> position.isInSameFile(target))
            .map(piecesByPosition::get)
            .collect(Collectors.toList()));
    }

    public double calculateScoreOfTeam(final TeamColor color) {
        return piecesByFile.stream()
            .map(pieces -> pieces.calculateScoreOfTeam(color))
            .reduce(Score.INITIAL_SCORE, Score::add)
            .getValue();
    }

}
