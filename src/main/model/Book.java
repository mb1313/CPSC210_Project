package model;

import org.json.JSONObject;

import javax.xml.bind.util.JAXBSource;

//Represents a book with a title, author, genre, and reading status
public class Book {
    private String title;
    private String author;
    private String genre;
    private int status;

    private EventLog eventLog = EventLog.getInstance();

    //MODIFIES: this
    //EFFECTS: creates a new book with given information and default sets status to unread
    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        status = 0;
        eventLog.logEvent(new Event("Added new book: " + title));
    }

    //MODIFIES: this
    //EFFECTS: creates a new book with given information and sets status to input
    //          for loading data from file
    public Book(String title, String author, String genre, int status) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.status = status;
        eventLog.logEvent(new Event("Added new book: " + title));
    }

    //EFFECTS: returns the title of the book
    public String getTitle() {
        return title;
    }

    //EFFECTS: returns the author of the book
    public String getAuthor() {
        return author;
    }

    //EFFECTS: returns the genre of the book
    public String getGenre() {
        return genre;
    }

    //EFFECTS: returns status of the book (read, unread, or in progress)
    public int getStatus() {
        return status;
    }



    //EFFECTS: returns all details of the book at once
    public String viewBook() {
        String statusString = "Compeleted";
        if (status == 0) {
            statusString = "Unread";
        }
        if (status == 1) {
            statusString = "In-progress";
        }
        return "Title: " + getTitle() + ", By: " + getAuthor()
                + ". Genre: " + getGenre() + ", Status: " + statusString;
    }

    //REQUIRES: status is either 0, 1, or 2
    //MODIFIES: this
    //EFFECTS: changes status of book to either read, in-progress, or unread
    public void changeStatus(int status) {
        this.status = status;
        eventLog.logEvent(new Event("Changed status of: " + viewBook()));
    }

    @Override
    public String toString() {
        return title;
    }

    public JSONObject toJson() {
        JSONObject jsObj = new JSONObject();
        jsObj.put("title", title);
        jsObj.put("author", author);
        jsObj.put("genre", genre);
        jsObj.put("status", status);

        return  jsObj;
    }



}
