package markdown.nodes;

import markdown.interpreter.Visitor;

public class MdHtml extends MdNode {
    public MdHtml(String value) { super(value); }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitHtml(this);
    }

    @Override
    public String toString() {
        return "MdHtml{" +
                "value='" + getValue() + '\'' +
                "}";
    }
}
