package markdown;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
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
            if (resourceHtml==null)
                fail("Failed to load " + htmlFile);
            URL resourceMd = Thread.currentThread().getContextClassLoader().getResource(markdownFile);
            if (resourceMd==null)
                fail("Failed to load " + markdownFile);

            expected = new String(Files.readAllBytes(Paths.get(resourceHtml.toURI())));
            expected = expected.replaceAll("\r\n","\n");    // fix CRLF from html-files written by the editor
            actual = mdp.process(resourceMd.toURI());
        } catch (IOException | URISyntaxException | NullPointerException e) {
            e.printStackTrace();
        }
        assertEquals(expected, actual);
    }

    @Test
    void test_basic01_text() {
        test_file("basic01_text.md", "basic01_text.html");
    }

    @Test
    void test_basic02_headings() {
        test_file("basic02_headings.md", "basic02_headings.html");
    }

    @Test
    void test_basic03_paragraphs() {
        test_file("basic03_paragraphs.md", "basic03_paragraphs.html");
    }

    @Test
    void test_basic04_linebreaks() {
        test_file( "basic04_linebreaks.md", "basic04_linebreaks.html");
    }

    @Test
    void test_basic05_emphasis() {
        test_file("basic05_emphasis.md", "basic05_emphasis.html");
    }

    @Test
    void test_basic06_blockquotes() {
        test_file( "basic06_blockquotes.md", "basic06_blockquotes.html");
    }
}