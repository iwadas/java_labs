package cv;

import java.io.PrintStream;

public class ParagraphWithList extends Paragraph {

    UnorderedList ul = new UnorderedList();

    public ParagraphWithList setContent(String content){
        super.setContent(content);
        return this;
    }

    public ParagraphWithList addListItem(String itemListContent){
        ul.addItem(itemListContent);
        return this;
    }

    public void writeHTML(PrintStream out){
        super.writeHTML(out);
        ul.writeHTML(out);
    }
}
