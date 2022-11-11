package persistence;

import model.Book;
import model.BookList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

//Code for class taken from: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java
//with a few modifications for the context of this application

//Represents a reader that reads a reading list from JSON data stored in a file
public class JsonReader {

    private String file;

    public JsonReader(String file) {
        this.file = file;
    }

    //EFFECTS: reads a reading list from a file and returns it as a list of the three BookLists
    //throws IOException if error occurs while reading file
    public List<BookList> read() throws IOException {
        String jsonData = readFile(file);
        JSONObject jsonObj = new JSONObject(jsonData);
        return parseLists(jsonObj);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ReadingLists from JSON object and returns them
    private List<BookList> parseLists(JSONObject jsonObject) {
        JSONArray listArray = jsonObject.getJSONArray("lists");

        List<BookList> savedLists = new LinkedList<>();
        for (Object json : listArray) {
            JSONArray nextList = (JSONArray) json;
            savedLists.add(addList(nextList));
        }

        return savedLists;
    }

    // EFFECTS: parses next  Booklist from JSON array and returns it
    private BookList addList(JSONArray jsArray) {
        BookList currentList = new BookList();

        for (Object json: jsArray) {
            JSONObject nextBook = (JSONObject) json;
            currentList.addBook(parseBook(nextBook));
        }

        return currentList;
    }

    // EFFECTS: parses next book in Json Array and returns it as a Book object
    private Book parseBook(JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String author = jsonObject.getString("author");
        String genre = jsonObject.getString("genre");
        int status = jsonObject.getInt("status");

        return new Book(title, author, genre, status);
    }
}
