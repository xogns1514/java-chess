import controller.ChessController;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        ChessController chessController = new ChessController(new OutputView());
        chessController.run();
    }
}
