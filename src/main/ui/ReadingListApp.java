package ui;

import model.Book;
import model.BookList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

//SOME METHODS TAKEN FROM: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/ui/WorkRoomApp.java
//runApp(), init(), displayOptions(), and processCommand() taken from: https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
//methods are indicated with a comment before the declaration

//Reading List application
public class ReadingListApp {
    private BookList unreadBooks;
    private BookList inProgressBooks;
    private BookList completedBooks;
    private List<BookList> lists = new LinkedList<>();

    private Scanner input;

    private static final String JSON_SAVE = "./data/readingApp.json";
    private JsonReader jsReader;
    private JsonWriter jsWriter;

    //EFFECTS: runs reading list app
    public ReadingListApp() {
        runApp();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    public void runApp() {
        boolean running = true;
        String command;

        init();

        while (running) {
            displayOptions();

            command = input.nextLine().toLowerCase();

            if (command.equals("q")) {
                running = false;
            } else {
                processCommand(command);
            }


        }

        System.out.println("Goodbye");
    }

    //MODIFIES: this
    //EFFECTS: initializes lists and scanner
    private void init() {
        unreadBooks = new BookList();
        inProgressBooks = new BookList();
        completedBooks = new BookList();
        input = new Scanner(System.in);
        lists.add(unreadBooks);
        lists.add(inProgressBooks);
        lists.add(completedBooks);

        jsReader = new JsonReader(JSON_SAVE);
        jsWriter = new JsonWriter(JSON_SAVE);
    }

    //EFFECTS: displays menu options
    private void displayOptions() {
        System.out.println("What would you like to do?");
        System.out.println("\ta -> ADD BOOK to list");
        System.out.println("\tv -> VIEW booklists");
        System.out.println("\tb -> VIEW a specific book");
        System.out.println("\te -> EDIT a book status or delete");
        System.out.println("\tl -> LOAD reading lists from save file");
        System.out.println("\ts -> SAVE reading lists to save file");
        System.out.println("\tq -> quit");
    }

    //MODIFIES: this
    //EFFECTS: processes user commands
    private void processCommand(String command) {
        if (command.equalsIgnoreCase("a")) {
            addBook();
        } else if (command.equalsIgnoreCase("v")) {
            viewLists();
        } else if (command.equalsIgnoreCase("b")) {
            viewBook();
        } else if (command.equalsIgnoreCase("e")) {
            editStatus();
        } else if (command.equalsIgnoreCase("l")) {
            loadReadingLists();
        } else if (command.equalsIgnoreCase("s")) {
            saveReadingLists();
        } else {
            System.out.println("Invalid command");
        }
    }

    //Method taken from gitHub repo linked at class declaration
    //EFFECTS: saves the reading lists to save file
    private void saveReadingLists() {
        try {
            jsWriter.open();
            jsWriter.write(lists);
            jsWriter.close();
            System.out.println("Saved Reading Lists to: " + JSON_SAVE);
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + JSON_SAVE);
        }
    }

    //Method structure taken from gitHub repo linked at class declaration
    // MODIFIES: this
    // EFFECTS: loads the three reading lists from save file
    private void loadReadingLists() {
        try {
            List<BookList> loadedLists = jsReader.read();
            unreadBooks = loadedLists.get(0);
            inProgressBooks = loadedLists.get(1);
            completedBooks = loadedLists.get(2);

            lists.set(0, unreadBooks);
            lists.set(1, inProgressBooks);
            lists.set(2, completedBooks);
            System.out.println("Loaded reading lists from " + JSON_SAVE);
        } catch (IOException e) {
            System.out.println("Unable to read file " + JSON_SAVE);
        }
    }

    //MODIFIES: this
    //EFFECTS: creates new book and adds it to unread list
    private void addBook() {
        String title;
        String author;
        String genre;

        System.out.println("Please enter the title of the book");
        title = input.nextLine();

        System.out.println("Please enter the author of the book");
        author = input.nextLine();

        System.out.println("Please enter the genre of the book");
        genre = input.nextLine();

        Book newBook = new Book(title, author, genre);
        unreadBooks.addBook(newBook);

        System.out.println("Updated list of Unread books: " + unreadBooks.viewBooks());

    }

    //MODIFIES: this
    //EFFECTS: prints out each reading list
    private void viewLists() {
        System.out.println("Unread Books: ");
        System.out.println(unreadBooks.viewBooks());

        System.out.println("In-progress Books: ");
        System.out.println(inProgressBooks.viewBooks());

        System.out.println("Completed Books:");
        System.out.println(completedBooks.viewBooks());
    }

    //EFFECTS: prints out lists and returns the book that the user chooses
    @SuppressWarnings("methodlength")
    private Book viewBook() {
        boolean viewBook = true;
        BookList listOfInterest = unreadBooks;
        viewLists();
        while (viewBook) {
            System.out.println("What list would you like to find a book from? 1 -> Unread Books, 2 -> In-progress,"
                    + "3 -> Completed Books");
            int listChoice = input.nextInt();
            if (listChoice > 3 || listChoice < 1) {
                System.out.println("Please enter a number 1, 2, or 3");
            } else {
                listOfInterest = lists.get(listChoice - 1);
                viewBook = false;
            }
        }
        System.out.println("What index book would you like to view?");
        int bookChoice = input.nextInt();
        input.nextLine();

        if (bookChoice > listOfInterest.length()) {
            System.out.println("Invalid index. Please try again.");
            return null;
        } else {
            System.out.println(listOfInterest.getBook(bookChoice - 1).viewBook());
            return listOfInterest.getBook(bookChoice - 1);
        }
    }

    //MODIFIES: this
    //EFFECTS: allows user to find a book and change the status. prints out lists afterwards
    private void editStatus() {
        Book b1 = viewBook();
        if (b1 == null) {
            return;
        }
        int ogList = b1.getStatus();
        System.out.println("What status would you like to set it to? 0 -> Delete book "
                + "1 -> Unread, 2 -> In-progress, 3 -> Completed");
        int status = input.nextInt() - 1;
        input.nextLine();
        if (status >= 0 && status < 3) {
            b1.changeStatus(status);
            lists.get(ogList).deleteBook(b1);
            lists.get(status).addBook(b1);

        } else if (status == -1) {
            lists.get(ogList).deleteBook(b1);
        } else {
            System.out.println("Please enter a number 1, 2, or 3");
        }

        viewLists();
    }


}
