package markdown.parser;

import markdown.nodes.*;

import java.io.File;
import java.util.List;
import java.util.ListIterator;

public class MarkdownParser {
    // parser:
    public static MdDoc parse(String content) {
        List<MarkdownToken> tokens = new MarkdownLexer(false).tokenize(content);
        return parse(null, tokens.listIterator());
    }

    public static MdDoc parse(File filePath, ListIterator<MarkdownToken> it) {
        MdDoc doc = new MdDoc(filePath);
        try {
            while (it.hasNext()) {
                MdNode node;
                if ((node = tryParseParagraph(it)) != null) {
                    doc.addChild(node);
                } else if ((node = tryParseHeading(it)) != null) {
                    doc.addChild(node);
                } else {
                    throw new MarkdownParseException("Invalid element " + node, doc, null);
                }
            }
        } catch( MarkdownParseException e ) {
            e.printStackTrace();
        }

        return doc;
    }


    private static MdHeading tryParseHeading(ListIterator<MarkdownToken> it) throws MarkdownParseException {
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
            if ((node = tryParseText(it)) != null) {
                heading.addChild(node);
            } else {
                break;
            }
        }
        if (node == null) {
            // Markdown Syntax error
            throw new MarkdownParseException("Heading is invalid!", heading, it.next());
        }

        // check for end-token MarkdownTokenType.CRLF
        if (it.hasNext()) {
            token = it.next();
            if (token.getType() != MarkdownTokenType.CRLF) {
                throw new MarkdownParseException("Heading is invalid!", heading, token);
            }
        }

        return heading;
    }

    private static MdParagraph tryParseParagraph(ListIterator<MarkdownToken> it) {
        if (!it.hasNext())
            return null;

        MdParagraph paragraph = new MdParagraph();

        // add child-tokens
        MdNode node;
        while (it.hasNext()) {
            if ((node = tryParseText(it)) != null) {
                paragraph.addChild(node);
            } else {
                MarkdownToken token = it.next();
                if (token.getType() == MarkdownTokenType.CRLF) {
                    if (it.hasNext()) {
                        token = it.next();
                        if (token.getType() == MarkdownTokenType.CRLF) {
                            // double CRLF: end of paragraph
                            break;
                        } else
                            it.previous();
                    }
                } else if (token.getType() == MarkdownTokenType.H) {
                    // end of paragraph
                    it.previous();
                    if (paragraph.isLeaf()) {
                        // no children!
                        return null;
                    }
                    break;
                }
            }
        }

        return paragraph;
    }

    private static MdText tryParseText(ListIterator<MarkdownToken> it) {
        if( !it.hasNext() )
            return null;

        MarkdownToken token = it.next();
        if (token.getType()!=MarkdownTokenType.T) {
            // not a text-token
            it.previous();
            return null;
        }
        return new MdText(token.getValue());
    }

}
