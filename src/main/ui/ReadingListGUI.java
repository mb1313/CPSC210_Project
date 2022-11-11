package ui;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SCREENPeer;
import model.Book;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.management.RuntimeErrorException;
import javax.swing.*;
import javax.swing.event.*;

//Reading List GUI Application
//SOURCES: framework for buttons, panels, and some indicated methods taken from:
// https://docs.oracle.com/javase/tutorial/uiswing/examples/zipfiles/components-ListDemoProject.zip
public class ReadingListGUI extends JPanel {

    private JList unread;
    private JList reading;
    private JList complete;
    private DefaultListModel unreadModel;
    private DefaultListModel readingModel;
    private DefaultListModel completeModel;
    private static JFrame frame;

    private static final String addString = "Add Book";
    private static final String deleteString = "Delete Book";
    private static final String editString = "Edit Book";
    private static final String viewString = "View Book";
    private static final String saveString = "Save Lists";
    private static final String loadString = "Load Lists";
    private int duration = 5;

    private static final String iconLocation = "./data/book.jpg";

    private JButton deleteButton;

    //MODIFIES: this
    //EFFECTS: creates all lists, panels, buttons for UI
    public ReadingListGUI() {
        super(new GridBagLayout());

        unreadModel = new DefaultListModel();
        readingModel = new DefaultListModel();
        completeModel = new DefaultListModel();
        unread = new JList(unreadModel);
        reading = new JList(readingModel);
        complete = new JList(completeModel);
        createListGUI();
    }

    //MODIFIES: this
    //EFFECTS: Creates list panes, buttons, action listeners
    //         Adds all buttons and lists to gridbaglayout
    //SOURCES: setting up buttons and panes taken from ListDemo
    @SuppressWarnings("methodlength")
    public void createListGUI() {
        ListSelectionListener listListener = new ListSelectionListener();
        JScrollPane completePane = new JScrollPane(complete);
        JScrollPane unreadPane = new JScrollPane(unread);
        JScrollPane readingPane = new JScrollPane(reading);

        listSetup(listListener);


        JButton addButton = new JButton(addString);
        BookListener bookListener = new AddBookListener(this);
        addButton.setActionCommand(addString);
        addButton.addActionListener(bookListener);

        JButton editButton = new JButton(editString);
        editButton.setActionCommand(editString);
        BookListener editListener = new EditBookListener(this);
        editButton.addActionListener(editListener);

        deleteButton = new JButton(deleteString);
        deleteButton.setActionCommand(deleteString);
        deleteButton.addActionListener(new DeleteBookListener(this));

        JButton viewButton = new JButton(viewString);
        viewButton.setActionCommand(viewString);
        viewButton.addActionListener(new ViewBookListener(this));

        JButton saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveBookListener(this));

        JButton loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadBookListener(this));


        JPanel buttonPane = getjPanel(addButton, editButton, viewButton, saveButton, loadButton);

        GridBagConstraints gcnt = new GridBagConstraints();
        gcnt.fill = GridBagConstraints.HORIZONTAL;
        gbagConstraints(unreadPane, readingPane, completePane, buttonPane, gcnt);
    }

    //MODIFIES: this
    //EFFECTS: sets listListeners and default settings for lists
    //SOURCES: list setups taken from ListDemo
    private void listSetup(ListSelectionListener listListener) {
        unread.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        unread.setSelectedIndex(0);
        unread.addListSelectionListener(listListener);
        unread.setVisibleRowCount(10);

        reading.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reading.setSelectedIndex(0);
        reading.addListSelectionListener(listListener);
        reading.setVisibleRowCount(10);

        complete.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        complete.setSelectedIndex(0);
        complete.addListSelectionListener(listListener);
        complete.setVisibleRowCount(10);
    }

    //EFFECTS: places labels, lists, and buttons in the grid bag layout
    @SuppressWarnings("methodlength")
    private void gbagConstraints(JScrollPane unreadPane, JScrollPane readingPane,
                           JScrollPane completePane, JPanel buttonPane, GridBagConstraints gcnt) {
        Label unreadLabel = new Label("Unread Books");
        Label readingLabel = new Label("In-progress Books");
        Label completeLabel = new Label("Complete Books");

        gcnt.gridx = 0;
        gcnt.gridy = 0;
        add(unreadLabel, gcnt);
        gcnt.gridx = 1;
        gcnt.gridy = 0;
        add(readingLabel, gcnt);
        gcnt.gridx = 2;
        gcnt.gridy = 0;
        add(completeLabel, gcnt);

        gcnt.gridx = 0;
        gcnt.gridy = 1;
        add(unreadPane, gcnt);
        gcnt.gridx = 1;
        gcnt.gridy = 1;
        add(readingPane, gcnt);
        gcnt.gridx = 2;
        gcnt.gridy = 1;
        add(completePane, gcnt);
        gcnt.gridx = 1;
        gcnt.gridy = 2;
        add(buttonPane, gcnt);
        gcnt.gridx = 2;
        gcnt.gridy = 2;
        try {
            BufferedImage myPicture = ImageIO.read(new File(iconLocation));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture.getScaledInstance(100, 100, 1)));
            add(picLabel, gcnt);
        } catch (IOException e) {
            //nothing
        }
    }

    //EFFECTS: Creates a panel of every button to be used in app
    //SOURCES: Button panel setup based on ListDemo
    private JPanel getjPanel(JButton addButton, JButton editButton,
                             JButton viewButton, JButton saveButton, JButton loadButton) {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridLayout(3, 2));
        buttonPane.add(addButton);
        buttonPane.add(deleteButton);
        buttonPane.add(viewButton);
        buttonPane.add(editButton);
        buttonPane.add(saveButton);
        buttonPane.add(loadButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        return buttonPane;
    }

    //MODIFIES: this
    //EFFECTS: adds new book to unread book list
    public void addBook(Book b1) {
        unreadModel.addElement(b1);
    }

    //EFFECTS: returns the selected list in app
    public JList getSelectedList() {
        if (unread.getSelectedIndex() >= 0) {
            return unread;
        } else if (reading.getSelectedIndex() >= 0) {
            return reading;
        } else {
            return complete;
        }
    }

    //CONSTRAINTS: 0 <= status <= 2
    //EFFECTS: returns the defaultListModel corresponding to the input status
    public DefaultListModel getListModel(int status) {
        if (status == 0) {
            return unreadModel;
        } else if (status == 1) {
            return readingModel;
        } else {
            return completeModel;
        }
    }

    //MODIFIES: this
    //EFFECTS: sets up the frame and makes it visible
    //SOURCES: code for setting up and displaying JFrame taken from ListDemo
    public static void createAndShowGUI() {
        //Create and set up the window.
        frame = new AlteredFrame("Reading List");
        frame.setLocation(500,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ReadingListGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    static class AlteredFrame extends JFrame {

        public AlteredFrame(String name) {
            super(name);
        }

        protected void processWindowEvent(WindowEvent e) {
            if (e.getID() == WindowEvent.WINDOW_CLOSING) {
                for (Event ev : EventLog.getInstance()) {
                    System.out.println(ev);
                }
                System.exit(0);
            }
        }
    }

    //Class to react to list interaction
    class ListSelectionListener implements javax.swing.event.ListSelectionListener {

        //MODIFIES: this
        //EFFECTS: deselects lists other than the list just clicked on
        public void valueChanged(ListSelectionEvent e) {
            if (unread != e.getSource()) {
                unread.clearSelection();
            }
            if (reading != e.getSource()) {
                reading.clearSelection();
            }
            if (complete != e.getSource()) {
                complete.clearSelection();
            }
        }
    }
}
