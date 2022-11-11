package persistence;

import model.Book;
import model.BookList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

//Test ideas and structure taken from: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonReaderTest.java

public class JsonReaderTest extends JsonTest {

    @Test
    void testReadInvalidFile() {
        JsonReader reader = new JsonReader("./data/nonexistentFile.json");
        try {
            List<BookList> readingApp = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //expected
        }
    }

    @Test
    void readEmptyReadingList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyReadingList.json");
        try {
            List<BookList> readingApp = reader.read();
            assertEquals(3, readingApp.size());
            assertEquals(0, readingApp.get(0).length());
        } catch (IOException e) {
            fail("Couldn't read file");
        }
    }

    @Test
    void readGeneralList() {
        JsonReader reader = new JsonReader("./data/testGeneralLists.json");
        try {
            List<BookList> readingApp = reader.read();
            BookList unread = readingApp.get(0);
            assertEquals(2, unread.length());
            checkBook("LotR", "Tolkien", "Fantasy", 0, unread.getBook(0));
            checkBook("Dune", "Herbert", "Sci-fi", 0, unread.getBook(1));

        } catch (IOException e) {
            fail("Couldn't read file");
        }
    }

}
