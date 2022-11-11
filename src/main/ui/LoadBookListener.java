package ui;

import model.Book;
import model.BookList;
import persistence.JsonReader;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

//Listener class for Load Button
public class LoadBookListener extends BookListener {

    List<BookList> blists;
    private JsonReader jsReader;
    private static final String JSON_SAVE = "./data/readingApp.json";

    //MODIFIES: this
    //EFFECTS: reads save file and parses lists
    public LoadBookListener(ReadingListGUI rlg) {
        super(rlg);
        jsReader = new JsonReader(JSON_SAVE);
    }

    //MODIFIES: rlg
    //EFFECTS: writes the lists from save file to lists in gui
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            blists = jsReader.read();
        } catch (IOException ex) {
            System.out.println("Unable to read file " + JSON_SAVE);
        }

        for (int i = 0; i < 3; i++) {
            BookList bl1 = blists.get(i);
            listModel = rlg.getListModel(i);
            listModel.removeAllElements();
            for (int j = 0; j < bl1.length(); j++) {
                Book b1 = bl1.getBook(j);
                listModel.addElement(b1);
            }
        }

        System.out.println("Loaded reading lists from " + JSON_SAVE);
    }
}
