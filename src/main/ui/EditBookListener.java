package ui;

import model.Book;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Listener for edit button
public class EditBookListener extends BookListener implements ActionListener {

    public EditBookListener(ReadingListGUI rlg) {
        super(rlg);
    }

    //MODIFIES: this, rlg, Book b1
    //EFFECTS: prompts user to choose new status of selected book, moves said book from current list to selected list
    @Override
    public void actionPerformed(ActionEvent e) {
        makeLists();

        if (list.getSelectedIndex() != -1) {
            int index = list.getSelectedIndex();
            Book b1 = (Book) listModel.get(index);
            Object[] options = {"Unread", "In-progress", "Completed"};
            Object selectedValue = JOptionPane.showInputDialog(null, "Choose one", "Change Book Status",
                    JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            int newList = 0;
            for (int i = 0; i < options.length; i++) {
                if (selectedValue == options[i]) {
                    newList = i;
                }
            }
            listModel.remove(index);
            listModel = rlg.getListModel(newList);
            b1.changeStatus(newList);
            listModel.addElement(b1);

        }
    }
}
