package chess.domain;

import chess.domain.dto.PositionDto;
import chess.domain.dto.req.MoveRequest;

import java.util.List;

public class Piece {

    private Position position;
    private final Shape shape;

    private Piece(final Position position, final Shape shape) {
        this.position = position;
        this.shape = shape;
    }

    public static Piece from(final int rank, final char file, final Shape shape) {
         return new Piece(Position.from(rank, file), shape);
    }

    public boolean isSameShape(Shape shape) {
        return this.shape == shape;
    }

    public Position move(
            final List<Position> positions,
            final Position inputTargetPosition,
            final Color movablePieceColor
    ) {
        char file = inputTargetPosition.getFileValue();
        int rank = inputTargetPosition.getRankValue();

        shape.move(MoveRequest.from(
                positions,
                movablePieceColor,
                new PositionDto(position),
                new PositionDto(Position.from(rank, file))
        ));

        Position changedPosition = Position.from(rank, file);
        this.position = changedPosition;
        return changedPosition;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "position=" + position +
                ", shape=" + shape +
                '}';
    }

    public Piece getNewPiece(int file) {
        return new Piece(position.changePosition(file), this.shape);
    }

    public Position getPosition() {
        return position;
    }

    public int getRank() {
        return position.getRankValue();
    }

    public char getFile() {
        return position.getFileValue();
    }

    public char getName(Color color) {
        return this.shape.getNameByColor(color);
    }

    public boolean isSamePosition(Position findPosition) {
        return position.equals(findPosition);
    }

}