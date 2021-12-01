package commands.color.colorchat;

import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.HashMap;
import java.util.regex.Pattern;

public enum Formatting {
    BOLDITALICS(1),
    BOLD(2),
    STAR_ITALICS(3),
    UNDERLINEITALICS(4),
    UNDERLINE(5),
    UNDERSCORE_ITALICS(6),
    STRIKETHROUGH(7),
    EMOJI(8),
    CODEBLOCK(9);

    private Pattern pattern = null;
    private String string = "";
    private int formattingOffset = 0;
    private HashMap<Attribute, Object> textAttributes = new HashMap<Attribute, Object>();

    private Formatting(int value) {
        Pattern pattern = null;
        String string = "";
        int formattingOffset = 0;
        HashMap<Attribute, Object> textAttributes = new HashMap<Attribute, Object>();
        switch(value) {
        case 1:
            pattern = Pattern.compile("\\*\\*\\*(.+?)\\*\\*\\*");
            string = "***";
            formattingOffset = 3;
            textAttributes.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
            textAttributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
            break;
        case 2:
            pattern = Pattern.compile("(?<!\\*)\\*\\*[^\\*](.*?)[^\\*]\\*\\*(?!\\*)");
            string = "**";
            formattingOffset = 2;
            textAttributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
            break;
        case 3:
            pattern = Pattern.compile("(?<!\\*)\\*[^\\*](.*?)[^\\*]\\*(?!\\*)");
            string = "*";
            formattingOffset = 1;
            textAttributes.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
            break;
        case 4:
            pattern = Pattern.compile("___(.+?)___");
            string = "___";
            formattingOffset = 3;
            textAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            textAttributes.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
            break;
        case 5:
            pattern = Pattern.compile("(?<!_)__[^___](.*?)__(?!_)");
            string = "__";
            formattingOffset = 2;
            textAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            break;
        case 6:
            pattern = Pattern.compile("(?<!_)_(.?+)_(?!_)");
            string = "_";
            formattingOffset = 1;
            textAttributes.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
            break;
        case 7:
            pattern = Pattern.compile("~~(.+?)~~");
            string = "~~";
            formattingOffset = 2;
            textAttributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
            break;
        case 8:
            pattern = Pattern.compile(":(.+?):");
            string = ":";
            formattingOffset = 1;
            break;
        case 9:
            pattern = Pattern.compile("```(.+?)```");
            string = "```";
            formattingOffset = 3;
            break;
        default:
            pattern = null;
            string = "";
            formattingOffset = 0;
        }

        this.pattern = pattern;
        this.string = string;
        this.formattingOffset = formattingOffset;
        this.textAttributes = textAttributes;
    }

    public Pattern getPattern() {
        return this.pattern;
    }

    public String getString() {
        return this.string;
    }

    public int getFormattingOffset() {
        return this.formattingOffset;
    }

    public HashMap<Attribute, Object> getTextAttributes() {
        return this.textAttributes;
    }
}
