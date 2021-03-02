package markdown.interpreter;

import markdown.nodes.MdDoc;
import markdown.nodes.MdHeading;
import markdown.nodes.MdParagraph;
import markdown.nodes.MdText;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HtmlGeneratorTest {
    public static final String DOCTYPE_HTML_BODY_BEGIN = """
            <!DOCTYPE html>
            <html>
            <body>
            """;
    public static final String BODY_HTML_END = """
            </body>
            </html>
            """;

    HtmlGeneratorVisitor htmlGenerator;

    @BeforeEach
    void setUp() {
        htmlGenerator = new HtmlGeneratorVisitor();
    }

    @Test
    public void test_Empty() {
        var doc = new MdDoc();
        var expected = DOCTYPE_HTML_BODY_BEGIN + BODY_HTML_END;
        var actual = htmlGenerator.generate(doc);
        assertEquals( actual, expected );
    }

    @Test
    void test_1ParagraphEmpty() {
        var doc = new MdDoc();
        doc.addChild( new MdParagraph() );
        var expected = DOCTYPE_HTML_BODY_BEGIN + """
            <p></p>
            """+ BODY_HTML_END;
        var actual = htmlGenerator.generate(doc);
        assertEquals( actual, expected );
    }

    @Test
    void test_2ParagraphEmpty() {
        var doc = new MdDoc();
        doc.addChild(new MdParagraph());
        doc.addChild(new MdParagraph());
        var expected = DOCTYPE_HTML_BODY_BEGIN + """
            <p></p>
            <p></p>
            """+ BODY_HTML_END;
        var actual = htmlGenerator.generate(doc);
        assertEquals( expected, actual );
    }

    @Test
    void test_3ParagraphEmpty() {
        var doc = new MdDoc();
        doc.addChild(new MdParagraph());
        doc.addChild(new MdParagraph());
        doc.addChild(new MdParagraph());
        var expected = DOCTYPE_HTML_BODY_BEGIN + """
            <p></p>
            <p></p>
            <p></p>
            """+ BODY_HTML_END;
        var actual = htmlGenerator.generate(doc);
        assertEquals( expected, actual );
    }

    @Test
    void test_1Paragraph1TextA() {
        var doc = new MdDoc();
        doc.addChild(new MdParagraph("A single paragraph with text"));
        var expected = DOCTYPE_HTML_BODY_BEGIN + """
            <p>A single paragraph with text </p>
            """+ BODY_HTML_END;
        var actual = htmlGenerator.generate(doc);
        assertEquals( actual, expected );
    }

    @Test
    void test_1Paragraph2Text() {
        var doc = new MdDoc();
        var paragraph = new MdParagraph();
        paragraph.addChild(new MdText("First Line"));
        paragraph.addChild(new MdText("Second Line"));
        doc.addChild(paragraph);
        var expected = DOCTYPE_HTML_BODY_BEGIN + """
            <p>First Line Second Line </p>
            """+ BODY_HTML_END;
        var actual = htmlGenerator.generate(doc);
        assertEquals( actual, expected );
    }

    @Test
    void test_1Paragraph3Text() {
        var doc = new MdDoc();
        var paragraph = new MdParagraph();
        paragraph.addChild(new MdText("First Line"));
        paragraph.addChild(new MdText("Second Line"));
        paragraph.addChild(new MdText("Third Line"));
        doc.addChild(paragraph);
        var expected = DOCTYPE_HTML_BODY_BEGIN + """
            <p>First Line Second Line Third Line </p>
            """+ BODY_HTML_END;
        var actual = htmlGenerator.generate(doc);
        assertEquals( actual, expected );
    }

    @Test
    void test_2Paragraph1Text() {
        var doc = new MdDoc();
        doc.addChild(new MdParagraph("First Paragraph"));
        doc.addChild(new MdParagraph("Second Paragraph"));
        var expected = DOCTYPE_HTML_BODY_BEGIN + """
            <p>First Paragraph </p>
            <p>Second Paragraph </p>
            """+ BODY_HTML_END;
        var actual = htmlGenerator.generate(doc);
        assertEquals( actual, expected );
    }

    @Test
    void test_3Paragraph1Text() {
        var doc = new MdDoc();
        doc.addChild(new MdParagraph("First Paragraph"));
        doc.addChild(new MdParagraph("Second Paragraph"));
        doc.addChild(new MdParagraph("Third Paragraph"));
        var expected = DOCTYPE_HTML_BODY_BEGIN + """
            <p>First Paragraph </p>
            <p>Second Paragraph </p>
            <p>Third Paragraph </p>
            """+ BODY_HTML_END;
        var actual = htmlGenerator.generate(doc);
        assertEquals( actual, expected );
    }


    @Test
    void test_1Heading1() {
        var doc = new MdDoc();
        doc.addChild(new MdHeading(1, "Heading 1"));
        var expected = DOCTYPE_HTML_BODY_BEGIN + """
            <h1>Heading 1 </h1>
            """+ BODY_HTML_END;
        var actual = htmlGenerator.generate(doc);
        assertEquals( actual, expected );
    }

    @Test
    void test_1Heading2() {
        var doc = new MdDoc();
        doc.addChild(new MdHeading(2, "Heading 2"));
        var expected = DOCTYPE_HTML_BODY_BEGIN + """
            <h2>Heading 2 </h2>
            """+ BODY_HTML_END;
        var actual = htmlGenerator.generate(doc);
        assertEquals( actual, expected );
    }

    @Test
    void test_1Heading3() {
        var doc = new MdDoc();
        doc.addChild(new MdHeading(3, "Heading 3"));
        var expected = DOCTYPE_HTML_BODY_BEGIN + """
            <h3>Heading 3 </h3>
            """+ BODY_HTML_END;
        var actual = htmlGenerator.generate(doc);
        assertEquals( actual, expected );
    }

}