package domain.game;

import domain.chessboard.Square;
import domain.piece.Color;
import domain.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class PieceMover {

    private final Map<Square, Piece> pieceBySquare;

    public PieceMover() {
        this.pieceBySquare = new HashMap<>();
    }

    public void add(Square square, Piece piece) {
        pieceBySquare.put(square, piece);
    }

    public void move(Square sourceSquare, Square targetSquare) {
        if (!pieceBySquare.containsKey(sourceSquare)) {
            throw new IllegalStateException("해당 위치에 Piece가 존재하지 않습니다.");
        }
        if (hasSameColorPiece(sourceSquare, targetSquare)) {
            throw new IllegalStateException("같은 진영의 기물이 있는 곳으로 옮길 수 없습니다.");
        }
        if (sourceSquare.equals(targetSquare)) {
            throw new IllegalStateException("같은 위치로의 이동입니다. 다시 입력해주세요.");
        }

        Piece findPiece = pieceBySquare.get(sourceSquare);
        if (findPiece.isPawn()) { // 예외 처리만
            Direction direction = Direction.findDirection(sourceSquare.getPosition(), targetSquare.getPosition());
            if (findPiece.isEqualColor(Color.BLACK)) {
                if (direction == Direction.SOUTH && pieceBySquare.containsKey(targetSquare)) {
                    throw new IllegalStateException("갈 수 없음");
                }
                if (direction == Direction.SOUTH_EAST && !pieceBySquare.containsKey(targetSquare)) {
                    throw new IllegalStateException("갈 수 없음");
                }
                if (direction == Direction.SOUTH_WEST && !pieceBySquare.containsKey(targetSquare)) {
                    throw new IllegalStateException("갈 수 없음");
                }
            }
            if (findPiece.isEqualColor(Color.WHITE)) {
                if (direction == Direction.NORTH && pieceBySquare.containsKey(targetSquare)) {
                    throw new IllegalStateException("갈 수 없음");
                }
                if (direction == Direction.NORTH_WEST && !pieceBySquare.containsKey(targetSquare)) {
                    throw new IllegalStateException("갈 수 없음");
                }
                if (direction == Direction.NORTH_EAST && !pieceBySquare.containsKey(targetSquare)) {
                    throw new IllegalStateException("갈 수 없음");
                }
            }
        }

        if (findPiece.isNotKnight()) { // 예외 처리만
            Direction direction = Direction.findDirection(sourceSquare.getPosition(), targetSquare.getPosition());

            Position here = new Position(sourceSquare.getPosition());
            here.move(direction);
            while (!here.equals(targetSquare.getPosition())) {
                if (pieceBySquare.containsKey(new Square(here))) {
                    throw new IllegalStateException("갈 수 없음");
                }
                here.move(direction);
            }
        }

        if (findPiece.canMove(sourceSquare, targetSquare)) {
            if (pieceBySquare.containsKey(targetSquare)) {
                pieceBySquare.remove(targetSquare);
            }

            pieceBySquare.put(targetSquare, findPiece);
            pieceBySquare.remove(sourceSquare);
        }
    }

    private boolean hasSameColorPiece(Square sourceSquare, Square targetSquare) {
        Piece sourcePiece = pieceBySquare.get(sourceSquare);

        if (pieceBySquare.containsKey(targetSquare)) {
            Piece targetPiece = pieceBySquare.get(targetSquare);
            return sourcePiece.isEqualColor(targetPiece);
        }
        return false;
    }


    public boolean hasPiece(final Square square) {
        return pieceBySquare.containsKey(square);
    }

    public Piece findPieceBySquare(Square targetSquare) {
        return pieceBySquare.get(targetSquare);
    }
}
