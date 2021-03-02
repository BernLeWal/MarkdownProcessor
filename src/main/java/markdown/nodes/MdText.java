package markdown.nodes;

import markdown.interpreter.Visitor;
import markdown.parser.MarkdownToken;
import markdown.parser.MarkdownTokenType;

import java.util.ListIterator;

public class MdText extends MdNode {
    public MdText(String value) {
        super(value);
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visitText(this);
    }

    @Override
    public String toString() {
        return "MdText{" +
                "value='" + getValue() + '\'' +
                '}';
    }
}
