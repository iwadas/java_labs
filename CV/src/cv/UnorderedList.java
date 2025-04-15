package cv;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class UnorderedList {

    List<ListItem> listItems = new ArrayList<>();

    public UnorderedList addItem(String itemListContent){
        ListItem li = new ListItem(itemListContent);
        listItems.add(li);
        return this;
    }

    public void writeHTML(PrintStream out){
        out.println("<ul>");
        for(ListItem li: listItems){
            li.writeHTML(out);
        }
        out.println("</ul>");
    }


}
