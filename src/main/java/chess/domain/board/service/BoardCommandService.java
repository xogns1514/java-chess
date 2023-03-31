package chess.domain.board.service;

import chess.dao.BoardDao;
import chess.domain.board.Board;
import chess.domain.board.service.dto.BoardModifyRequest;
import chess.domain.board.service.dto.BoardRegisterRequest;
import chess.domain.board.service.mapper.BoardMapper;

public class BoardCommandService {

    private final BoardDao boardDao;
    private final BoardMapper boardMapper;

    public BoardCommandService(final BoardDao boardDao, final BoardMapper boardMapper) {
        this.boardDao = boardDao;
        this.boardMapper = boardMapper;
    }

    public Long registerBoard(final Board board) {

        final BoardRegisterRequest boardRegisterRequest = boardMapper.mapToBoardRegisterRequestFrom(board);

        return boardDao.save(boardRegisterRequest);
    }

    public void modifyBoard(final Board board) {

        final BoardRegisterRequest boardRegisterRequest = boardMapper.mapToBoardRegisterRequestFrom(board);

        final BoardModifyRequest boardModifyRequest = new BoardModifyRequest(board.id(),
                                                                             boardRegisterRequest.position(),
                                                                             boardRegisterRequest.turn()
        );

        boardDao.modifyById(boardModifyRequest);
    }

    public void deleteBoard(final Long boardId) {
        boardDao.deleteById(boardId);
    }
}