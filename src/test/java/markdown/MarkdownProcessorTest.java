package markdown;

import markdown.parser.MarkdownParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Resources;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownProcessorTest {
    private MarkdownProcessor mdp;

    @BeforeEach
    void setUp() {
        mdp = new MarkdownProcessor();
    }

    void test_file(String markdownFile, String htmlFile) {
        String expected = null;
        String actual = null;
        try {
            URL resourceHtml = Thread.currentThread().getContextClassLoader().getResource(htmlFile);
            URL resourceMd = Thread.currentThread().getContextClassLoader().getResource(markdownFile);

            expected = new String(Files.readAllBytes(Paths.get(resourceHtml.toURI())));
            expected = expected.replaceAll("\r\n","\n");    // fix CRLF from html-files written by the editor
            actual = mdp.process(resourceMd.toURI());
        } catch (IOException | URISyntaxException | NullPointerException e) {
            e.printStackTrace();
        }
        assertEquals(expected, actual);
    }

    @Test
    void test_basic1_paragraphs() {
        test_file( "basic1.md", "basic1.html");
    }
}