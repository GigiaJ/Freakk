package bot.settings;

import java.util.ArrayList;

import macro.Macro;

public class Setting {
	protected final String currentVersion;
	protected final String currentCommandSign;
	protected final String currentAdminCommandSign;
	protected final boolean colorChatStatus;
	protected final boolean autoColorStatus;
	protected final boolean embedMessageStatus;
	protected final boolean authorEmbedStatus;
	protected final boolean reverserStatus;
	protected final boolean noSpaceStatus;
	protected final boolean spongeBobChickenCapsStatus;
	protected final String colorChatColor;
	protected final String embedColor;
	protected final String currentFontStyle;
	protected final int currentFontSize;
	protected final boolean[] nsfwFilters;
	protected final ArrayList<Macro> macros;

	public Setting(SettingBuilder builder) {
		currentVersion = builder.currentVersion;
		currentCommandSign = builder.currentCommandSign;
		currentAdminCommandSign = builder.currentAdminCommandSign;
		colorChatStatus = builder.colorChatStatus;
		autoColorStatus = builder.autoColorStatus;
		embedMessageStatus = builder.embedMessageStatus;
		authorEmbedStatus = builder.authorEmbedStatus;
		reverserStatus = builder.reverserStatus;
		noSpaceStatus = builder.noSpaceStatus;
		spongeBobChickenCapsStatus = builder.spongeBobChickenCapsStatus;
		colorChatColor = builder.colorChatColor;
		embedColor = builder.embedColor;
		currentFontStyle = builder.currentFontStyle;
		currentFontSize = builder.currentFontSize;
		nsfwFilters = builder.nsfwFilters;
		macros = builder.macros;
	}
	
	public String getCurrentVersion() {
		return currentVersion;
	}

	public String getCommandSign() {
		return currentCommandSign;
	}

	public String getAdminCommandSign() {
		return currentAdminCommandSign;
	}

	public boolean getColorChatStatus() {
		return colorChatStatus;
	}

	public boolean getAutoColorStatus() {
		return autoColorStatus;
	}

	public boolean getEmbedMessageStatus() {
		return embedMessageStatus;
	}

	public boolean getAuthorEmbedStatus() {
		return authorEmbedStatus;
	}

	public boolean getReverserStatus() {
		return reverserStatus;
	}
	
	public boolean getNoSpaceStatus() {
		return noSpaceStatus;
	}
	
	public boolean getSpongebobChickenCapsStatus() {
		return spongeBobChickenCapsStatus;
	}
	
	public String getColorChatColor() {
		return colorChatColor;
	}

	public String getEmbedColor() {
		return embedColor;
	}

	public String getCurrentFontStyle() {
		return currentFontStyle;
	}

	public int getCurrentFontSize() {
		return currentFontSize;
	}

	public boolean[] getNsfwFilters() {
		return nsfwFilters;
	}


	public ArrayList<Macro> getMacros() {
		return macros;
	}

	
	public SettingBuilder debuild() {
		return new SettingBuilder(this);
	}



}
