/**
 * Name: Emily Ochmann
 * Course: CEN 3024C
 * Date: April 13, 2026
 * Represents a video game in the Video Game Collection Manager (VGCM).
 *
 * Each VideoGame object stores information about a single game,
 * including its ID, title, genre, platform, release year, and rating.
 *
 * This class is used throughout the application to transfer and store
 * game data between the database and the user interface.
 */

public class VideoGame {

    private int gameId;
    private String title;
    private String genre;
    private String platform;
    private int releaseYear;
    private double rating;
    /**
     * Creates a new VideoGame object with the specified details.
     *
     * @param gameId the unique ID of the game
     * @param title the title of the game
     * @param genre the genre of the game
     * @param platform the platform the game is played on
     * @param releaseYear the year the game was released
     * @param rating the rating of the game
     */
    public VideoGame(int gameId, String title, String genre, String platform, int releaseYear, double rating)   {
        this.gameId = gameId;
        this.title = title;
        this.genre = genre;
        this.platform = platform;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }
    /**
     * Gets the game ID.
     * @return the game ID
     */
    public int getGameId()  {
        return gameId;
    }
    /**
     * Gets the game title.
     * @return the title of the game
     */
    public String getTitle()    {
        return title;
    }
    /**
     * Gets the game genre.
     * @return the genre of the game
     */
    public String getGenre()    {
        return genre;
    }
    /**
     * Gets the platform of the game.
     * @return the platform
     */
    public String getPlatform() {
        return platform;
    }
    /**
     * Gets the release year of the game.
     * @return the release year
     */
    public int getReleaseYear() {
        return releaseYear;
    }
    /**
     * Gets the rating of the game.
     * @return the game rating
     */
    public double getRating()   {
        return rating;
    }
    /**
     * Sets the title of the game.
     *
     * @param title the new title
     * @return the updated title
     */
    public String setTitle(String title)    {
        this.title = title;
        return title;
    }
    /**
     * Sets the genre of the game.
     *
     * @param genre the new genre
     * @return the updated genre
     */
    public String setGenre(String genre)    {
        this.genre = genre;
        return genre;
    }
    /**
     * Sets the platform of the game.
     *
     * @param platform the new platform
     * @return the updated platform
     */
    public String setPlatform(String platform)  {
        this.platform = platform;

        return platform;
    }
    /**
     * Sets the release year of the game.
     *
     * @param year the new release year
     * @return the updated release year
     */
    public int setReleaseYear(int year) {
        this.releaseYear = year;
        return year;
    }
    /**
     * Sets the rating of the game.
     *
     * @param rating the new rating
     * @return the updated rating
     */
    public double setRating(double rating)  {
        this.rating = rating;
        return rating;
    }
    /**
     * Returns a formatted string representation of the video game.
     *
     * @return a string containing all game details
     */
    @Override
    public String toString() {
        return  "----------------------------\n" +
                "ID: " + gameId + "\n" +
                "Title: " + title + "\n" +
                "Genre: " + genre + "\n" +
                "Platform: " + platform + "\n" +
                "Release Year: " + releaseYear + "\n" +
                "Rating: " + rating + "\n" +
                "----------------------------";
    }
}
