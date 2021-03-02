package markdown.nodes;

import markdown.parser.MarkdownToken;
import markdown.parser.MarkdownTokenType;

import java.util.ListIterator;

public class MdParagraph extends MdNode {
    public MdParagraph() {
    }

    public MdParagraph(String text) {
        addChild(new MdText(text));
    }
}
