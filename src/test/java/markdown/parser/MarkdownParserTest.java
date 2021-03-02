package markdown.parser;

import markdown.nodes.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MarkdownParserTest {
    @Test
    void test_Empty() {
        var expected = new MdDoc();
        var actual = MarkdownParser.parse("");
        assertEquals( expected, actual );
    }

    @Test
    void test_1ParagraphEmpty() {
        var expected = new MdDoc();
        expected.addChild( new MdParagraph() );
        var actual = MarkdownParser.parse("\n");
        assertEquals( expected, actual );
    }

    @Test
    void test_2ParagraphEmpty() {
        var expected = new MdDoc();
        expected.addChild( new MdParagraph() );
        expected.addChild( new MdParagraph() );
        var actual = MarkdownParser.parse("\n\n ");
        assertEquals( expected, actual );
    }

    @Test
    void test_3ParagraphEmpty() {
        var expected = new MdDoc();
        expected.addChild( new MdParagraph() );
        expected.addChild( new MdParagraph() );
        expected.addChild( new MdParagraph() );
        var actual = MarkdownParser.parse("\n\n\n\n ");
        assertEquals( expected, actual );
    }

    @Test
    void test_1Paragraph1TextA() {
        var expected = new MdDoc();
        expected.addChild( new MdParagraph("A single paragraph with text") );
        var actual = MarkdownParser.parse("A single paragraph with text");
        assertEquals( expected, actual );
    }

    //@Test - TODO
    void test_1Paragraph1TextB() {
        var expected = new MdDoc();
        expected.addChild( new MdParagraph(" ") );
        var actual = MarkdownParser.parse(" ");
        assertEquals( expected, actual );
    }

    @Test
    void test_1Paragraph2Text() {
        var expected = new MdDoc();
        var paragraph = new MdParagraph();
        paragraph.addChild( new MdText("First Line") );
        paragraph.addChild( new MdText( "Second Line") );
        expected.addChild( paragraph );
        var actual = MarkdownParser.parse("First Line\nSecond Line");
        assertEquals( expected, actual );
    }

    @Test
    void test_1Paragraph3Text() {
        var expected = new MdDoc();
        var paragraph = new MdParagraph();
        paragraph.addChild( new MdText("First Line") );
        paragraph.addChild( new MdText( "Second Line") );
        paragraph.addChild( new MdText( "Third Line") );
        expected.addChild( paragraph );
        var actual = MarkdownParser.parse("First Line\nSecond Line\nThird Line");
        assertEquals( expected, actual );
    }

    @Test
    void test_2Paragraph1Text() {
        var expected = new MdDoc();
        expected.addChild( new MdParagraph("First Paragraph") );
        expected.addChild( new MdParagraph("Second Paragraph") );
        var actual = MarkdownParser.parse("First Paragraph\n\nSecond Paragraph");
        assertEquals( expected, actual );
    }

    @Test
    void test_3Paragraph1Text() {
        var expected = new MdDoc();
        expected.addChild( new MdParagraph("First Paragraph") );
        expected.addChild( new MdParagraph("Second Paragraph") );
        expected.addChild( new MdParagraph("Third Paragraph") );
        var actual = MarkdownParser.parse("First Paragraph\n\nSecond Paragraph\n\nThird Paragraph");
        assertEquals( expected, actual );
    }


    @Test
    void test_1Heading1() {
        var expected = new MdDoc();
        expected.addChild( new MdHeading(1, "Heading 1") );
        var actual = MarkdownParser.parse("# Heading 1");
        assertEquals( expected, actual );
    }

    @Test
    void test_1Heading2() {
        var expected = new MdDoc();
        expected.addChild( new MdHeading(2, "Heading 2") );
        var actual = MarkdownParser.parse("## Heading 2");
        assertEquals( expected, actual );
    }

    @Test
    void test_1Heading3() {
        var expected = new MdDoc();
        expected.addChild( new MdHeading(3, "Heading 3") );
        var actual = MarkdownParser.parse("### Heading 3");
        assertEquals( expected, actual );
    }

}
