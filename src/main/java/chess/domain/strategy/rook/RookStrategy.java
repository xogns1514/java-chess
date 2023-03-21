package chess.domain.strategy.rook;

import chess.domain.dto.PositionDto;
import chess.domain.dto.req.MoveRequest;
import chess.domain.strategy.PieceStrategy;

public class RookStrategy implements PieceStrategy {

    @Override
    public void validateDirection(final MoveRequest request) {
        PositionDto movablePiecePosition = request.getMovablePieceDto();
        PositionDto targetPosition = request.getTargetPositionDto();
        boolean sameRank = movablePiecePosition.getRank() == targetPosition.getRank();
        boolean sameFile = movablePiecePosition.getFile() == targetPosition.getFile();

        if (!sameRank && !sameFile) {
            throw new IllegalArgumentException("룩은 상하좌우로만 움직일 수 있습니다.");
        }
    }

}