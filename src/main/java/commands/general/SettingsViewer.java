package commands.general;

import static main.Main.settings;

import commands.Command;
import commands.CommandBuilder;
import commands.CommandType;
import commands.color.ColorSetter;
import commands.color.colorchat.ChatColorSetter;
import main.VersionEnum;
import messages.Message;


public class SettingsViewer {
	public static Message settingsViewer() {
		
		String versionInfo = "Version";
		String autoColorActive = "Auto Color";
		String embActive = "Message Embing";
		String authorActive = "Author Embing";
		String fontActive = "Font Style";
		String fontSizeActive = "Font Size";
		String colorChatActive = "Color Chat";
		String colorCurrent = "Emb Color";
		String colorChatCurrent = "Chat Color";
		String commandSign = "Command Sign";
		String adminSign = "Admin Sign";

		// boolean mentionLogging = MessageEmber.logging;
		String version = VersionEnum.VERSION.getVersion();
		boolean autoColor = settings.getAutoColorStatus();
		boolean embMsg = settings.getEmbedMessageStatus();
		boolean authorEmb = settings.getAuthorEmbedStatus();
		int fontSize = settings.getCurrentFontSize();
		String font = settings.getCurrentFontStyle();
		boolean colorChat = settings.getColorChatStatus();
		String generalCmdSign = settings.getCommandSign();
		String adminCmdSign = settings.getAdminCommandSign();
		String hexColorEmb = "";
		
		if (ColorSetter.hexColor.isEmpty()) {
			hexColorEmb = "Not set";
		} else {
			hexColorEmb = ColorSetter.hexColor;
		}
		String hexColorChat = "";
		if (ChatColorSetter.hexColor.isEmpty()) {
			hexColorChat = "Not set";
		} else {
			hexColorChat = ChatColorSetter.hexColor;
		}
		String colorChatStatus = org.apache.commons.lang3.StringUtils.capitalize(Boolean.toString(colorChat));
		String autoColorStatus = org.apache.commons.lang3.StringUtils.capitalize(Boolean.toString(autoColor));
		String embMessageStatus = org.apache.commons.lang3.StringUtils.capitalize(Boolean.toString(embMsg));
		String authorEmbStatus = org.apache.commons.lang3.StringUtils.capitalize(Boolean.toString(authorEmb));

		String title = "**Bot Settings**";
		String settingsOfBot = versionInfo + ": " + version + "\n" + colorChatActive + ": " + colorChatStatus + "\n"
				+ autoColorActive + ": " + autoColorStatus + "\n" + embActive + ": " + embMessageStatus + "\n"
				+ authorActive + ": " + authorEmbStatus + "\n" + fontSizeActive + ": " + fontSize + "\n" + fontActive
				+ ": " + font + "\n" + colorCurrent + ": " + hexColorEmb + "\n" + colorChatCurrent + ": " + hexColorChat
				+ "\n" + commandSign + ": " + generalCmdSign + "\n" + adminSign + ": " + adminCmdSign + "\n";
	
		return new Message(settingsOfBot);
	}

	public static Command updateCommand(String cmdSign, String adminCmdSign)
			throws NoSuchMethodException, SecurityException {
		return new CommandBuilder("settings", cmdSign, "", CommandType.ADVANCED, "displays the settings of the bot.",
				"thisCommand").addMethod(SettingsViewer.class.getMethod("settingsViewer")).build();
	}

}
