package commands.color.colorchat;

public class ColorChatEmoji {
    private int startPos;
    private String emojiUrl;
    private String emojiName;

    public ColorChatEmoji(int startPos, String emojiUrl, String emojiName) {
        this.startPos = startPos;
        this.emojiUrl = emojiUrl;
        this.emojiName = emojiName;
    }

    public int getStartPos() {
        return this.startPos;
    }

    public void setStartPos(int beginPos) {
        this.startPos = beginPos;
    }

    public String getEmojiUrl() {
        return this.emojiUrl;
    }

    public void setEmojiUrl(String emojiUrl) {
        this.emojiUrl = emojiUrl;
    }

    public String getEmojiName() {
        return this.emojiName;
    }

    public void setEmojiName(String emojiName) {
        this.emojiName = emojiName;
    }
}
