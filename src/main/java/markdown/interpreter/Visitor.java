package markdown.interpreter;

import markdown.nodes.*;

public interface Visitor {
    void visitDoc(MdDoc doc);
    void visitHeading(MdHeading heading);
    void visitParagraph(MdParagraph paragraph);
    void visitText(MdText text);
}
