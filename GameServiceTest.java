/* Name: Emily Ochmann
Course: CEN 3024C
Date: March 24, 2026

Class Name: GameServiceTest
Purpose: This class contains JUnit tests to verify the functionality of the GameService class. It
tests CRUD operations, file loading, validation, duplicate ID handling, ID generation, and the custom search
by title feature. Both affirmative and negative test cases are included to ensure the system behaves correctly
and handles errors without crashing.
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {

    private GameService gameService;

    @BeforeEach
    public void setUp() {
        gameService = new GameService();
    }

    //ADD GAME TESTS

    @Test
    public void testAddGamePositive() {
        VideoGame game = new VideoGame(1, "Minecraft", "Sandbox", "PC", 2011, 9.5);

        boolean result = gameService.addGame(game);

        assertTrue(result);
        assertEquals(1, gameService.getAllGames().size());
    }

    @Test
    public void testAddGameNegativeDuplicateId() {
        VideoGame game1 = new VideoGame(1, "Minecraft", "Sandbox", "PC", 2011, 9.5);
        VideoGame game2 = new VideoGame(1, "Stardew Valley", "Simulation", "PC", 2016, 9.0);

        gameService.addGame(game1);
        boolean result = gameService.addGame(game2);

        assertFalse(result);
        assertEquals(1, gameService.getAllGames().size());
    }

    @Test
    public void testAddGameNegativeInvalidData() {
        VideoGame game = new VideoGame(2, "", "Sandbox", "PC", 2011, 9.5);

        boolean result = gameService.addGame(game);

        assertFalse(result);
        assertEquals(0, gameService.getAllGames().size());
    }

    //DELETE TESTS

    @Test
    public void testDeleteGamePositive() {
        VideoGame game = new VideoGame(1, "Halo 3", "Shooter", "Xbox", 2007, 9.0);
        gameService.addGame(game);

        boolean result = gameService.deleteGame(1);

        assertTrue(result);
        assertNull(gameService.findGameById(1));
    }

    @Test
    public void testDeleteGameNegative() {
        boolean result = gameService.deleteGame(99);

        assertFalse(result);
    }

    //UPDATE TESTS

    @Test
    public void testUpdateGamePositive() {
        VideoGame original = new VideoGame(1, "Halo 3", "Shooter", "Xbox", 2007, 9.0);
        gameService.addGame(original);

        VideoGame updated = new VideoGame(1, "Halo Infinite", "Shooter", "PC", 2021, 8.5);

        boolean result = gameService.updateGame(1, updated);

        assertTrue(result);
        assertEquals("Halo Infinite", gameService.findGameById(1).getTitle());
        assertEquals("PC", gameService.findGameById(1).getPlatform());
    }

    @Test
    public void testUpdateGameNegativeGameNotFound() {
        VideoGame updated = new VideoGame(99, "Halo Infinite", "Shooter", "PC", 2021, 8.5);

        boolean result = gameService.updateGame(99, updated);

        assertFalse(result);
    }

    @Test
    public void testUpdateGameNegativeInvalidData() {
        VideoGame original = new VideoGame(1, "Halo 3", "Shooter", "Xbox", 2007, 9.0);
        gameService.addGame(original);

        VideoGame updated = new VideoGame(1, "", "Shooter", "PC", 2021, 8.5);

        boolean result = gameService.updateGame(1, updated);

        assertFalse(result);
        assertEquals("Halo 3", gameService.findGameById(1).getTitle());
    }

    //ATTRIBUTE UPDATE TESTS

    @Test
    public void testUpdateAnyAttributePositive() {
        VideoGame original = new VideoGame(1, "Mario Kart 8", "Racing", "Nintendo Switch", 2014, 8.8);
        gameService.addGame(original);

        VideoGame updated = new VideoGame(1, "Mario Kart 8 Deluxe", "Party Racing", "Nintendo Switch", 2017, 9.4);

        boolean result = gameService.updateGame(1, updated);

        assertTrue(result);
        VideoGame game = gameService.findGameById(1);
        assertEquals("Mario Kart 8 Deluxe", game.getTitle());
        assertEquals("Party Racing", game.getGenre());
        assertEquals("Nintendo Switch", game.getPlatform());
        assertEquals(2017, game.getReleaseYear());
        assertEquals(9.4, game.getRating());
    }

    @Test
    public void testUpdateAnyAttributeNegativeInvalidYear() {
        VideoGame original = new VideoGame(1, "Mario Kart 8", "Racing", "Nintendo Switch", 2014, 8.8);
        gameService.addGame(original);

        VideoGame updated = new VideoGame(1, "Mario Kart 8 Deluxe", "Party Racing", "Nintendo Switch", 3000, 9.4);

        boolean result = gameService.updateGame(1, updated);

        assertFalse(result);
        assertEquals(2014, gameService.findGameById(1).getReleaseYear());
    }

    @Test
    public void testUpdateAnyAttributeNegativeInvalidRating() {
        VideoGame original = new VideoGame(1, "Mario Kart 8", "Racing", "Nintendo Switch", 2014, 8.8);
        gameService.addGame(original);

        VideoGame updated = new VideoGame(1, "Mario Kart 8 Deluxe", "Party Racing", "Nintendo Switch", 2017, 15.0);

        boolean result = gameService.updateGame(1, updated);

        assertFalse(result);
        assertEquals(8.8, gameService.findGameById(1).getRating());
    }

    //SEARCH BY TITLE TESTS

    @Test
    public void testSearchByTitlePositive() {
        gameService.addGame(new VideoGame(1, "Call of Duty Cold War", "Shooter", "Playstation", 2020, 8.0));
        gameService.addGame(new VideoGame(2, "Call of Duty Modern Warfare", "Shooter", "PC", 2019, 8.5));
        gameService.addGame(new VideoGame(3, "Minecraft", "Sandbox", "PC", 2011, 9.5));

        List<VideoGame> results = gameService.searchByTitle("Call of Duty");

        assertEquals(2, results.size());
    }

    @Test
    public void testSearchByTitleNegative() {
        gameService.addGame(new VideoGame(1, "Minecraft", "Sandbox", "PC", 2011, 9.5));

        List<VideoGame> results = gameService.searchByTitle("Zelda");

        assertTrue(results.isEmpty());
    }

    //FILE LOAD TESTS

    @Test
    public void testLoadGamesFromFilePositive() throws IOException {
        String fileName = "test_games.txt";

        FileWriter writer = new FileWriter(fileName);
        writer.write("1,Minecraft,Sandbox,PC,2011,9.5\n");
        writer.write("2,Stardew Valley,Simulation,PC,2016,9.0\n");
        writer.close();

        boolean result = gameService.loadGamesFromFile(fileName);

        assertTrue(result);
        assertEquals(2, gameService.getAllGames().size());
    }

    @Test
    public void testLoadGamesFromFileNegative() {
        boolean result = gameService.loadGamesFromFile("does_not_exist.txt");

        assertFalse(result);
    }

    //NEXT ID TESTS

    @Test
    public void testGetNextGameIdPositive() {
        gameService.addGame(new VideoGame(3, "Minecraft", "Sandbox", "PC", 2011, 9.5));
        gameService.addGame(new VideoGame(7, "Halo 3", "Shooter", "Xbox", 2007, 9.0));

        int nextId = gameService.getNextGameId();

        assertEquals(8, nextId);
    }

    @Test
    public void testGetNextGameIdNegativeStyleCheck() {
        int nextId = gameService.getNextGameId();

        assertNotEquals(0, nextId);
        assertEquals(1, nextId);
    }

    //VALIDATION TESTS

    @Test
    public void testValidateGamePositive() {
        VideoGame game = new VideoGame(1, "Elden Ring", "RPG", "PC", 2022, 9.7);

        boolean result = gameService.validateGame(game);

        assertTrue(result);
    }

    @Test
    public void testValidateGameNegative() {
        VideoGame game = new VideoGame(1, "Bad Game", "Action", "PC", 2020, 15.0);

        boolean result = gameService.validateGame(game);

        assertFalse(result);
    }
}