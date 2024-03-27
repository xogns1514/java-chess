package domain.piece.piecerole;

public abstract class Pawn implements PieceRole {
    @Override
    public boolean isPawn() {
        return true;
    }

    @Override
    public boolean isSlidingPiece() {
        return false;
    }
}
