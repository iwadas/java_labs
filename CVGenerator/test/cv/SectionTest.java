package cv;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class SectionTest {

    @Test
    public void writeHTML(){
        String title = "Section Title";
        String paragraphText = "Paragraph Text";
        String paragraphWithListText = "Paragraph with List Text";
        String listItemContent = "List item content";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);

        new Section().setTitle(title)
                .addParagraph(new Paragraph().setContent(paragraphText))
                .addParagraph(
                        new ParagraphWithList().setContent(paragraphWithListText)
                                .addListItem(listItemContent)
                ).writeHTML(ps);

        String result = null;

        try{
            result = os.toString("ISO-8859-2");
        } catch (java.io.UnsupportedEncodingException e){
            e.printStackTrace();
        }

        assertTrue(result.contains("<h2>"));
        assertTrue(result.contains("<p>Paragraph Text</p>"));
        assertTrue(result.contains("<p>Paragraph with List Text</p>"));
        assertTrue(result.contains("<li>List item content</li>"));

    }


}