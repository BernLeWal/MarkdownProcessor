package markdown.interpreter;

import markdown.nodes.MdDoc;
import markdown.nodes.MdHeading;
import markdown.nodes.MdParagraph;
import markdown.nodes.MdText;

public class HtmlGeneratorVisitor implements Visitor {
    private StringBuilder content = new StringBuilder();


    public String generate(MdDoc doc) {
        doc.accept(this);
        return content.toString();
    }

    @Override
    public void visitDoc(MdDoc doc) {
        content.append("<!DOCTYPE html>\n");
        content.append("<html>\n");
        content.append("<body>\n");
        doc.acceptChildren(this);
        content.append("</body>\n");
        content.append("</html>\n");
    }

    @Override
    public void visitHeading(MdHeading heading) {
        content.append("<h" + heading.getLevel() + ">");
        heading.acceptChildren(this);
        content.append("</h" + heading.getLevel() + ">\n");
    }

    @Override
    public void visitParagraph(MdParagraph paragraph) {
        content.append("<p>");
        paragraph.acceptChildren(this);
        content.append("</p>\n");
    }

    @Override
    public void visitText(MdText text) {
        content.append(text.getValue() + " ");
    }
}
