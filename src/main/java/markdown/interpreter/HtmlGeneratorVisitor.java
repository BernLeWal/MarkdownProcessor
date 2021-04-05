package markdown.interpreter;

import markdown.nodes.*;

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
    public void visitHtml(MdHtml html) {
        content.append(html.getValue() + "\n");
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

    @Override
    public void visitBreak(MdBreak lineBreak) {
        content.append("<br/>\n");
    }
}
