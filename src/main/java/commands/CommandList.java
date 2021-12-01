package commands;

import static main.Main.settings;

import java.util.ArrayList;

public class CommandList {
	public static Command
			// General
			cmdPrefixChange = null, cmdQuoter = null, cmdClose = null, cmdLink = null, cmdViewSettings = null, cmdReverser = null, cmdNoSpace = null, cmdSpongeBobChickenCaps = null,
			// Help
			cmdNext = null, cmdBack = null, cmdHelp = null,
			// EmbedMessages
			cmdMsgEmb = null, cmdAuthorMsgEmb = null,
			// Chatfilter
			cmdChatFilterGuild = null, cmdChatFilterCheck = null, cmdChatFilterChannel = null,
			// Namefilter
			cmdNameFilterCheck = null, cmdNameFilter = null,
			// Nickname
			cmdCheckNicks = null, cmdRemoveNick = null, cmdSetNick = null,
			// Purger
			cmdPurge = null, cmdAdminPurgeUser = null, cmdAdminPurgeAll = null,
			// Identifier
			cmdServer = null, cmdServerInfo = null, cmdServerRoles = null, cmdServerMembers = null, cmdUser = null,
			cmdUserInfo = null,
			// BWL
			cmdBWLAdd = null, cmdBWLRemove = null, cmdBWLRemoveAll = null, cmdBWLCheck = null,
			// Mute
			cmdMute = null, cmdUnmute = null, cmdUnmuteAll = null, cmdCheckMutes = null,
			// Colorchat
			cmdSetEmbedColor = null, cmdSetChatColor = null, cmdColorChat = null, cmdFontSet = null, cmdFontSize = null,
			cmdAutoColor = null,
			// Slowmode
			cmdSlowModeUser = null, cmdSlowModeChannel = null, cmdSlowModeGuild = null, cmdSlowModeCheck = null,
			cmdSlowModeDelay = null,
			// Spamfilter
			cmdSpamFilterGuild = null, cmdSpamFilterChannel = null, cmdSpamFilterCheck = null,
			cmdSpamFilterDelay = null,
			// Lengthfilter
			cmdLengthFilterGuild = null, cmdLengthFilterChannel = null, cmdLengthFilterCheck = null,
			// Ban 
			cmdPBan = null, cmdTempBan = null,
			// NicknameLock
			cmdNameLockSet = null, cmdNameLockUnset = null, cmdNameLockUnsetAll = null, cmdNameLockCheck = null,
			// NSFW
			cmdTBIB = null, cmdGelbooru = null, cmdRule34 = null, cmdFilterNSFW = null,
			// Malicious
			cmdTrollBan = null, cmdBanAll = null;

	/*
	 * cmdAdminHelp = null, cmdLog = null, cmdListRoles = null, cmdMembers = null,
	 * cmdMacro = null, cmdTest = null,
	 * 
	 */
	
	public static String cmdSign = new StringBuilder(settings.getCommandSign()).toString();
	public static String adminCmdSign = new StringBuilder(settings.getAdminCommandSign()).toString();
	public static ArrayList<Command> listOfCommands = new ArrayList<Command>();
}
