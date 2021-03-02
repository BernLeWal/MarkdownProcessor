package markdown.nodes;

import markdown.parser.MarkdownToken;

public class MarkdownSyntaxError extends RuntimeException {
    private String message;
    private MdNode node;
    private MarkdownToken token;

    public MarkdownSyntaxError(String message, MdNode currentNode, MarkdownToken currentToken) {
        super("Syntax error: \"" + message + "\" at node=" + currentNode + ", token=" + currentToken);

        node = currentNode;
        token = currentToken;
    }
}
