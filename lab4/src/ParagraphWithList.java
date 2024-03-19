import java.io.PrintStream;

public class ParagraphWithList extends Paragraph{
    UnorderedList list = new UnorderedList();
    ParagraphWithList() {
        super();
    }
    ParagraphWithList(String content) {
        super(content);
    }
    @Override
    ParagraphWithList setContent(String newContent) {
        super.setContent(newContent);
        return this;
    }
    ParagraphWithList addListItem(String itemName) {
        list.addItem(new ListItem(itemName));
        return this;
    }

    void writeHTML(PrintStream out) {
        super.writeHTML(out);
        list.writeHTML(out);
    }
}
