package domain.board;

import domain.piece.Piece;
import domain.piece.Team;
import domain.position.File;
import domain.position.Position;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public final class Board {

    private static final String NOT_EXIST_SOURCE = "해당 위치에 말이 존재하지 않습니다.";
    private static final String INVALID_MOVEMENT = "해당 위치로 말을 이동할 수 없습니다.";
    private static final String INVALID_PLAYER_TURN = "잘못된 차례입니다.";
    private static final double MULTI_PAWN_SCORE = 0.5;

    private final Map<Position, Piece> board;
    private Team currentTurn;

    private Board(final Map<Position, Piece> board) {
        this.board = board;
        this.currentTurn = Team.WHITE;
    }

    public static Board create(final ChessAlignment alignment) {
        return new Board(alignment.makeInitialPieces());
    }

    public static Board load(final Map<Position, Piece> pieces) {
        return new Board(pieces);
    }

    public void move(final Position source, final Position destination) {
        validateTurn(source);
        validateRoute(source, destination);

        if (board.containsKey(destination)) {
            captureDestination(source, destination);
        }
        if (!board.containsKey(destination)) {
            moveDestination(source, destination);
        }
        changeTurn();
    }

    private void validateTurn(final Position source) {
        if (!getPiece(source).isTeam(currentTurn)) {
            throw new IllegalArgumentException(INVALID_PLAYER_TURN);
        }
    }

    private Piece getPiece(final Position source) {
        if (!board.containsKey(source)) {
            throw new IllegalArgumentException(NOT_EXIST_SOURCE);
        }

        return board.get(source);
    }

    private void validateRoute(final Position source, final Position destination) {
        if (pieceInRoute(source, destination)) {
            throw new IllegalArgumentException(INVALID_MOVEMENT);
        }

        if (isSameTeamOnDestination(destination, getPiece(source))) {
            throw new IllegalArgumentException(INVALID_MOVEMENT);
        }
    }

    private boolean pieceInRoute(final Position source, final Position destination) {
        return source.between(destination).stream()
                .anyMatch(board::containsKey);
    }

    private boolean isSameTeamOnDestination(final Position destination, final Piece sourcePiece) {
        return board.containsKey(destination) &&
                sourcePiece.isBlack() == getPiece(destination).isBlack();
    }

    private void captureDestination(final Position source, final Position destination) {
        if (getPiece(source).isCapturable(source, destination)) {
            board.put(destination, board.remove(source));
            return;
        }

        throw new IllegalArgumentException(INVALID_MOVEMENT);
    }

    private void moveDestination(final Position source, final Position destination) {
        if (getPiece(source).isMovable(source, destination)) {
            board.put(destination, board.remove(source));
            return;
        }

        throw new IllegalArgumentException(INVALID_MOVEMENT);
    }

    private void changeTurn() {
        if (currentTurn.equals(Team.BLACK)) {
            currentTurn = Team.WHITE;
            return;
        }

        currentTurn = Team.BLACK;
    }

    public boolean isBlackKingExist() {
        return board.values().stream()
                .filter(Piece::isBlack)
                .anyMatch(Piece::isKing);
    }

    public boolean isWhiteKingExist() {
        return board.values().stream()
                .filter(Piece::isWhite)
                .anyMatch(Piece::isKing);
    }

    public Team getWinner() {
        if (isWhiteKingExist() && isBlackKingExist()) {
            return Team.NOTHING;
        }
        if (isBlackKingExist()) {
            return Team.BLACK;
        }
        return Team.WHITE;
    }

    public double getCurrentBlackScore() {
        final double sumScore = sumDefaultScore(Team.BLACK);
        return minusMultiPawnScore(sumScore, Team.BLACK);
    }

    public double getCurrentWhiteScore() {
        final double sumScore = sumDefaultScore(Team.WHITE);
        return minusMultiPawnScore(sumScore, Team.WHITE);
    }

    private double sumDefaultScore(Team team) {
        return board.values().stream()
                .filter(piece -> piece.isTeam(team))
                .mapToDouble(Piece::getScore)
                .sum();
    }

    private double minusMultiPawnScore(double defaultScore, Team team) {
        final Map<File, Long> countPawns = countPawnsOnFile(team);
        final double multiPawnScore = countPawns.values().stream()
                .filter(count -> count > 1)
                .mapToDouble(count -> count * MULTI_PAWN_SCORE)
                .sum();

        return defaultScore - multiPawnScore;
    }

    private Map<File, Long> countPawnsOnFile(Team team) {
        return board.entrySet().stream()
                .filter(entry -> entry.getValue().isTeam(team) && entry.getValue().isPawn())
                .collect(Collectors.groupingBy(entry -> entry.getKey().getFile(), Collectors.counting()));
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(board);
    }
}