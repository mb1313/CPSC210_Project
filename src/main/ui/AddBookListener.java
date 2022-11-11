package ui;

import model.Book;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Listener for Add butotn
public class AddBookListener extends BookListener implements ActionListener {

    public AddBookListener(ReadingListGUI rlg) {
        super(rlg);
    }

    //MODIFIES: rlg
    //EFFECTS: Prompts user for book info and adds this new book to unread books in gui
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = JOptionPane.showInputDialog("Please enter the title of the book");
        String author = JOptionPane.showInputDialog("Please enter the author of the book");
        String genre = JOptionPane.showInputDialog("Please enter the genre of the book");
        Book b1 = new Book(name, author, genre);
        rlg.addBook(b1);
    }
}
