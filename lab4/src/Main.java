import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

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

        cv.writeHTML(System.out); // wersja html dokumentu
        String jsonString = cv.toJson(); // serializacja dokumentu
        System.out.println();
        System.out.println(jsonString);
        Document fromJsonDoc = Document.fromJson(jsonString); // deserializacja
        System.out.println();
        fromJsonDoc.writeHTML(System.out);
        String secondSerializationString = fromJsonDoc.toJson(); // ponowna serializacja
        System.out.println();
        System.out.println(secondSerializationString);
//        System.out.println(jsonString.equals(secondSerializationString));

        try {
            PrintStream jsonFile = new PrintStream("cv.json", StandardCharsets.UTF_8);
            cv.writeHTML(new PrintStream("cv.html", StandardCharsets.UTF_8));
            jsonFile.print(jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
