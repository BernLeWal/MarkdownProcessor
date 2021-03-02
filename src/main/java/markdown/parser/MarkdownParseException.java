package markdown.parser;

import markdown.nodes.MdNode;

public class MarkdownParseException extends Exception {
    private final String message;
    private final MdNode node;
    private final MarkdownToken token;

    public MarkdownParseException(String message, MdNode currentNode, MarkdownToken currentToken) {
        super("Syntax error: \"" + message + "\" at node=" + currentNode + ", token=" + currentToken);

        this.message = message;
        this.node = currentNode;
        this.token = currentToken;
    }
}
