import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Section {
    String title;
    List<Paragraph> paragraps = new ArrayList<>() ;

    Section(String title) {
        this.title = title;
    }
    Section setTitle(String title){
        this.title = title;
        return this;
    }
    Section addParagraph(String paragraphText){
        paragraps.add(new Paragraph(paragraphText));
        return this;
    }
    Section addParagraph(Paragraph p){
        paragraps.add(p);
        return this;
    }
    void writeHTML(PrintStream out){
        out.printf("<section>\n\t%s<h2>%s</h2>\n", "\t\t", title);
        for(Paragraph paragraph : paragraps) {
            out.print("\t\t" + "\t");
            paragraph.writeHTML(out);
        }
        out.print("\t\t" + "</section>\n");
    }
}

