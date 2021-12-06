package commands;

import static main.Main.settings;

import java.util.ArrayList;

public class CommandUpdater extends CommandList {

	public static void commands() throws Exception {
		listOfCommands = new ArrayList<Command>();

		cmdSign = new StringBuilder(settings.getCommandSign()).toString();
		adminCmdSign = new StringBuilder(settings.getAdminCommandSign()).toString();		
				
		cmdClose = commands.general.CloseBot.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdClose);

		cmdPrefixChange = commands.general.PrefixChange.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdPrefixChange);

		cmdMsgEmb = commands.general.MessageEmbeding.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdMsgEmb);

		cmdTranslator = commands.general.Translator.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdTranslator);

		cmdLink = commands.general.SelfBotLink.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdLink);
		
		cmdViewSettings = commands.general.SettingsViewer.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdViewSettings);
		
		cmdReverser = commands.general.Reverser.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdReverser);
		
		cmdNoSpace = commands.general.NoSpace.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdNoSpace);
		
		cmdSpongeBobChickenCaps = commands.general.SpongeBobChickenCaps.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdSpongeBobChickenCaps);

//		 Color commands
		cmdAutoColor = commands.color.AutoColor.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdAutoColor);

		cmdSetEmbedColor = commands.color.ColorSetter.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdSetEmbedColor);

		cmdSetChatColor = commands.color.colorchat.ChatColorSetter.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdSetChatColor);

		cmdColorChat = commands.color.colorchat.ColorChat.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdColorChat);

		cmdFontSet = commands.color.colorchat.FontStyle.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdFontSet);

		cmdFontSize = commands.color.colorchat.FontSize.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdFontSize);

//		 NSFW commands
		cmdTBIB = commands.nsfw.TBIB.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdTBIB);

		cmdGelbooru = commands.nsfw.Gelbooru.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdGelbooru);

		cmdRule34 = commands.nsfw.Rule34.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdRule34);

		cmdFilterNSFW = commands.nsfw.FilterManager.updateCommand(cmdSign, adminCmdSign);
		listOfCommands.add(cmdFilterNSFW);

		
	}
}