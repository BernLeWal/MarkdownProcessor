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


    // parser:
    public static MdNode parse(ListIterator<MarkdownToken> it) {
        if (!it.hasNext())
            return null;

        // check for start-token MarkdownTokenType.H
        MarkdownToken token = it.next();
        if (token.getType() != MarkdownTokenType.H) {
            // not a heading
            it.previous();
            return null;
        }
        MdHeading heading = new MdHeading(token.getValue().length());

        // add child-tokens
        MdNode node = null;
        while (it.hasNext()) {
            if ((node = MdText.parse(it)) != null) {
                heading.addChild(node);
            } else {
                break;
            }
        }
        if (node == null) {
            // Markdown Syntax error
            throw new MarkdownSyntaxError("Heading is invalid!", heading, it.next());
        }

        // check for end-token MarkdownTokenType.CRLF
        if (it.hasNext()) {
            token = it.next();
            if (token.getType() != MarkdownTokenType.CRLF) {
                throw new MarkdownSyntaxError("Heading is invalid!", heading, token);
            }
        }

        return heading;
    }
}
