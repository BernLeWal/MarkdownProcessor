package markdown.nodes;

import markdown.parser.MarkdownLexer;
import markdown.parser.MarkdownToken;

import java.io.File;
import java.util.List;
import java.util.ListIterator;

public class MdDoc extends MdNode {
    private File filePath;

    public MdDoc(File filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "MdDoc{" +
                "filePath=" + filePath +
                '}' + toStringRecursive(false);
    }

    // parser:
    public static MdDoc parse(String content) {
        List<MarkdownToken> tokens = new MarkdownLexer(false).tokenize(content);
        return parse(null, tokens.listIterator());
    }

    public static MdDoc parse(File filePath, ListIterator<MarkdownToken> it) {
        MdDoc doc = new MdDoc(filePath);

        while (it.hasNext()) {
            MdNode node;
            if ((node = MdParagraph.parse(it)) != null) {
                doc.addChild(node);
            } else if ((node = MdHeading.parse(it)) != null) {
                doc.addChild(node);
            } else {
                // ERROR
                break;
            }
        }

        return doc;
    }


}
