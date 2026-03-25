/*
Name: Emily Ochmann
Course: CEN 3024C
Date: March 8, 2026

Class name: GameUI

This class manages all user interaction for the VGCM. It displays the program menu, collects user input from the CL,
and communicates with the GameService class to perform CRUD operations, searching games, and loading games from .txt
file
 */

import java.util.List;
import java.util.Scanner;

public class GameUI {

    private GameService gameService;
    private Scanner scanner;

    public GameUI(GameService gameService, Scanner scanner) {
        this.gameService = gameService;
        this.scanner = scanner;
    }

    /*
    Method name: displayMenu
    Purpose: Displays the main menu options for the user so they can select an operation to perform

    Parameters: none
    Returns: void
     */

    public void displayMenu() {

        System.out.println("\nVideo Game Collection Manager");
        System.out.println("1. Add Game");
        System.out.println("2. View All Games");
        System.out.println("3. Update Game");
        System.out.println("4. Delete Game");
        System.out.println("5. Search by Title");
        System.out.println("6. Load Games From a File");
        System.out.println("7. Exit");

    }

    /*
    Method name: handleAdd
    Purpose: Collects information from the user to create a new VideoGame object and attempts
    to add it to the collection using the GameService class

    Parameters: none
    Returns: void
     */

    public void handleAdd() {

        int id = gameService.getNextGameId();

        VideoGame game = collectGameInput(id);

        boolean added = gameService.addGame(game);

        if (added)  {
            System.out.println("Game added.");
            System.out.println("Assigned Game ID: " + id);
            System.out.println("Total games now: " + gameService.getAllGames().size());
        }   else {
            System.out.println("Invalid game data. Game was not added.");
        }
    }

    /*
    Method name: displayGames
    Purpose: Retrieves all video games from the GameService and displays them to the user through the CLI

    Parameters: none
    Returns: void
     */

    public void displayGames() {

        List<VideoGame> games = gameService.getAllGames();

        if (games.isEmpty()) {
            System.out.println("No games in collection.");
            return;
        }

        for (VideoGame game : games) {
            System.out.println(game);
        }
    }

    /*
    Method name: handleUpdate
    Purpose: Allows the user to update information for an existing game by entering the game ID and providing new
    information

    Parameters: none
    Returns: void
     */

    public void handleUpdate() {

        System.out.println("Enter Game ID to update:");
        int id = getValidatedInt();

        VideoGame existing = gameService.findGameById(id);

        if (existing == null) {
            System.out.println("Game not found.");
            return;
        }

        VideoGame updated = collectGameInput(id);

        if (gameService.updateGame(id, updated)) {
            System.out.println("Game updated.");
        }
    }

    /*
    Method name: handleDelete
    Purpose: Removes a game from the collection based on the ID provided by the user

    Parameters: none
    Returns: void
     */

    public void handleDelete() {

        System.out.println("Enter Game ID to delete:");
        int id = getValidatedInt();

        if (gameService.deleteGame(id)) {
            System.out.println("Game deleted.");
        } else {
            System.out.println("Game not found.");
        }
    }

    /*
    Method name: handleSearch
    Purpose: Searches for games that titles match or contain the search text entered by the user

    Parameters: none
    Returns: void
     */

    public void handleSearch() {

        scanner.nextLine();

        System.out.println("Enter title to search:");
        String title = scanner.nextLine();

        List<VideoGame> results = gameService.searchByTitle(title);

        if (results.isEmpty()) {
            System.out.println("No games match your search. Please try again.");
        } else {
            for (VideoGame game : results)  {
                System.out.println(game);
            }
        }
    }

    /*
    Method name: handleFileLoad
    Purpose: Prompts the user to enter the location of a .txt file and loads the data from that file into the system

    Parameters: none
    Returns: void
     */

    public void handleFileLoad()    {

        scanner.nextLine();

        System.out.println("Enter file path: ");
        String path = scanner.nextLine();

        if (gameService.loadGamesFromFile(path))    {
            System.out.println("Games loaded successfully!");
        }   else {
            System.out.println("Error loading file");
        }
    }

    /*
    Method name: collectGameInput
    Purpose: Collects video game information from the user and creates a new VideoGame object using the entered
    values

    Parameters: id
    Returns: VideoGame
     */

    private VideoGame collectGameInput(int id) {

        scanner.nextLine();

        String title;
        while (true) {
            System.out.println("Enter Title:");
            title = scanner.nextLine();
            if (!title.trim().isEmpty()) break;
            System.out.println("Title cannot be empty.");
        }

        String genre;
        while (true) {
            System.out.println("Enter Genre:");
            genre = scanner.nextLine();
            if (!genre.trim().isEmpty()) break;
            System.out.println("Genre cannot be empty.");
        }

        String platform = "";
        while (true) {
            System.out.println("Select Platform:");
            System.out.println("1. Xbox");
            System.out.println("2. Playstation");
            System.out.println("3. PC");
            System.out.println("4. Nintendo Switch");
            System.out.print("Enter choice: ");

            if (!scanner.hasNextInt())  {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1)    {
                platform = "Xbox";
                break;
            } else if (choice == 2) {
                platform = "Playstation";
                break;
            } else if (choice == 3) {
                platform = "PC";
                break;
            } else if (choice == 4) {
                platform = "Nintendo Switch";
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        int year;
        while (true) {
            System.out.println("Enter Release Year (1958-2026):");
            year = getValidatedInt();
            if (year >= 1958 && year <= 2026) break;
            System.out.println("Invalid year. Please try again.");
        }

        double rating;
        while (true) {
            System.out.println("Enter Rating (0-10):");
            rating = getValidatedDouble();
            if (rating >= 0 && rating <= 10) break;
            System.out.println("Invalid rating. Please try again.");
        }

        return new VideoGame(id, title, genre, platform, year, rating);
    }

    /*
    Method name: getValidatedInt
    Purpose: Ensures the user enters a valid integer before continuing

    Parameters: none
    Returns: int
     */

    private int getValidatedInt() {

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }

        return scanner.nextInt();
    }

    /*
    Method name: getValidatedDouble
    Purpose: Ensures the user enters a valid decimal number before continuing

    Parameters: none
    Returns: double
     */

    private double getValidatedDouble() {

        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Enter a valid number.");
            scanner.next();
        }

        return scanner.nextDouble();
    }
}