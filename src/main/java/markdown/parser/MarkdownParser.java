package markdown.parser;

import markdown.nodes.*;

import java.io.File;
import java.util.List;
import java.util.ListIterator;

/**
 * The MarkdownParser parses the flat token-stream to build up a hierarchy of MdNodes.
 * <p>
 * The grammar rules are shown for documentation at the tryParse(...)-Methods using the BNF notation
 * BNF see https://en.wikipedia.org/wiki/Backus%E2%80%93Naur_form
 */
public class MarkdownParser {
    // parser:
    public static MdDoc parse(String content) {
        List<MarkdownToken> tokens = new MarkdownLexer(false).tokenize(content);
        return parse(null, tokens.listIterator());
    }


    /**
     * Parse the MdDoc root-node out of a token-stream where the iterator starts at the begin.
     *
     * @param filePath optional; a filePath to an .md-file where the initial markdown was stored
     * @param it       the token-stream; it should point to the very head element
     * @return the root-node of the markdown-hierarchy; on error it returns null
     * <p>
     * Rule (BNF):
     * document ::= {paragraph | heading}*
     */
    public static MdDoc parse(File filePath, ListIterator<MarkdownToken> it) {
        MdDoc doc = new MdDoc(filePath);
        try {
            while (it.hasNext()) {
                MdNode node;
                if ((node = tryParseParagraph(it, null)) != null) {
                    doc.addChild(node);
                } else if ((node = tryParseHeading(it, null)) != null) {
                    doc.addChild(node);
                } else if ((node = tryParseHtml(it)) != null) {
                    doc.addChild(node);
                } else if ((node = tryParseBlockQuite(it, null)) != null) {
                    doc.addChild(node);
                } else {
                    throw new MarkdownParseException("Invalid token!", doc, it.next());
                }
            }
        } catch (MarkdownParseException e) {
            e.printStackTrace();
        }

        return doc;
    }

    /**
     * Try to parse an html-tag
     *
     * @param it
     * @return
     */
    private static MdNode tryParseHtml(ListIterator<MarkdownToken> it) {
        if (!it.hasNext())
            return null;

        MarkdownToken token;
        if ((token = readToken(MarkdownTokenType.HTML, it)) == null)
            return null;

        String value = token.getValue().toLowerCase();
        if (value.equals("<br>") || value.equals("<br/>"))
            return new MdBreak();
        else
            return new MdHtml(token.getValue());
    }


    /**
     * Try to parse an heading element
     *
     * @param it
     * @return
     * @throws MarkdownParseException Rule (BNF):
     *                                heading ::= "#" {"#"}* {text}* crlf
     */
    private static MdHeading tryParseHeading(ListIterator<MarkdownToken> it, String checkBlockQuote) {
        if (!it.hasNext())
            return null;

        // check for start-token MarkdownTokenType.H
        MarkdownToken token;
        if ((token = readToken(MarkdownTokenType.H, it)) == null)
            return null;
        if (checkBlockQuote != null) {
            if ((token = readToken(MarkdownTokenType.QUOTE, it)) == null)
                return null;    // not a blockquote
            else if (!token.getValue().equals(checkBlockQuote)) {
                it.previous();
                return null;    // it is a child-blockquote
            }
        }

        MdHeading heading = new MdHeading(token.getValue().length());

        // add child-tokens
        while (it.hasNext()) {
            MdNode node = null;
            if ((node = tryParseText(it)) != null) {
                heading.addChild(node);
            } else if (tryParseCrLf(it) != null) {
                return heading;
            } else
                return null;    // Heading is invalid, no text-token!
        }

        // check for end-token MarkdownTokenType.CRLF
        if (it.hasNext() && ((token = readToken(MarkdownTokenType.CRLF, it)) == null))
            return null;    // Heading is invalid, no CRLF token at the end

        return heading;
    }

    /**
     * Try to parse a paragraph element
     *
     * @param it
     * @return Rule (BNF):
     * paragraph ::= { text | crlf }* crlf
     */
    private static MdParagraph tryParseParagraph(ListIterator<MarkdownToken> it, String checkBlockQuote) {
        if (!it.hasNext())
            return null;


        if (checkBlockQuote != null) {
            MarkdownToken token;
            if ((token = readToken(MarkdownTokenType.QUOTE, it)) == null)
                return null;    // not a blockquote
            else if (!token.getValue().equals(checkBlockQuote)) {
                it.previous();
                return null;    // it is a child-blockquote
            }
        }
        MdParagraph paragraph = new MdParagraph();

        // add child-tokens
        MdNode node;
        while (it.hasNext()) {
            if ((node = tryParseText(it)) != null) {
                paragraph.addChild(node);
            } else if ((node = tryParseBreak(it)) != null) {
                paragraph.addChild(node);
            } else if ((node = tryParseHtml(it)) != null) {
                paragraph.addChild(node);
            } else if ((node = tryParseEmphasis(it)) != null) {
                paragraph.addChild(node);
            } else {
                MarkdownToken token;
                if ((token = readToken(MarkdownTokenType.CRLF, it)) != null) {
                    if (it.hasNext()) {
                        if ((token = readToken(MarkdownTokenType.CRLF, it)) != null)
                            break;  // double CRLF: end of paragraph
                        if (checkBlockQuote != null) {
                            if ((token = readToken(MarkdownTokenType.QUOTE, it)) == null)
                                break;    // newline is not a blockquote: end of paragraph (and blockquote)
                            else if (token.getValue().equals(checkBlockQuote)) {
                                if ((token = readToken(MarkdownTokenType.CRLF, it)) != null)
                                    break;  // CRLF + QUOTE + CRLF: end of paragraph inside quote
                            } else if (token.getValue().length() < checkBlockQuote.length()) {
                                // this blockquote is done, the parent blockquote takes over
                                it.previous();
                                break;
                            }
                        }
                    }
                } else {
                    // end of paragraph
                    if (paragraph.isLeaf())     // no children!
                        return null;
                    else
                        break;
                }
            }
        }

        return paragraph;
    }

    private static MdBlockQuote tryParseBlockQuite(ListIterator<MarkdownToken> it, String checkParentBlockQuote) {
        if (!it.hasNext())
            return null;
        MarkdownToken token;
        if ((token = readToken(MarkdownTokenType.QUOTE, it)) == null)
            return null;    // this is not a block-quote
        if (checkParentBlockQuote != null) {
            if (checkParentBlockQuote.length() > token.getValue().length()) {
                it.previous();
                return null;    // this is a parent block-quote: close the child block-quote
            }
        }

        MdBlockQuote blockQuote = new MdBlockQuote(token.getValue());
        it.previous();

        // add child-tokens
        MdNode node;
        while (it.hasNext()) {
            if ((node = tryParseParagraph(it, blockQuote.getValue())) != null) {
                blockQuote.addChild(node);
            } else if ((node = tryParseHeading(it, blockQuote.getValue())) != null) {
                blockQuote.addChild(node);
            } else if ((node = tryParseBlockQuite(it, blockQuote.getValue())) != null) {
                blockQuote.addChild(node);
            } else if (blockQuote.isLeaf())    // blockquote is empty
                return null;
            else
                break;
        }
        return blockQuote;
    }

    private static MdEmphasis tryParseEmphasis(ListIterator<MarkdownToken> it) {
        if (!it.hasNext())
            return null;
        MarkdownToken token;
        if ((token = readToken(MarkdownTokenType.EM, it)) == null)
            return null;

        MdEmphasis em = new MdEmphasis(token.getValue());
        // add child-tokens
        MdNode node;
        while (it.hasNext()) {
            if ((node = tryParseText(it)) != null) {
                em.addChild(node);
            } else if ((node = tryParseBreak(it)) != null) {
                em.addChild(node);
            } else if ((node = tryParseHtml(it)) != null) {
                em.addChild(node);
            } else {
                if ((token = readToken(MarkdownTokenType.CRLF, it)) != null) {
                    if (it.hasNext() && ((token = readToken(MarkdownTokenType.CRLF, it)) != null))
                        return null;  // double CRLF: not a valid em-tag
                    else
                        em.addChild(new MdText(" "));
                } else if ((token = readToken(MarkdownTokenType.EM, it)) != null) {
                    if (token.getValue().equals(em.getValueReverse())) {
                        break;  // it's the "end-tag"
                    } else {
                        // it's a child em-tag
                        it.previous();
                        if ((node = tryParseEmphasis(it)) != null) {
                            em.addChild(node);
                        } else {
                            if (it.hasNext())
                                it.next();  // failed to parse a child em-tag, skip it.
                            return null;
                        }
                    }
                }
            }
        }

        return em;
    }

    /**
     * Try to parse a simple text
     *
     * @param it
     * @return Rule (BNF):
     * text ::= {character}*
     * <p>
     * character       ::= letter | digit | symbol
     * letter          ::= "A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" | "J" | "K" | "L" | "M" | "N" | "O" | "P" | "Q" | "R" | "S" | "T" | "U" | "V" | "W" | "X" | "Y" | "Z" | "a" | "b" | "c" | "d" | "e" | "f" | "g" | "h" | "i" | "j" | "k" | "l" | "m" | "n" | "o" | "p" | "q" | "r" | "s" | "t" | "u" | "v" | "w" | "x" | "y" | "z"
     * digit           ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     * symbol          ::= "|" | " " | "!" | "#" | "$" | "%" | "&" | "(" | ")" | "*" | "+" | "," | "-" | "." | "/" | ":" | ";" | ">" | "=" | "<" | "?" | "@" | "[" | "\" | "]" | "^" | "_" | "`" | "{" | "}" | "~"
     * <p>
     * crlf            ::= "\n" | "\r\n"
     */
    private static MdText tryParseText(ListIterator<MarkdownToken> it) {
        if (!it.hasNext())
            return null;

        MarkdownToken token;
        if ((token = readToken(MarkdownTokenType.T, it)) == null)
            return null;
        return new MdText(token.getValue());
    }

    private static MdText tryParseCrLf(ListIterator<MarkdownToken> it) {
        if (!it.hasNext())
            return null;

        MarkdownToken token;
        if ((token = readToken(MarkdownTokenType.CRLF, it)) == null)
            return null;

        return new MdText("\n");
    }

    private static MdBreak tryParseBreak(ListIterator<MarkdownToken> it) {
        if (!it.hasNext())
            return null;

        MarkdownToken token;
        if ((token = readToken(MarkdownTokenType.BR, it)) == null)
            return null;

        return new MdBreak();
    }

    //
    // Helpers:
    //
    private static MarkdownToken readToken(MarkdownTokenType tokenType, ListIterator<MarkdownToken> it) {
        if (!it.hasNext())
            return null;
        MarkdownToken token = it.next();
        if (token.getType() != tokenType) {
            // not the correct tokenType
            it.previous();
            return null;
        }
        return token;
    }
}
