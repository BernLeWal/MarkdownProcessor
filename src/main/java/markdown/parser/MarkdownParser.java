package markdown.parser;

import markdown.nodes.*;

import java.io.File;
import java.util.List;
import java.util.ListIterator;

/**
 * The MarkdownParser parses the flat token-stream to build up a hierarchy of MdNodes.
 *
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
     * @param filePath optional; a filePath to an .md-file where the initial markdown was stored
     * @param it the token-stream; it should point to the very head element
     * @return the root-node of the markdown-hierarchy; on error it returns null
     *
     * Rule (BNF):
     *  document ::= {paragraph | heading}*
     */
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
                    throw new MarkdownParseException("Invalid token!", doc, it.next());
                }
            }
        } catch( MarkdownParseException e ) {
            e.printStackTrace();
        }

        return doc;
    }


    /**
     * Try to parse an heading element
     * @param it
     * @return
     * @throws MarkdownParseException
     *
     * Rule (BNF):
     *  heading ::= "#" {"#"}* {text}* crlf
     */
    private static MdHeading tryParseHeading(ListIterator<MarkdownToken> it) throws MarkdownParseException {
        if (!it.hasNext())
            return null;

        // check for start-token MarkdownTokenType.H
        MarkdownToken token;
        if ((token=readToken(MarkdownTokenType.H, it))==null)
            return null;
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
            throw new MarkdownParseException("Heading is invalid, no text-token!", heading, it.next());
        }

        // check for end-token MarkdownTokenType.CRLF
        if (it.hasNext()) {
            if ((token=readToken(MarkdownTokenType.CRLF, it))==null)
                throw new MarkdownParseException("Invalid token!", heading, token);
        }

        return heading;
    }

    /**
     * Try to parse a paragraph element
     * @param it
     * @return
     *
     * Rule (BNF):
     *  paragraph ::= { text | crlf }* crlf
     */
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
                        if ((token=readToken(MarkdownTokenType.CRLF, it))!=null)
                            // double CRLF: end of paragraph
                            break;
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

    /**
     * Try to parse a simple text
     * @param it
     * @return
     *
     * Rule (BNF):
     *  text ::= {character}*
     *
     *  character       ::= letter | digit | symbol
     *  letter          ::= "A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" | "J" | "K" | "L" | "M" | "N" | "O" | "P" | "Q" | "R" | "S" | "T" | "U" | "V" | "W" | "X" | "Y" | "Z" | "a" | "b" | "c" | "d" | "e" | "f" | "g" | "h" | "i" | "j" | "k" | "l" | "m" | "n" | "o" | "p" | "q" | "r" | "s" | "t" | "u" | "v" | "w" | "x" | "y" | "z"
     *  digit           ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *  symbol          ::= "|" | " " | "!" | "#" | "$" | "%" | "&" | "(" | ")" | "*" | "+" | "," | "-" | "." | "/" | ":" | ";" | ">" | "=" | "<" | "?" | "@" | "[" | "\" | "]" | "^" | "_" | "`" | "{" | "}" | "~"
     *
     *  crlf            ::= "\n" | "\r\n"
     */
    private static MdText tryParseText(ListIterator<MarkdownToken> it) {
        if( !it.hasNext() )
            return null;

        MarkdownToken token;
        if ((token=readToken(MarkdownTokenType.T,it))==null)
            return null;
        return new MdText(token.getValue());
    }



    //
    // Helpers:
    //
    private static MarkdownToken readToken(MarkdownTokenType tokenType, ListIterator<MarkdownToken> it) {
        MarkdownToken token = it.next();
        if (token.getType() != tokenType) {
            // not the correct tokenType
            it.previous();
            return null;
        }
        return token;
    }
}
