package markdown.nodes;

import markdown.parser.MarkdownLexer;
import markdown.parser.MarkdownToken;

import java.io.File;
import java.util.List;
import java.util.ListIterator;

public class MdDoc extends MdNode {
    private File filePath;

    public MdDoc() {
    }

    public MdDoc(File filePath) {
        this.filePath = filePath;
    }


    public File getFilePath() {
        return filePath;
    }

    public void setFilePath(File filePath) {
        this.filePath = filePath;
    }


    @Override
    public String toString() {
        return "MdDoc{" +
                "filePath=" + filePath +
                '}' + toStringRecursive(false);
    }
}
