package persistence;

import model.Book;
import static org.junit.jupiter.api.Assertions.assertEquals;

//Idea for class and method taken from: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonTest.java

public class JsonTest {
    protected void checkBook(String title, String author, String genre, int status, Book b1) {
        assertEquals(title, b1.getTitle());
        assertEquals(author, b1.getAuthor());
        assertEquals(genre, b1.getGenre());
        assertEquals(status, b1.getStatus());
    }
}
