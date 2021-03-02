package markdown.parser;

public class MarkdownTokenFactory {
    public static MarkdownToken createT( String text ) {
        return new MarkdownToken(MarkdownTokenType.T, text);
    }

    public static MarkdownToken createCRLF() {
        return new MarkdownToken(MarkdownTokenType.CRLF);
    }

    public static MarkdownToken createINDENT( String indent ) {
        return new MarkdownToken(MarkdownTokenType.INDENT, indent );
    }

    public static MarkdownToken createBR() {
        return new MarkdownToken(MarkdownTokenType.BR );
    }

    public static MarkdownToken createH( String heading ) {
        return new MarkdownToken(MarkdownTokenType.H, heading );
    }

    public static MarkdownToken createHTML( String html ) {
        return new MarkdownToken(MarkdownTokenType.HTML, html );
    }

    public static MarkdownToken createEM( String emphasis ) {
        return new MarkdownToken(MarkdownTokenType.EM, emphasis );
    }

    public static MarkdownToken createQUOTE( String quote ) {
        return new MarkdownToken(MarkdownTokenType.QUOTE, quote );
    }

    public static MarkdownToken createUL( String unnumberedListItem ) {
        return new MarkdownToken(MarkdownTokenType.UL, unnumberedListItem );
    }

    public static MarkdownToken createNL( String numberedListItem ) {
        return new MarkdownToken(MarkdownTokenType.NL, numberedListItem );
    }

    public static MarkdownToken createCODE( String code ) {
        return new MarkdownToken(MarkdownTokenType.CODE, code );
    }
}
