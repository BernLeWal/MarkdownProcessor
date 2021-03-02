package markdown.nodes;

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

    @Override
    public String toString() {
        return "MdHeading{" +
                "level=" + level +
                '}';
    }
}
