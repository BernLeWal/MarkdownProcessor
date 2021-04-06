package markdown.nodes;

import markdown.interpreter.Visitor;

public class MdEmphasis extends MdNode {
    public static enum Kind {
        BOLD,
        ITALIC,
        BOLD_ITALIC
    }

    private final Kind kind;

    public MdEmphasis(String emKind) {
        setValue(emKind);
        kind = switch (emKind ) {
            case "_", "*" -> Kind.ITALIC;
            case "__", "**", "_*", "*_" -> Kind.BOLD;
            default -> Kind.BOLD_ITALIC;
        };
    }

    public Kind getKind() {
        return kind;
    }

    public String getValueReverse() {
        return new StringBuilder(getValue()).reverse().toString();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitEmphasis(this);
    }
}
