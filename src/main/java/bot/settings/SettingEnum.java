package bot.settings;

public enum SettingEnum {
	VERSION(0), AUTOCOLORSTATUS(1), COLORCHATSTATUS(2), EMBEDMESSAGE(3), AUTHOREMBED(4), REVERSER(5), NOSPACE(6), SPONGEBOBCAPS(7), CHAT_COLOR(8), EMBED_COLOR(9), FONTSTYLE(10), FONTSIZE(11), NSFWFILTER(
					12), COMMANDSIGN(13), ADMINCOMMANDSIGN(14), BANNED_WORD_LIST(15), MACROS(16);

	private int value;

	SettingEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
