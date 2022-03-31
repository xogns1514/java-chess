package chess.status;

import chess.game.Board;
import chess.game.MoveCommand;
import chess.piece.detail.Color;
import chess.view.Command;
import java.util.Map;

public interface State {

    State turn(Command command);

    boolean isRunning();

    void move(MoveCommand moveCommand);

    boolean canMove();

    Board getBoard();

    boolean isGameEnd();

    Color getTurn();

    Map<Color, Double> getStatus();
}