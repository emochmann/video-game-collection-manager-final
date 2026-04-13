import javax.swing.*;

/**
 * Name: Emily Ochmann
 * Course: CEN 3024C
 * Date: April 12, 2026
 * Entry point for the Video Game Collection Manager application.
 *
 * This class contains the main method, which initializes the database service
 * and launches the GUI.
 *
 * The application allows users to manage a collection of video games,
 * including adding, updating, deleting, and searching for games by title.
 */
public class Main {
    /**
     * Starts the program by setting up the database and opening the GUI.
     *
     * @param args command-line arguments (not used in this program)
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            GameDatabaseService dbService = new GameDatabaseService();
            GameManagerGUI gui = new GameManagerGUI(dbService);
            gui.setVisible(true);
        });
    }
}
