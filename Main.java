/*
Name: Emily Ochmann
Course: CEN 3024C
Date: March 9, 2026

Class Name: Main

This class contains the main method that starts the Video Game Collection Manager. The program allows users to
manage a collection of video games using a CLI. Users can perform CRUD operations, search games by title, and
load in game data from a txt file.
 */

public class Main {

    /* Method Name: main

    * Purpose: This method serves as the entry point for the program. It creates and Application object
    and starts the program.

    * Parameters: args- command line arguments are passed to the program
    * Returns: void
     */

    public static void main(String[] args)  {

        Application application = new Application();

        application.start();
    }
}
