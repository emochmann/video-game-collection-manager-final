import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Name: Emily Ochmann
 * Course: CEN 3024C
 * Date: April 13, 2026
 * Handles all interactions between the application and the SQLite database.
 *
 * This class is responsible for performing CRUD
 * operations on the video_games table. It allows the application to store,
 * retrieve, update, and delete video game data.
 *
 * This class is used by other components, such as the GUI, to persist
 * data between application sessions.
 */
public class GameDatabaseService {

    private Connection connection;
    /**
     * Establishes a connection to the SQLite database using the given file path.
     *
     * @param dbPath the file path to the database
     * @return true if the connection is successful, false otherwise
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
    /**
     * Retrieves all video game records from the database.
     *
     * @return a list of VideoGame objects representing all stored games
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
    /**
     * Adds a new video game to the database.
     *
     * @param game the VideoGame object to add
     * @return true if the game was added successfully, false otherwise
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
    /**
     * Deletes a video game from the database by its ID.
     *
     * @param id the ID of the game to delete
     * @return true if the game was deleted successfully, false otherwise
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
    /**
     * Searches for video games that match the given title using partial matching.
     *
     * @param title the title or partial title to search for
     * @return a list of VideoGame objects that match the search criteria
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
    /**
     * Retrieves the next available game ID by finding the current maximum ID
     * in the database and incrementing it.
     *
     * @return the next available game ID
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
    /**
     * Updates a specific field of a video game in the database.
     *
     * @param gameId the ID of the game to update
     * @param fieldChoice the field to update (1 = title, 2 = genre, 3 = platform,
     *                    4 = release year, 5 = rating)
     * @param newValue the new value to assign to the selected field
     * @return true if the update was successful, false otherwise
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
