package chess.domain.dto.res;

import chess.domain.Piece;
import chess.domain.Color;

public class PieceResponse {

    private final int rank;
    private final char file;
    private final char name;

    public PieceResponse(Piece piece, Color color) {
        this.rank = piece.getRank();
        this.file = piece.getFile();
        this.name = piece.getName(color);
    }

    public boolean samePosition(int rank, int file) {
        return this.rank == rank && this.file == file;
    }

    public String getName() {
        return String.valueOf(this.name);
    }
}