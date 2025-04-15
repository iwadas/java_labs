package cv;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class UnorderedListTest {

    @org.junit.Test
    public void writeHTML() throws Exception {

        String element1 = "element1";
        String element2 = "element2";

        // Utwórz strumień zapisujący w pamięci
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);

        // Utwórz obiekt i zapisz do strumienia
        new UnorderedList().addItem(element1).addItem(element2).writeHTML(ps);

        String result = null;
        // Pobierz jako String
        try {
            result = os.toString("ISO-8859-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Sprawdź, czy result zawiera wybrane elementy
        assertTrue(result.contains("<ul>"));
        assertTrue(result.contains("</ul>"));
        assertTrue(result.contains("<li>"));
        assertTrue(result.contains("</li>"));
        assertTrue(result.contains("element1"));
    }

}