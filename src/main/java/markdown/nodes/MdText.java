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

    // parser:
    public static MdNode parse(ListIterator<MarkdownToken> it) {
        if( !it.hasNext() )
            return null;

        MarkdownToken token = it.next();
        if (token.getType()!=MarkdownTokenType.T) {
            // not a text-token
            it.previous();
            return null;
        }
        return new MdText(token.getValue());
    }
}
