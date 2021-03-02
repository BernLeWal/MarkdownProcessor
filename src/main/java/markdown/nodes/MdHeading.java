package markdown.nodes;

import markdown.interpreter.Visitor;
import markdown.parser.MarkdownToken;
import markdown.parser.MarkdownTokenType;

import java.util.ListIterator;

public class MdHeading extends MdNode {
    private int level;

    public MdHeading(int level) {
        this.level = level;
    }

    public MdHeading(int level, String text) {
        this.level = level;
        addChild(new MdText(text));
    }


    public int getLevel() {
        return level;
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visitHeading(this);
    }

    @Override
    public String toString() {
        return "MdHeading{" +
                "level=" + level +
                '}';
    }
}
