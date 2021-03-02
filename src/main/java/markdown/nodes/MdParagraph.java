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

    // parser:
    public static MdParagraph parse(ListIterator<MarkdownToken> it) {
        if (!it.hasNext())
            return null;

        MdParagraph paragraph = new MdParagraph();

        // add child-tokens
        MdNode node;
        while (it.hasNext()) {
            if ((node = MdText.parse(it)) != null) {
                paragraph.addChild(node);
            } else {
                MarkdownToken token = it.next();
                if (token.getType() == MarkdownTokenType.CRLF) {
                    if (it.hasNext()) {
                        token = it.next();
                        if (token.getType() == MarkdownTokenType.CRLF) {
                            // double CRLF: end of paragraph
                            break;
                        } else
                            it.previous();
                    }
                } else if (token.getType() == MarkdownTokenType.H) {
                    // end of paragraph
                    it.previous();
                    if (paragraph.isLeaf()) {
                        // no children!
                        return null;
                    }
                    break;
                }
            }
        }

        return paragraph;
    }
}
