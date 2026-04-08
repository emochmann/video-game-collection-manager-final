import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/*
Name: Emily Ochmann
Course: CEN 3024C
Date: April 7, 2026

Class Name: GameDatabaseService
Description: This class handles all interactions between the application and the SQLite database. It performs CRUD
operations directly on the database. This allows data to persist even after the application is closed.
 */
public class GameDatabaseService {

    private Connection connection;
/*
Method: connect
Purpose: Establishes a connection to the SQLite database using the file path provided by the user

Parameters: dbPath
Returns: boolean
 */
    public boolean connect(String dbPath) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Database connection failed:\n" + e.getMessage());
            return false;
        }
    }
/*
Method: getAllGames
Purpose: Retrieves all video game records from the database and converts them into a list of VideoGame objects

Parameters: none
Returns: List<VideoGame>
 */
    public List<VideoGame> getAllGames() {
        List<VideoGame> games = new ArrayList<>();
        String sql = "SELECT * FROM video_games";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                VideoGame game = new VideoGame(
                        rs.getInt("game_id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("platform"),
                        rs.getInt("release_year"),
                        rs.getDouble("rating")
                );
                games.add(game);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return games;
    }
/*
Method: addGame
Purpose: Adds a new video game record into the database

Parameters: game
Returns: boolean
 */
    public boolean addGame(VideoGame game) {
        String sql = "INSERT INTO video_games (game_id, title, genre, platform, release_year, rating) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, game.getGameId());
            pstmt.setString(2, game.getTitle());
            pstmt.setString(3, game.getGenre());
            pstmt.setString(4, game.getPlatform());
            pstmt.setInt(5, game.getReleaseYear());
            pstmt.setDouble(6, game.getRating());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }
/*
Method: deleteGame
Purpose: Deletes a video game record from the database

Parameters: id
Returns: boolean
 */
    public boolean deleteGame(int id) {
        String sql = "DELETE FROM video_games WHERE game_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
/*
Method: searchByTitle
Purpose: Searches for video games in the database that match the given title using partial matching

Parameters: title
Returns: List<VideoGame>
 */
    public List<VideoGame> searchByTitle(String title) {
        List<VideoGame> results = new ArrayList<>();
        String sql = "SELECT * FROM video_games WHERE LOWER(title) LIKE ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + title.toLowerCase() + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                VideoGame game = new VideoGame(
                        rs.getInt("game_id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("platform"),
                        rs.getInt("release_year"),
                        rs.getDouble("rating")
                );
                results.add(game);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }
/*
Method: getNextGameId
Purpose: Finds the highest existing game ID in the database and returns the next available ID

Parameters: none
Returns: int
 */
    public int getNextGameId() {
        String sql = "SELECT MAX(game_id) FROM video_games";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.getInt(1) + 1;

        } catch (SQLException e) {
            return 1;
        }
    }
/*
Method: updateGame
Purpose: Updates a specific field of a video game record in the database

Parameters: gameId, fieldChoice, newValue
Returns: boolean
 */
    public boolean updateGame(int gameId, int fieldChoice, String newValue) {
        String columnName;

        switch (fieldChoice) {
            case 1: columnName = "title"; break;
            case 2: columnName = "genre"; break;
            case 3: columnName = "platform"; break;
            case 4: columnName = "release_year"; break;
            case 5: columnName = "rating"; break;
            default: return false;
        }

        String sql = "UPDATE video_games SET " + columnName + " = ? WHERE game_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            if (fieldChoice == 4) {
                pstmt.setInt(1, Integer.parseInt(newValue));
            } else if (fieldChoice == 5) {
                pstmt.setDouble(1, Double.parseDouble(newValue));
            } else {
                pstmt.setString(1, newValue);
            }

            pstmt.setInt(2, gameId);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            return false;
        }
    }
}
