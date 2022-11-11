package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//General class for all other listeners to extend
public abstract class BookListener implements ActionListener {

    protected ReadingListGUI rlg;
    protected JList list;
    protected DefaultListModel listModel;

    //Sets up relationship with the GUI
    public BookListener(ReadingListGUI rlg) {
        this.rlg = rlg;
    }

    //MODIFIES: this
    //EFFECTS: gets currently selected list and corresponding DefaultListModel
    public void makeLists() {
        list = rlg.getSelectedList();
        listModel = (DefaultListModel) list.getModel();
    }

    public abstract void actionPerformed(ActionEvent e);
}
