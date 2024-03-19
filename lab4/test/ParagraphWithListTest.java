import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class ParagraphWithListTest {
    @Test
    public void writeHTML() {
        String paragraphContent = "Kursy";
        // Utwórz strumień zapisujący w pamięci
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        // Utwórz obiekt i zapisz do strumienia
        new ParagraphWithList().setContent(paragraphContent)
                .addListItem("Języka Angielskiego")
                .addListItem("Języka Hiszpańskiego")
                .addListItem("Szydełkowania").writeHTML(ps);
        String result = null;
        // Pobierz jako String
        try {
            result = os.toString("ISO-8859-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//        System.out.println(result);

        assertTrue(result.contains("<li>"));
        assertTrue(result.contains("</li>"));
        assertTrue(result.contains("<ul>"));
        assertTrue(result.contains("</ul>"));
        assertTrue(result.contains("<p>"));
        assertTrue(result.contains("</p>"));
        assertTrue(result.contains(paragraphContent));
    }
}