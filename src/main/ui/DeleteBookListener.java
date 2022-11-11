package ui;

import model.Book;
import model.BookList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Listener for delete button
public class DeleteBookListener extends BookListener implements ActionListener {

    public DeleteBookListener(ReadingListGUI rlg) {
        super(rlg);
    }

    //MODIFIES: rlg
    //EFFECTS: if book is selected, removes selected book from the application
    @Override
    public void actionPerformed(ActionEvent e) {
        makeLists();

        if (list.getSelectedIndex() != -1) {
            int index = list.getSelectedIndex();
            new BookList().deleteBook((Book)listModel.get(index));
            listModel.remove(index);
        }


    }
}
