/*
Name: Emily Ochmann
Course: CEN 3024C
Date: March 9, 2026

Class Name: Main

This class contains the main method that starts the Video Game Collection Manager. The program allows users to
manage a collection of video games using a CLI. Users can perform CRUD operations, search games by title, and
load in game data from a txt file.
 */

import javax.swing.*;

/*
Method Name: main

Purpose: Launches the application by creating the GameService and GUI

Parameters: args
Returns: none
 */
public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            GameDatabaseService dbService = new GameDatabaseService();
            GameManagerGUI gui = new GameManagerGUI(dbService);
            gui.setVisible(true);
        });
    }
}
