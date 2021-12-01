package commands.color.colorchat;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import javax.imageio.ImageIO;
import main.Main;
import org.apache.commons.lang3.text.WordUtils;

public class ColorChatImageCreator {
    private static File file = null;
    private static final int IMAGE_WIDTH = 400;

    public ColorChatImageCreator() {
    }

    public static void textToImage(String text, String fontStyle, Integer fontSize, Color color) throws Exception {
        int emojiUrlIndex = 0;
        file = Main.imageFile;
        File.createTempFile("Text", "png");
        file.deleteOnExit();
        BufferedImage img = new BufferedImage(1, 1, 2);
        Graphics2D g2d = img.createGraphics();
        Font setFont = new Font(text, 0, fontSize);
        g2d.setFont(setFont);
        FontMetrics fm = g2d.getFontMetrics();
        int height = fm.getHeight();
        WrappedFormattedStrings formattedStrings = createFormatting(text, fm, fontSize, 400);
        List<String> lines = formattedStrings.getStrings();
        g2d.dispose();
        int numberOfLines = lines.size();
        height = numberOfLines * height;
        int heightSet = height / numberOfLines - (fm.getAscent() + fm.getDescent() + fm.getLeading() - fontSize);
        img = new BufferedImage(400, height, 2);

        for(int l = 1; !lines.isEmpty(); ++l) {
            int linePos = l * heightSet;
            g2d = createGraphic(img);
            String formatString = ((String)lines.get(0)).toString();
            AttributedString attributeString = new AttributedString(formatString);
            if (formatString.length() > 0) {
                attributeString.addAttribute(TextAttribute.SIZE, fontSize);
                attributeString.addAttribute(TextAttribute.FOREGROUND, color);
                attributeString.addAttribute(TextAttribute.FAMILY, fontStyle);
            }

            Iterator iter;
            int count;
            if (!formattedStrings.getFormattedTextPerLine().isEmpty()) {
                iter = ((ArrayList)formattedStrings.getFormattedTextPerLine().get(l - 1)).iterator();

                while(iter.hasNext()) {
                    Format format = (Format)iter.next();

                    for(count = 0; count < format.getFormat().size(); ++count) {
                        if (format.adjustedEndPos <= formatString.length() && format.adjustedStartPos >= 0 && format.adjustedStartPos < format.adjustedEndPos) {
                            attributeString.addAttributes(((Formatting)format.getFormat().get(count)).getTextAttributes(), format.adjustedStartPos, format.adjustedEndPos);
                        }
                    }
                }
            }

            AttributedCharacterIterator messageIterator = attributeString.getIterator();
            g2d.drawString(messageIterator, 0, linePos);
            g2d.dispose();
            g2d = img.createGraphics();
            if (!formattedStrings.getFormattedTextPerLine().isEmpty()) {
                iter = ((ArrayList)formattedStrings.getFormattedTextPerLine().get(l - 1)).iterator();
                count = formatString.split("   ").length;

                label76:
                while(true) {
                    Format format;
                    int emojiHorizontalShift;
                    do {
                        if (!iter.hasNext()) {
                            break label76;
                        }

                        format = (Format)iter.next();
                        emojiHorizontalShift = 0;
                    } while(!format.getFormat().contains(Formatting.EMOJI));

                    String urlStr = ((ColorChatEmoji)EmojiFinder.colorChatEmojis.get(emojiUrlIndex)).getEmojiUrl();
                    ++emojiUrlIndex;
                    URL url = new URL(urlStr);
                    int imageSize = (fm.getHeight() + fontSize) / fontSize + fontSize;
                    HttpURLConnection pageRequest = (HttpURLConnection)url.openConnection();
                    pageRequest.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
                    pageRequest.connect();
                    BufferedImage emojiImage = ImageIO.read(pageRequest.getInputStream());
                    BufferedImage newImage = new BufferedImage(emojiImage.getWidth(), emojiImage.getHeight(), 2);
                    int emojiWidth = imageSize - emojiImage.getHeight() / emojiImage.getWidth();
                    int emojiHeight = fontSize;
                    Graphics2D g = createGraphic(newImage);
                    g.drawImage(emojiImage, 0, 0, emojiWidth, emojiHeight, (ImageObserver)null);

                    for(int i = 0; i <= formatString.split("   ").length - count; ++i) {
                        if (i > 0) {
                            emojiHorizontalShift += fm.stringWidth(formatString.split("   ")[i]) + fm.stringWidth("   ");
                        } else {
                            emojiHorizontalShift += fm.stringWidth(formatString.split("   ")[i]);
                        }
                    }

                    --count;
                    g2d.drawImage(newImage, emojiHorizontalShift, linePos - imageSize + 2, (ImageObserver)null);
                    iter.remove();
                    g.dispose();
                    pageRequest.disconnect();
                }
            }

            g2d.dispose();
            lines.remove(0);
        }

        try {
            ImageIO.write(img, "png", file);
        } catch (IOException var33) {
            var33.printStackTrace();
        }

    }

    public static Graphics2D createGraphic(BufferedImage image) {
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        return g;
    }

    public static WrappedFormattedStrings createFormatting(String text, FontMetrics fm, Integer fontSize, int imageWidth) {
        String removeFormatting = text.replaceAll(Formatting.STRIKETHROUGH.getString(), "").replaceAll(Formatting.UNDERSCORE_ITALICS.getString(), "").replace(Formatting.STAR_ITALICS.getString(), "").replaceAll(Formatting.EMOJI.getPattern().toString(), "   ").replace(Formatting.CODEBLOCK.getString(), "");
        ArrayList<ArrayList<Format>> listOfFormattedText = new ArrayList();
        ArrayList<Format> assignedFormatting = assignFormatting(text);
        int lineSplitLength = 400 / (fontSize / 2);
        String wrappedText = WordUtils.wrap(removeFormatting, lineSplitLength, "\n", true);
        List<String> list = new ArrayList();
        String[] splitText = wrappedText.split("\n");
        String[] var14 = splitText;
        int i = splitText.length;

        int displacement;
        for(displacement = 0; displacement < i; ++displacement) {
            String line = var14[displacement];
            list.add(line);
        }

        if (!assignedFormatting.isEmpty()) {
            int totalLineLength = 0;
            displacement = 0;

            for(i = 0; i < splitText.length; ++i) {
                ArrayList<Format> lineFormatting = new ArrayList();
                Iterator<Format> itr = assignedFormatting.iterator();
                boolean firstFormat = true;

                while(itr.hasNext()) {
                    int lastLineLength = 0;
                    if (i > 0) {
                        lastLineLength = splitText[i - 1].length();
                    } else {
                        lastLineLength = splitText[i].length();
                    }

                    Format format = (Format)itr.next();
                    if (format.getAdjustedStartPos() - displacement <= totalLineLength + lastLineLength) {
                        format.setAdjustedStartPos(format.getAdjustedStartPos() - displacement - totalLineLength);
                        format.setAdjustedEndPos(format.getAdjustedEndPos() - displacement - totalLineLength);
                        if (firstFormat) {
                            firstFormat = false;
                            if (splitText[i].contains(format.getText()) && splitText[i].indexOf(format.getText()) != format.getAdjustedStartPos()) {
                                format.setAdjustedStartPos(format.getAdjustedStartPos() - 1);
                                format.setAdjustedEndPos(format.getAdjustedEndPos() - 1);
                                ++displacement;
                            }

                            if (splitText[i].contains("   ") && splitText[i].indexOf("   ") != format.getAdjustedStartPos() && format.getFormat().contains(Formatting.EMOJI)) {
                                format.setAdjustedStartPos(format.getAdjustedStartPos() - 1);
                                format.setAdjustedEndPos(format.getAdjustedEndPos() - 1);
                                ++displacement;
                            }
                        }

                        lineFormatting.add(format);
                        displacement += format.getDisplacement();
                        itr.remove();
                    }
                }

                totalLineLength += splitText[i].length();
                listOfFormattedText.add(lineFormatting);
            }
        }

        WrappedFormattedStrings formattedStrings = new WrappedFormattedStrings(listOfFormattedText, list);
        return formattedStrings;
    }

    public static ArrayList<Format> assignFormatting(String text) {
        ArrayList<Format> formattedTextList = new ArrayList();
        Matcher matcher = null;
        Formatting[] var6;
        int var5 = (var6 = Formatting.values()).length;

        label46:
        for(int var4 = 0; var4 < var5; ++var4) {
            Formatting format = var6[var4];
            matcher = format.getPattern().matcher(text);

            while(true) {
                while(true) {
                    if (!matcher.find()) {
                        continue label46;
                    }

                    Format formattedText = new Format(matcher.group(0), format, matcher.start(), matcher.end());
                    if (!formattedTextList.isEmpty()) {
                        formattedTextList.add(formattedText);

                        for(int i = 0; i < formattedTextList.size(); ++i) {
                            if (((Format)formattedTextList.get(i)).isAlreadyFormatted(matcher.start(), matcher.end()) && !((Format)formattedTextList.get(i)).equals(formattedText)) {
                                formattedTextList.set(i, ((Format)formattedTextList.get(i)).reformat(matcher.start(), matcher.end(), format));
                            }

                            if (((Format)formattedTextList.get(i)).getAdjustedStartPos() == formattedText.getAdjustedStartPos() && !((Format)formattedTextList.get(i)).equals(formattedText)) {
                                formattedTextList.remove(formattedText);
                            }
                        }
                    } else {
                        formattedTextList.add(formattedText);
                    }
                }
            }
        }

        Collections.sort(formattedTextList, (format1, format2) -> {
            return format1.getAdjustedStartPos() - format2.getAdjustedStartPos();
        });
        return formattedTextList;
    }

    protected static class WrappedFormattedStrings {
        protected ArrayList<ArrayList<Format>> formattedTextPerLine;
        protected List<String> strings;
        protected List<Integer> lineLengths;

        public List<String> getStrings() {
            return this.strings;
        }

        public void setStrings(List<String> strings) {
            this.strings = strings;
        }

        WrappedFormattedStrings(ArrayList<ArrayList<Format>> formattedTextPerLine, List<String> list) {
            this.formattedTextPerLine = formattedTextPerLine;
            this.strings = list;
        }

        public ArrayList<ArrayList<Format>> getFormattedTextPerLine() {
            return this.formattedTextPerLine;
        }

        public void setFormattedTextPerLine(ArrayList<ArrayList<Format>> formattedTextPerLine) {
            this.formattedTextPerLine = formattedTextPerLine;
        }
    }
}
