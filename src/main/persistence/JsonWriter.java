package persistence;

import model.BookList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

//Code for all methods copied or heavily implemented from: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonWriter.java

//Writes JSON representation of the reading lists to file
public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter pw;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot be written to
    public void open() throws FileNotFoundException {
        pw = new PrintWriter(new File(destination));
    }


    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        pw.close();
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of reading lists to destination file
    public void write(List<BookList> blists) {
        JSONObject json = new JSONObject();
        JSONArray lists = new JSONArray();
        for (BookList bl : blists) {
            lists.put(bl.toJson());
        }

        json.put("lists", lists);

        saveToFile(json.toString(TAB));
    }


    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        pw.print(json);
    }
}
