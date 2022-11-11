package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    Book b1;
    BookList bl1;

    @BeforeEach
    void createBook() {
        b1 = new Book("Lord of the Rings", "J.R.R Tolkien", "Fantasy");
        bl1 = new BookList();
    }

    @Test
    void titleTest() {
        assertEquals("Lord of the Rings", b1.getTitle());
    }

    @Test
    void authorTest() {
        assertEquals("J.R.R Tolkien", b1.getAuthor());
    }

    @Test
    void genreTest() {
        assertEquals("Fantasy", b1.getGenre());
    }

    @Test
    void unreadTest() {
        assertEquals(0, b1.getStatus());
    }

    @Test
    void completedTest() {
        b1.changeStatus(2);
        assertEquals(2, b1.getStatus());
    }

    @Test
    void inProgressTest() {
        b1.changeStatus(1);
        assertEquals(1, b1.getStatus());
    }

    @Test
    void viewBookTest() {
        assertEquals(b1.viewBook(), "Title: Lord of the Rings, By: J.R.R Tolkien. "
                + "Genre: Fantasy, Status: Unread");
    }

    @Test
    void viewBookProgress() {
        b1.changeStatus(1);
        assertEquals(b1.viewBook(), "Title: Lord of the Rings, By: J.R.R Tolkien. "
                + "Genre: Fantasy, Status: In-progress");
    }

    @Test
    void testAdd() {
        bl1.addBook(b1);
        assertEquals(1, bl1.length());
        assertEquals(b1, bl1.getBook(0));
    }

    @Test
    void testDelete() {
        bl1.addBook(b1);
        assertEquals(1, bl1.length());
        bl1.deleteBook(b1);
        assertEquals(0, bl1.length());
    }

    @Test
    void testViewList() {
        bl1.addBook(b1);
        Book b2 = new Book("1984", "George Orwell", "Dystopian");
        bl1.addBook(b2);
        assertEquals(bl1.viewBooks(),
                "\n\t1: " + b1.viewBook()
                        + "\n\t2: " + b2.viewBook());
    }

    @Test
    void toStringTest() {
        assertEquals(b1.toString(), b1.getTitle());
    }

}