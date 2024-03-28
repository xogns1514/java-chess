package domain.piece.piecerole;

public abstract class NonSlidingPiece implements PieceRole {
    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isSlidingPiece() {
        return false;
    }
}
