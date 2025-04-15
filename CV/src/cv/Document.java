package cv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Document {

    String title;
    Photo photo;
    List<Section> sections = new ArrayList<>();

    Document(){};

    Document(String title){
        this.title = title;
    }

    Document setTitle(String title){
        this.title = title;
        return this;
    }

    Document setPhoto(String photoUrl){
        this.photo = new Photo(photoUrl);
        return this;
    }

    Section addSection(String sectionTitle){
        Section s = new Section();
        s.setTitle(sectionTitle);
        sections.add(s);
        return s;
    }

    Document addSection(Section s){
        sections.add(s);
        return this;
    }

    void writeHTML(PrintStream out){
        out.println("<html><body>");
        out.printf("<h1>%s</h1>", title);
        this.photo.writeHTML(out);
        for(Section s: sections){
            s.writeHTML(out);
        }
        out.println("</html></body>");
    }

    public String toJson(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    Document fromJson(String jsonString){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, Document.class);
    }


}
