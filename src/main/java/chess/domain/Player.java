package chess.domain;

import java.util.List;

public class Player {

    private final Color color;
    private final Pieces pieces;

    private Player(final Color color, final Pieces pieces) {
        this.color = color;
        this.pieces = pieces;
    }

    public static Player fromWhitePlayer(final Pieces pieces) {
        return new Player(Color.WHITE, pieces);
    }

    public static Player fromBlackPlayer(final Pieces pieces) {
        return new Player(Color.BLACK, pieces);
    }

    @Override
    public String toString() {
        return "Player{" +
                "color='" + color + '\'' +
                ", pieces=" + pieces +
                '}';
    }

    public Pieces getOriginPieces() {
        return this.pieces;
    }

    public List<Piece> getPieces() {
        return pieces.getPieces();
    }

    public Color getColor() {
        return color;
    }

    public boolean hasPositionPiece(final Position findPosition) {
        return pieces.hasPosition(findPosition);
    }

    public Position movePieceByInput(
            final List<Position> allPosition,
            final Position findPosition,
            final Position targetPosition
    ) {
        if (pieces.hasPosition(targetPosition)) {
            throw new IllegalArgumentException("상대 기물 위치로만 이동할 수 있습니다.");
        }

        Piece findPiece = findPiece(findPosition);
        return findPiece.move(allPosition, targetPosition, color);
    }

    private Piece findPiece(final Position findPosition) {
        return pieces.findPiece(findPosition);
    }

    public void removePiece(Position changedPosition) {
        pieces.remove(changedPosition);
    }
}