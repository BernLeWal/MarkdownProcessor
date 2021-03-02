package markdown.parser;

import markdown.nodes.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MarkdownParserTest {
    @Test
    void test_Empty() {
        var expected = new MdDoc(null);
        var actual = MdDoc.parse("");
        assertEquals( expected, actual );
    }

    @Test
    void test_SingleParagraphEmpty() {
        var expected = new MdDoc(null);
        expected.addChild( new MdParagraph() );
        var actual = MdDoc.parse("\n");
        assertEquals( expected, actual );
    }

    @Test
    void test_SingleParagraph1Text() {
        var expected = new MdDoc(null);
        expected.addChild( new MdParagraph("A single paragraph with text") );
        var actual = MdDoc.parse("A single paragraph with text");
        assertEquals( expected, actual );
    }

    @Test
    void test_SingleParagraph2Text() {
        var expected = new MdDoc(null);
        var paragraph = new MdParagraph();
        paragraph.addChild( new MdText("First Line") );
        paragraph.addChild( new MdText( "Second Line") );
        expected.addChild( paragraph );
        var actual = MdDoc.parse("First Line\nSecond Line");
        assertEquals( expected, actual );
    }

    @Test
    void test_SingleParagraph3Text() {
        var expected = new MdDoc(null);
        var paragraph = new MdParagraph();
        paragraph.addChild( new MdText("First Line") );
        paragraph.addChild( new MdText( "Second Line") );
        paragraph.addChild( new MdText( "Third Line") );
        expected.addChild( paragraph );
        var actual = MdDoc.parse("First Line\nSecond Line\nThird Line");
        assertEquals( expected, actual );
    }

    @Test
    void test_SingleHeading() {
        var expected = new MdDoc(null);
        expected.addChild( new MdHeading(1, "Heading 1") );
        var actual = MdDoc.parse("# Heading 1");
        assertEquals( expected, actual );
    }
}
