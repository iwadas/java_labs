package cv;

import java.io.PrintStream;

public class ListItem {

    String content;

    public ListItem(String content){
        this.content = content;
    }

    public void writeHTML(PrintStream out){
        out.printf("<li>%s</li>", content);
    }

}
