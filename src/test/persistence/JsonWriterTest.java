package persistence;

import model.Book;
import model.BookList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

//Tests and code structure taken from: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonWriterTest.java

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalid() {
        try {
            List<BookList> bls = new LinkedList<BookList>();
            JsonWriter writer = new JsonWriter("./data/\0invalidFile.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            //success
        }
    }

    @Test
    void testWriterEmptyReadingList() {
        try {
            List<BookList> emptyBL = new LinkedList<BookList>();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRL.json");
            writer.open();
            writer.write(emptyBL);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRL.json");
            emptyBL = reader.read();
            assertEquals(0, emptyBL.size());
        } catch (IOException e) {
            fail("Should not have caught exception");
        }
    }

    @Test
    void testWriterGeneralReadingList() {
        try {
            List<BookList> generalList = new LinkedList<BookList>();

            for (int i = 0; i < 3; i++) {
                BookList bl = new BookList();
                Book b1 = new Book("LotR", "Tolkien", "Fantasy", i);
                bl.addBook(b1);
                generalList.add(bl);
            }

            JsonWriter writer = new JsonWriter("./data/testWriterGeneral.json");
            writer.open();
            writer.write(generalList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneral.json");
            generalList = reader.read();
            assertEquals(3, generalList.size());
            checkBook("LotR", "Tolkien", "Fantasy", 0, generalList.get(0).getBook(0));
            checkBook("LotR", "Tolkien", "Fantasy", 1, generalList.get(1).getBook(0));
            checkBook("LotR", "Tolkien", "Fantasy", 2, generalList.get(2).getBook(0));
        } catch (IOException e) {
            fail("No exception should be thrown");
        }
    }
}
