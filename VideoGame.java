/*
Name: Emily Ochmann
Course: CEN 3024C
Date: March 8, 2026

Class name: VideoGame

This class represents a video game stored in the VGCM. Each object contains the game's ID, title, genre,
platform, release year, and rating.
 */

public class VideoGame {

    private int gameId;
    private String title;
    private String genre;
    private String platform;
    private int releaseYear;
    private double rating;

    public VideoGame(int gameId, String title, String genre, String platform, int releaseYear, double rating)   {
        this.gameId = gameId;
        this.title = title;
        this.genre = genre;
        this.platform = platform;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    public int getGameId()  {
        return gameId;
    }

    public String getTitle()    {
        return title;
    }

    public String getGenre()    {
        return genre;
    }

    public String getPlatform() {
        return platform;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getRating()   {
        return rating;
    }

    public String setTitle(String title)    {
        this.title = title;
        return title;
    }

    public String setGenre(String genre)    {
        this.genre = genre;
        return genre;
    }

    public String setPlatform(String platform)  {
        this.platform = platform;
        return platform;
    }

    public int setReleaseYear(int year) {
        this.releaseYear = year;
        return year;
    }

    public double setRating(double rating)  {
        this.rating = rating;
        return rating;
    }

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
