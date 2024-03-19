import java.io.PrintStream;
public class Paragraph {
    String content;
    Paragraph() {};

    Paragraph(String content) {
        this.content = content;
    }

    Paragraph setContent(String newContent) {
        this.content = newContent;
        return this;
    }

    void writeHTML(PrintStream out) {
        out.printf("<p>%s</p>\n", content);
    }
}
