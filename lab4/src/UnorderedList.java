import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class UnorderedList {
    List<ListItem> items = new ArrayList<>();

    void addItem(ListItem item) {
        items.add(item);
    }
    void writeHTML(PrintStream out) {
        out.print("\t\t\t<ul>\n");
        for(ListItem item : items) {
            out.print("\t\t\t\t");
            item.writeHTML(out);
        }
        out.print("\t\t\t</ul>\n");
    }
}
