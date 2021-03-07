package markdown;

import markdown.interpreter.HtmlGeneratorVisitor;
import markdown.nodes.MdDoc;
import markdown.parser.MarkdownLexer;
import markdown.parser.MarkdownParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

public class MarkdownProcessor {
    public static void main(String[] args) {
        if (args.length<2) {
            System.out.println("MarkdownProcessor by bernhard_wallisch@hotmail.com");
            System.out.println();
            System.out.println("Process input markdown-files and generate template-based output-files in different formats");
            System.out.println();
            System.out.println("Syntax: MarkdownProcessor <input-file> <output-file>");
            System.out.println("    <input-file>    the path to a markdown-file (.md)");
            System.out.println("    <output-file>   the path to the generated output-file (.html)");
            System.out.println("Remarks: When no further arguments are given, (built-in) plain html files are generated.");
            System.exit(1);
        }

        File inputFile = new File(args[1]);
        if (!inputFile.exists()) {
            System.err.printf("Input markdown-file %s is not existing!\n", inputFile);
            System.exit(1);
        }
        File outputFile = new File(args[2]);
        if (!outputFile.canWrite()) {
            System.err.printf("Can not write to output-file %s!\n", outputFile);
        }

        var inputURI = inputFile.toURI();
        MarkdownProcessor mdp = new MarkdownProcessor();
        int status = mdp.process( inputFile.toURI(), outputFile );
        if (status!=0) {
            System.exit(status);
        }
    }

    public int process(URI inputURI, File outputFile) {
        String html = process(inputURI);
        if( html==null )
            return 1;

        try (PrintWriter writer = new PrintWriter(outputFile)) {
            writer.println( html );
        } catch (FileNotFoundException e) {
            System.err.printf("Failed to write to output-file %s!\n", outputFile);
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    public String process(URI inputURI) {
        MdDoc doc;
        try {
            MarkdownLexer lexer = new MarkdownLexer();
            lexer.setCreateCRLFEofToken(true);
            var tokens = lexer.tokenize(inputURI);

            doc = MarkdownParser.parse(new File(inputURI), tokens.listIterator());
        } catch (IOException e) {
            System.err.printf("Could not read from input markdown-file %s!\n", inputURI);
            e.printStackTrace(System.err);
            return null;
        }
        if (doc==null) {
            System.err.printf("Failed to parse markdown in file %s!\n", inputURI);
            return null;
        }

        var htmlGenerator = new HtmlGeneratorVisitor();
        return htmlGenerator.generate( doc );
    }
}
