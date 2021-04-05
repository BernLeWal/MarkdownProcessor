package markdown.nodes;

import markdown.interpreter.Visitor;

public class MdBreak extends MdNode {
    @Override
    public void accept(Visitor visitor) {
        visitor.visitBreak(this);
    }

    @Override
    public String toString() {
        return "MdBreak{}";
    }
}
