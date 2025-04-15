package cv;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) {
        Document cv = new Document("Jana Kowalski - CV");
        cv.setPhoto("https://upload.wikimedia.org/wikipedia/commons/thumb/7/71/Calico_tabby_cat_-_Savannah.jpg/1200px-Calico_tabby_cat_-_Savannah.jpg");
        cv.addSection("Wykształcenie")
                .addParagraph("2000-2005 Przedszkole im. Królewny Snieżki w ...")
                .addParagraph("2006-2012 SP7 im Ronalda Regana w ...")
                .addParagraph(
                        new ParagraphWithList().setContent("Kursy")
                                .addListItem("Języka Angielskiego")
                                .addListItem("Języka Hiszpańskiego")
                                .addListItem("Szydełkowania")
                );
        cv.addSection("Umiejętności")
                .addParagraph(
                        new ParagraphWithList().setContent("Znane technologie")
                                .addListItem("C")
                                .addListItem("C++")
                                .addListItem("Java")
                );


//        wyświetlenie cv w wersji HTML
        cv.writeHTML(System.out);


//        zapisanie cv do pliku w wersji HTML
        try {
            cv.writeHTML(new PrintStream("cv.html", "UTF-8"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }


//        zapisanie cv do pliku w wersji JSON
        try {
            PrintWriter writer = new PrintWriter("cv.json", "UTF-8");
            writer.write(cv.toJson());
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

//        odczytanie cv z pliku oraz konwersja do objektu Document
        Document cv2 = new Document().fromJson(cv.toJson());

//        zapisanie cv do pliku w wersji HTML
        try {
            cv2.writeHTML(new PrintStream("cv_from_json.html", "UTF-8"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }

    }


}