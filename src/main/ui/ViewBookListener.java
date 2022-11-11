package ui;

import model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Listener for View Book button
public class ViewBookListener extends BookListener implements ActionListener {

    public ViewBookListener(ReadingListGUI rlg) {
        super(rlg);
    }

    //EFFECTS: Shows info of selected book
    @Override
    public void actionPerformed(ActionEvent e) {
        makeLists();

        if (list.getSelectedIndex() != -1) {
            ImageIcon icon = new ImageIcon("./data/book.jpg");
            Image iconImage  = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            icon = new ImageIcon(iconImage);
            Book b1 = (Book) listModel.getElementAt(list.getSelectedIndex());
            JOptionPane.showMessageDialog(null, "Title: " + b1.getTitle()
                    + "\n" + "Author: " + b1.getAuthor() + "\n"
                    + "Genre: " + b1.getGenre(), "Book: " + b1.getTitle(), JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }
}
