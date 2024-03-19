import java.io.PrintStream;

public class ListItem {
    String content;

    ListItem(String name) {
        this.content = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    void writeHTML(PrintStream out) {
        out.printf("<li>%s</li>\n", content);
    }
}
