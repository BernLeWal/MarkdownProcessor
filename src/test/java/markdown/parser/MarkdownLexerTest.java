package markdown.parser;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownLexerTest extends MarkdownTokenFactory {
    private static List<MarkdownToken> lexer(String s) {
        return new MarkdownLexer(false).tokenize( s );
    }

    @Test
    void test_Empty() {
        var expected = new ArrayList<MarkdownToken>();
        var actual = lexer("");
        assertEquals( expected, actual );
    }

    @Test
    void testSingleToken_Text() {
        testSingleToken( "Hello World!", createT("Hello World!") );
    }

    @Test
    void testSingleToken_CRLF() {
        var expected = Arrays.asList( createCRLF() );
        assertEquals( expected, lexer("\n") );
        assertEquals( expected, lexer("\r") );
        assertEquals( expected, lexer("\r\n") );
        assertEquals( expected, lexer("\n\r") );
    }

    @Test
    void testSingleToken_Indent1() {
        testSingleToken( " ", createINDENT(" ") );
    }

    @Test
    void testSingleToken_Indent2() {
        testSingleToken( "  ", createINDENT("  ") );
    }

    @Test
    void testSingleToken_IndentTab1() {
        testSingleToken( "\t", createINDENT("\t") );
    }

    @Test
    void testSingleToken_IndentTab2() {
        testSingleToken( "\t\t", createINDENT("\t\t") );
    }


    @Test
    void testSimpleTokens_DoubleCRLF() {
        var expected = Arrays.asList( createCRLF(), createCRLF() );
        assertEquals( expected, lexer("\n\n") );
        assertEquals( expected, lexer("\r\n\r\n") );
        assertEquals( expected, lexer("\n\r\n\r") );
        assertEquals( expected, lexer("\r\n\n") );
        assertEquals( expected, lexer("\n\r\n") );
        assertEquals( expected, lexer("\n\n\r") );
    }

    @Test
    void testSimpleTokens_TextCRLF() {
        testDoubleToken( "Text\n", createT("Text"), createCRLF() );
    }

    @Test
    void testSimpleTokens_DoubleTextCRLF() {
        var expected = Arrays.asList( createT( "First"), createCRLF(), createT( "Second") );
        assertEquals( expected, lexer("First\nSecond") );
    }

    @Test
    void testSimpleTokens_DoubleTextDoubleCRLF() {
        var expected = Arrays.asList(
                createT( "First"),
                createCRLF(),
                createT( "Second"),
                createCRLF()
        );
        assertEquals( expected, lexer("First\nSecond\n") );
    }

    @Test
    void testSimpleTokens_NoLineBreak1() {
        testDoubleToken( "Text \n", createT( "Text "), createCRLF() );
    }

    @Test
    void testSimpleTokens_NoLineBreak2() {
        testSingleToken( "Text ", createT( "Text ") );
    }

    @Test
    void testSimpleTokens_NoLineBreak3() {
        testSingleToken( "First  Second", createT( "First  Second") );
    }

    @Test
    void testSimpleTokens_LineBreak1() {
        testDoubleToken( "Text  \n", createT( "Text"), createBR() );
    }

    @Test
    void testSimpleTokens_LineBreak2() {
        testDoubleToken( "Text   \n", createT( "Text"), createBR() );
    }

    @Test
    void testSimpleTokens_LineBreak3() {
        testDoubleToken( "Text    \n", createT( "Text"), createBR() );
    }

    @Test
    void testSimpleTokens_LineBreak1b() {
        testDoubleToken( "Text  \r\n", createT( "Text"), createBR() );
    }

    @Test
    void testSimpleTokens_LineBreak2b() {
        testDoubleToken( "Text   \r\n", createT( "Text"), createBR() );
    }

    @Test
    void testSimpleTokens_LineBreak3b() {
        testDoubleToken( "Text    \r\n", createT( "Text"), createBR() );
    }

    @Test
    void testSimpleTokens_H1() {
        var expected = Arrays.asList(createH("#"),createT( "Heading") );
        assertEquals( expected, lexer("# Heading") );
        assertEquals( expected, lexer("#Heading") );
    }

    @Test
    void testSimpleTokens_IndentH1() {
        var expected = Arrays.asList(
                createINDENT( " "),
                createH("#"),
                createT( "Heading") );
        assertEquals( expected, lexer(" # Heading") );
        assertEquals( expected, lexer(" #Heading") );
    }

    @Test
    void testSimpleTokens_H2() {
        var expected = Arrays.asList( createH("##"), createT( "Heading") );
        assertEquals( expected, lexer("## Heading") );
        assertEquals( expected, lexer("##Heading") );
    }

    @Test
    void testSimpleTokens_IndentH2() {
        var expected = Arrays.asList(
                createINDENT( " "),
                createH("##"),
                createT( "Heading") );
        assertEquals( expected, lexer(" ## Heading") );
        assertEquals( expected, lexer(" ##Heading") );
    }

    @Test
    void testSimpleTokens_H3() {
        var expected = Arrays.asList( createH("###"),createT( "Heading") );
        assertEquals( expected, lexer("### Heading") );
        assertEquals( expected, lexer("###Heading") );
    }

    @Test
    void testSimpleTokens_IndentH3() {
        var expected = Arrays.asList(
                createINDENT( " "),
                createH("###"),
                createT( "Heading") );
        assertEquals( expected, lexer(" ### Heading") );
        assertEquals( expected, lexer(" ###Heading") );
    }

    @Test
    void testSimpleTokens_H4() {
        var expected = Arrays.asList( createH("####"),createT( "Heading") );
        assertEquals( expected, lexer("#### Heading") );
        assertEquals( expected, lexer("####Heading") );
    }

    @Test
    void testSimpleTokens_IndentH4() {
        var expected = Arrays.asList(
                createINDENT( " "),
                createH("####"),
                createT( "Heading") );
        assertEquals( expected, lexer(" #### Heading") );
        assertEquals( expected, lexer(" ####Heading") );
    }

    @Test
    void testSimpleTokens_H5() {
        var expected = Arrays.asList( createH("#####"),createT( "Heading") );
        assertEquals( expected, lexer("##### Heading") );
        assertEquals( expected, lexer("#####Heading") );
    }

    @Test
    void testSimpleTokens_IndentH5() {
        var expected = Arrays.asList(
                createINDENT( " "),
                createH("#####"),
                createT( "Heading") );
        assertEquals( expected, lexer(" ##### Heading") );
        assertEquals( expected, lexer(" #####Heading") );
    }

    @Test
    void testSimpleTokens_H6() {
        var expected = Arrays.asList( createH("######"),createT( "Heading") );
        assertEquals( expected, lexer("###### Heading") );
        assertEquals( expected, lexer("######Heading") );
    }

    @Test
    void testSimpleTokens_IndentH6() {
        var expected = Arrays.asList(
                createINDENT( " "),
                createH("######"),
                createT( "Heading") );
        assertEquals( expected, lexer(" ###### Heading") );
        assertEquals( expected, lexer(" ######Heading") );
    }

    @Test
    void testSimpleTokens_HtmlTagHR() {
        testSingleToken( "<hr>", createHTML( "<hr>") );
    }

    @Test
    void testSimpleTokens_HtmlTagHR1() {
        testDoubleToken( "Text<hr>", createT( "Text"), createHTML( "<hr>") );
    }

    @Test
    void testSimpleTokens_HtmlTagHR2() {
        testDoubleToken( "<hr>Text", createHTML( "<hr>"), createT( "Text") );
    }

    @Test
    void testSimpleTokens_HtmlTagHR3() {
        testTripleToken( "Front<hr>Back", createT( "Front"),createHTML( "<hr>"),createT( "Back") );
    }

    @Test
    void testSimpleTokens_HtmlTagBR() {
        testSingleToken( "<br/>", createHTML( "<br/>") );
    }

    @Test
    void testSimpleTokent_NoHtmlTag1() {
        testSingleToken( "The result of 9<10 is true", createT( "The result of 9<10 is true") );
    }

    @Test
    void testSimpleTokent_NoHtmlTag2() {
        testDoubleToken( "The result of 10 < 11 is true\n", createT( "The result of 10 < 11 is true"), new MarkdownToken(MarkdownTokenType.CRLF) );
    }

    @Test
    void testSimpleTokent_NoHtmlTag3() {
        final String longText = "Lorem ipsum < dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
        var expected = Arrays.asList( createT( longText) );
        assertEquals( expected, lexer(longText) );
    }


    @Test
    void testSimpleTokens_Quote1() {
        var expected = Arrays.asList( createQUOTE(">"),createT( "Quote") );
        assertEquals( expected, lexer(">Quote") );
        assertEquals( expected, lexer("> Quote") );
    }

    @Test
    void testSimpleTokens_IndentQuote1() {
        var expected = Arrays.asList(
                createINDENT( " "),
                createQUOTE(">"),
                createT( "Quote") );
        assertEquals( expected, lexer(" >Quote") );
        assertEquals( expected, lexer(" > Quote") );
    }

    @Test
    void testSimpleTokens_Quote2() {
        var expected = Arrays.asList( createQUOTE(">>"),createT( "Quote") );
        assertEquals( expected, lexer(">>Quote") );
        assertEquals( expected, lexer(">> Quote") );
    }

    @Test
    void testSimpleTokens_IndentQuote2() {
        var expected = Arrays.asList(
                createINDENT( " "),
                createQUOTE(">>"),
                createT( "Quote") );
        assertEquals( expected, lexer(" >>Quote") );
        assertEquals( expected, lexer(" >> Quote") );
    }

    @Test
    void testSimpleTokens_Quote3() {
        var expected = Arrays.asList( createQUOTE(">>>"),createT( "Quote") );
        assertEquals( expected, lexer(">>>Quote") );
        assertEquals( expected, lexer(">>> Quote") );
    }

    @Test
    void testSimpleTokens_IndentQuote3() {
        var expected = Arrays.asList(
                createINDENT( " "),
                createQUOTE(">>>"),
                createT( "Quote") );
        assertEquals( expected, lexer(" >>>Quote") );
        assertEquals( expected, lexer(" >>> Quote") );
    }

    @Test
    void testSimpleTokens_UnnumberedListItem1() {
        testDoubleToken( "- List Item", createUL("-"),createT( "List Item") );
    }

    @Test
    void testSimpleTokens_IndentUnnumberedListItem1() {
        testTripleToken( " - List Item", createINDENT( " "),createUL("-"),createT( "List Item") );
    }

    @Test
    void testSimpleTokens_UnnumberedListItem2() {
        testDoubleToken( "* List Item", createUL("*"),createT( "List Item") );
    }

    @Test
    void testSimpleTokens_IndentUnnumberedListItem2() {
        testTripleToken( " * List Item", createINDENT( " "),createUL("*"),createT( "List Item") );
    }

    @Test
    void testSimpleTokens_UnnumberedListItem3() {
        testDoubleToken( "+ List Item", createUL("+"),createT( "List Item") );
    }

    @Test
    void testSimpleTokens_IndentUnnumberedListItem3() {
        testTripleToken( " + List Item", createINDENT( " "),createUL("+"),createT( "List Item") );
    }

    @Test
    void testSimpleTokens_NoUnnumberedListItem1() {
        testSingleToken( "-List Item", createT( "-List Item") );
    }

    @Test
    void testSimpleTokens_IndentNoUnnumberedListItem1() {
        testDoubleToken( " -List Item", createINDENT( " "),createT( "-List Item") );
    }

    @Test
    void testSimpleTokens_NoUnnumberedListItem2() {
        testDoubleToken( "*List Item", createEM("*"),createT( "List Item") );
    }

    @Test
    void testSimpleTokens_IndentNoUnnumberedListItem2() {
        testTripleToken( " *List Item", createINDENT( " "),createEM("*"),createT( "List Item") );
    }

    @Test
    void testSimpleTokens_NoUnnumberedListItem3() {
        testSingleToken( "+List Item", createT( "+List Item") );
    }

    @Test
    void testSimpleTokens_IndentNoUnnumberedListItem3() {
        testDoubleToken( " +List Item", createINDENT( " "),createT( "+List Item") );
    }


    // helpers:
    private void testSingleToken( String text, MarkdownToken expected ) {
        assertEquals( Arrays.asList( expected ), lexer( text ) );
    }

    private void testDoubleToken( String text, MarkdownToken token1, MarkdownToken token2 ) {
        assertEquals( Arrays.asList( token1, token2 ), lexer( text ) );
    }

    private void testTripleToken( String text, MarkdownToken t1, MarkdownToken t2, MarkdownToken t3 ) {
        assertEquals( Arrays.asList( t1, t2, t3 ), lexer( text ) );
    }
}