package cv;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Section {

    String title;
    List<Paragraph> paragraphs = new ArrayList<>();

    Section setTitle (String title){
        this.title = title;
        return this;
    }

    Section addParagraph(String paragraphText){
        this.paragraphs.add(new Paragraph().setContent(paragraphText));
        return this;
    }

    Section addParagraph(Paragraph p){
        this.paragraphs.add(p);
        return this;
    }

    void writeHTML(PrintStream out){
        out.printf("<h2>%s</h2>", title);
        for (Paragraph p : paragraphs){
            p.writeHTML(out);
        }
    }
}
