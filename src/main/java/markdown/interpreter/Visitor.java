package markdown.interpreter;

import markdown.nodes.*;

public interface Visitor {
    void visitDoc(MdDoc doc);

    void visitParagraph(MdParagraph paragraph);
    void visitHeading(MdHeading heading);
    void visitHtml(MdHtml html);
    void visitBlockQuote(MdBlockQuote mdBlockQuote);

    void visitText(MdText text);
    void visitBreak(MdBreak lineBreak);
    void visitEmphasis(MdEmphasis mdEmphasis);
}
