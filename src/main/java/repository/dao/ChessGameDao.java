package repository.dao;

import domain.game.GameState;
import domain.piece.Color;
import exception.DataBaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import repository.DatabaseConnection;

public class ChessGameDao {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public void save(Color color, GameState gameState) {
        final String query = "INSERT INTO chess_game(id, color, game_status) VALUES(?, ?, ?)";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, color.name());
            preparedStatement.setString(3, gameState.name());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage() + "chessGame을 저장할 수 없습니다.");
        }
    }

    public GameState findGameStatusById() {
        final String query = "SELECT game_status FROM chess_game WHERE id = ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String gameStatus = resultSet.getString("game_status");
                return GameState.of(gameStatus);
            }
            return null;
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage() + "chessGame을 찾을 수 없습니다.");
        }
    }

    public Color findColorById() {
        final String query = "SELECT color FROM chess_game WHERE id = ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String color = resultSet.getString("color");
                return Color.of(color);
            }
            return null;
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage() + "chessGame의 Color를 찾을 수 없습니다.");
        }
    }

    public void updateGameStatus(GameState gameState) {
        final String query = "UPDATE chess_game SET game_status = ? WHERE id = ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameState.name());
            preparedStatement.setInt(2, 1);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage() + "chessGame을 update할 수 없습니다.");
        }
    }

    public void updateColor(Color color) {
        final String query = "UPDATE chess_game SET color = ? WHERE id = ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, color.name());
            preparedStatement.setInt(2, 1);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage() + "chessGame의 Color를 업데이트 할 수 없습니다.");
        }
    }

    public boolean delete() {
        final String query = "DELETE FROM chess_game WHERE id = ?";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, 1);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage() + "chessGame을 삭제할 수 없습니다.");
        }
    }
}
