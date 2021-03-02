Basic Syntax

The Markdown elements outlined in John Gruber's design document.

Overview

Nearly all Markdown applications support the basic syntax outlined in John Gruber’s original design document. There are minor variations and discrepancies between Markdown processors — those are noted inline wherever possible.

Headings

To create a heading, add number signs (#) in front of a word or phrase. The number of number signs you use should correspond to the heading level.

# Heading level 1
## Heading level 2
### Heading level 3
#### Heading level 4
##### Heading level 5
###### Heading level 6

Paragraphs

To create paragraphs, use a blank line to separate one or more lines of text.

I really like using Markdown.

I think I'll use it to format all of my documents from now on.

Line Breaks

To create a line break (<br>), end a line with two or more spaces, and then type return.

This is the first line.    
And this is the second line.

First line with the HTML tag after.<br>
And the next line.

Emphasis

You can add emphasis by making text bold or italic.

Bold

To bold text, add two asterisks or underscores before and after a word or phrase. To bold the middle of a word for emphasis, add two asterisks without spaces around the letters.

I just love **bold text**.
I just love __bold text__.
Love**is**bold

Italic

To italicize text, add one asterisk or underscore before and after a word or phrase. To italicize the middle of a word for emphasis, add one asterisk without spaces around the letters.

Italicized text is the *cat's meow*.  
Italicized text is the _cat's meow_.  
A*cat*meow

Bold and Italic

To emphasize text with bold and italics at the same time, add three asterisks or underscores before and after a word or phrase. To bold and italicize the middle of a word for emphasis, add three asterisks without spaces around the letters.

This text is ***really important***.  
 This text is ___really important___.  
  This text is __*really important*__.  
   This text is **_really important_**.  
    This is really***very***important text.

Blockquotes

To create a blockquote, add a > in front of a paragraph.

> Dorothy followed her through many of the beautiful rooms in her castle.

Blockquotes with Multiple Paragraphs

Blockquotes can contain multiple paragraphs. Add a > on the blank lines between the paragraphs.

> Dorothy followed her through many of the beautiful rooms in her castle.
>
> The Witch bade her clean the pots and kettles and sweep the floor and keep the fire fed with wood.

Nested Blockquotes

Blockquotes can be nested. Add a >> in front of the paragraph you want to nest.

> Dorothy followed her through many of the beautiful rooms in her castle.
>
>> The Witch bade her clean the pots and kettles and sweep the floor and keep the fire fed with wood.

Blockquotes with Other Elements

Blockquotes can contain other Markdown formatted elements. Not all elements can be used — you’ll need to experiment to see which ones work.

> #### The quarterly results look great!
>
> - Revenue was off the chart.
> - Profits were higher than ever.
>
>  *Everything* is going according to **plan**.

Lists

You can organize items into ordered and unordered lists.

Ordered Lists

To create an ordered list, add line items with numbers followed by periods. 

1. First item
2. Second item
3. Third item
4. Fourth item 

The numbers don’t have to be in numerical order, but the list should start with the number one.

1. First item
1. Second item
1. Third item
1. Fourth item 

The numbers don’t have to be in numerical order, but the list should start with the number one.

1. First item
8. Second item
3. Third item
5. Fourth item 

Two cascaded lists written with indentations:

1. First item
2. Second item
3. Third item
    1. Indented item
    2. Indented item
4. Fourth item 

Unordered Lists

To create an unordered list, add dashes (-), asterisks (*), or plus signs (+) in front of line items. Indent one or more items to create a nested list.

- First item
* Second item
+ Third item 
  - Fourth item
  * Fifth item
  + Sixth item
- Seventh item

Adding Elements in Lists

To add another element in a list while preserving the continuity of the list, indent the element four spaces or one tab, as shown in the following examples.  
Paragraphs

*   This is the first list item.
*   Here's the second list item.

    I need to add another paragraph below the second list item.

*   And here's the third list item.

Blockquotes

*   This is the first list item.
*   Here's the second list item.

    > A blockquote would look great below the second list item.

*   And here's the third list item.



