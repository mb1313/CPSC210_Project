package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;

import java.util.LinkedList;

//represents a list of books that can be modified
public class BookList {

    private LinkedList<Book> booklist;
    private EventLog eventLog;

    //MODIFIES: this
    //EFFECTS: creates a new booklist
    public BookList() {
        booklist = new LinkedList<>();
        eventLog = EventLog.getInstance();
    }

    //MODIFIES: this
    //EFFECTS: adds another book to the current booklist
    public void addBook(Book b1) {
        booklist.add(b1);
    }

    //MODIFIES: this
    //EFFECTS: removes the book from the booklist
    public void deleteBook(Book b1) {
        eventLog.logEvent(new Event(b1.getTitle() + " deleted from list " + b1.getStatus()));
        booklist.remove(b1);
    }

    //EFFECTS: returns the length of the booklist
    public int length() {
        return booklist.size();
    }

    //REQUIRES: index is in scope of list
    //EFFECTS: returns the book located at index of booklist
    public Book getBook(int index) {
        return booklist.get(index);
    }

    //EFFECTS: prints out title, author, genre of each book in the list with its index in the list
    public String viewBooks() {
        String output = "";
        int count = 1;
        for (Book b1 : booklist) {
            output += "\n\t" + count + ": " + b1.viewBook();
            count++;
        }
        return output;
    }

    //EFFECTS: returns books in booklist as JSON array
    //code for method taken from: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/model/WorkRoom.java
    public JSONArray toJson() {
        JSONArray jsArray = new JSONArray();

        for (Book b : booklist) {
            jsArray.put(b.toJson());
        }

        return jsArray;
    }
}
