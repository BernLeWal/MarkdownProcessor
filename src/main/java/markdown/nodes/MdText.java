package markdown.nodes;

import markdown.parser.MarkdownToken;
import markdown.parser.MarkdownTokenType;

import java.util.ListIterator;

public class MdText extends MdNode {
    public MdText(String value) {
        super(value);
    }

    @Override
    public String toString() {
        return "MdText{" +
                "value='" + getValue() + '\'' +
                '}';
    }
}
