package markdown.nodes;

import markdown.interpreter.Visitor;

public class MdBlockQuote extends MdNode {
    public MdBlockQuote(String value) {
        super(value);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitBlockQuote(this);
    }
}
