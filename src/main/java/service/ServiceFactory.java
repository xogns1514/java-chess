package service;

import repository.ChessBoardRepositoryImpl;
import repository.ChessGameRepositoryImpl;
import repository.dao.ChessBoardDao;
import repository.dao.ChessGameDao;

public class ServiceFactory {
    private static final ServiceFactory serviceFactory = new ServiceFactory();

    private final ChessGameService chessGameService;

    private ServiceFactory() {
        ChessBoardRepositoryImpl chessBoardRepository = new ChessBoardRepositoryImpl(new ChessBoardDao());
        ChessGameRepositoryImpl chessGameRepository = new ChessGameRepositoryImpl(new ChessGameDao());
        chessGameService = new ChessGameService(chessBoardRepository, chessGameRepository);
    }

    public static ServiceFactory getInstance() {
        return serviceFactory;
    }

    public ChessGameService getChessGameService() {
        return chessGameService;
    }
}