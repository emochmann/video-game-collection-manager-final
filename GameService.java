/*
Name: Emily Ochmann
Course: CEN 3024C
Date: March 9, 2026

Class name: GameService

This class manages the core logic of the VGCM. It stores the list of VideoGame objects and performs operations
such as adding, updating, deleting, searching, validating, and loading games from a file.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class GameService {

    private List<VideoGame> games;

    public GameService() {

        games = new ArrayList<>();

    }

    /*
    Method name: addGame
    Purpose: Adds a VideoGame object to the collection if the game data is valid

    Parameters: game
    Returns: boolean
     */

    public boolean addGame(VideoGame game) {

        if (!validateGame(game)) {
            return false;
        }

        if (findGameById(game.getGameId()) != null) {
            return false;
        }

        games.add(game);
        return true;
    }

    /*
    Method name: getAllGames
    Purpose: Retrieves the list of all video games currently stored in the system

    Parameters: none
    Returns: List<VideoGame>
     */

    public List<VideoGame> getAllGames() {

        return games;
    }

    /*
    Method name: findGameById
    Purpose: Searches the game collection for a game with the specified ID

    Parameters: gameId
    Returns: VideoGame
     */

    public VideoGame findGameById(int gameId) {

        for (VideoGame game : games) {

            if (game.getGameId() == gameId) {
                return game;
            }
        }

        return null;
    }

    /*
    Method name: updateGame
    Purpose: Updates an existing game's information using the values from the provided VideoGame object

    Parameters: gameId and updatedGame
    Returns: boolean
     */

    public boolean updateGame(int gameId, VideoGame updatedGame) {

        VideoGame game = findGameById(gameId);

        if (game == null) {
            return false;
        }

        if (!validateGame(updatedGame)) {
            return false;
        }

        game.setTitle(updatedGame.getTitle());
        game.setGenre(updatedGame.getGenre());
        game.setPlatform(updatedGame.getPlatform());
        game.setReleaseYear(updatedGame.getReleaseYear());
        game.setRating(updatedGame.getRating());

        return true;
    }

    /*
    Method name: deleteGame
    Purpose: Removes a game from the collection based on its ID

    Parameters: gameId
    Returns: boolean
     */

    public boolean deleteGame(int gameId) {

        VideoGame game = findGameById(gameId);

        if (game != null) {
            games.remove(game);
            return true;
        }

        return false;
    }

    /*
    Method name: searchByTitle
    Purpose: Searches for games whose titles contain the specified text

    Parameters: title
    Returns: List<VideoGame>
     */

    public List<VideoGame> searchByTitle(String title) {

        List<VideoGame> results = new ArrayList<>();

        for (VideoGame game : games) {

            if (game.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(game);
            }
        }

        return results;
    }

    /*
    Method name: validateGame
    Purpose: Ensures that a VideoGame object contains valid data before it is added or updated in the collection

    Parameters: game
    Returns: boolean
     */

    public boolean validateGame(VideoGame game) {

        if (game.getTitle() == null || game.getTitle().isEmpty()) return false;

        if (game.getGenre() == null || game.getGenre().isEmpty()) return false;

        if (game.getPlatform() == null || game.getPlatform().isEmpty()) return false;

        if (game.getReleaseYear() < 1958 || game.getReleaseYear() > 2026) return false;

        if (game.getRating() < 0 || game.getRating() > 10) return false;

        return true;
    }

    /*
    Method name: getNextGameId
    Purpose: Generates a new unique game ID based on the highest existing ID to eliminate duplicates

    Parameters: none
    Returns: int
     */

    public int getNextGameId() {

        int maxId = 0;

        for (VideoGame game : games) {
            if (game.getGameId() > maxId) {
                maxId = game.getGameId();
            }
        }
        return maxId + 1;
    }

    /*
    Method name: loadGamesFromFile
    Purpose: Reads a .txt file containing video game records and loads them into the collection

    Parameters: filePath
    Returns: boolean
     */

    public boolean loadGamesFromFile(String filePath) {

        try (Scanner fileScanner = new Scanner(new File(filePath))) {

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] parts = line.split(",");

                if (parts.length != 6) {
                    continue;
                }

                int id = Integer.parseInt(parts[0].trim());
                String title = parts[1].trim();
                String genre = parts[2].trim();
                String platform = parts[3].trim();
                int year = Integer.parseInt(parts[4].trim());
                double rating = Double.parseDouble(parts[5].trim());

                VideoGame game = new VideoGame(id, title, genre, platform, year, rating);

                if (!addGame(game)) {
                    System.out.println("Duplicate GameID or invalid game skipped: " + title);
                }
            }

            return true;

        } catch (FileNotFoundException e) {
            System.out.println("Error: File could not be opened.");
            return false;
        } catch (Exception e) {
            System.out.println("Error: Invalid data found in file.");
            return false;
        }
    }
}
