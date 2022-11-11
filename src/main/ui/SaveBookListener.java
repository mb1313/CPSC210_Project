package ui;

import model.Book;
import model.BookList;
import persistence.JsonWriter;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

//Listener for Save button
public class SaveBookListener extends BookListener {

    private List<BookList> blists;
    private JsonWriter jsWriter;
    private static final String JSON_SAVE = "./data/readingApp.json";

    //MODIFIES: this
    //EFFECTS: opens jsWriter
    public SaveBookListener(ReadingListGUI rlg) {
        super(rlg);
        jsWriter = new JsonWriter(JSON_SAVE);
    }

    //MODIFIES: this, rlg
    //EFFECTS: takes lists in rlg and changes them to booklists, where they are then written to the save file
    @Override
    public void actionPerformed(ActionEvent e) {
        makeLists();
        blists = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            BookList bl = new BookList();
            listModel = rlg.getListModel(i);
            for (int j = 0; j < listModel.size(); j++) {
                bl.addBook((Book) listModel.get(j));
            }
            blists.add(bl);
        }
        try {
            jsWriter.open();
            jsWriter.write(blists);
            jsWriter.close();
            System.out.println("Saved Reading Lists to: " + JSON_SAVE);
        } catch (IOException exc) {
            System.out.println("Unable to write to file: " + JSON_SAVE);
        }

    }

}
