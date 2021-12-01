package commands.general;

import static main.Main.settings;

import commands.Command;
import commands.CommandBuilder;
import commands.CommandType;
import commands.CommandUpdater;
import commands.general.PrefixChange;
import eventInfo.MessageInfo;
import messages.Message;

public class PrefixChange {

	private static String commandSign = "";
	private static String adminCommandSign = "";
	public static String cmdAdmin = String.valueOf(commandSign) + String.valueOf(adminCommandSign);
	public static String cmdPrefixChange = commandSign + "prefix";


	public static void loadPrefixes() {
			commandSign = settings.getCommandSign();
			adminCommandSign = settings.getAdminCommandSign();
			cmdAdmin = commandSign + adminCommandSign;
			cmdPrefixChange = commandSign + "prefix";
	}

	public static Message prefixChange() throws Exception {
		String content = "";
		Message message = MessageInfo.message;
		String rawMessageContent = message.getContentRaw();
		// Sign Changing
		String filterCommandOut = rawMessageContent.replace(cmdPrefixChange + " ", "");
		if (filterCommandOut.startsWith("general")) {
			filterCommandOut = filterCommandOut.substring(8);
			if (filterCommandOut.length() > 1) {
				content = "The attempted sign change was too long please try again.";
				return new Message(content);
			}

			else if (filterCommandOut.length() < 1) {
				content = "The attempted sign change was too short please try again.";
				return new Message(content);

			} else {
				commandSign = filterCommandOut;
				cmdAdmin = String.valueOf(commandSign) + String.valueOf(adminCommandSign);
				cmdPrefixChange = commandSign + "prefix";
				content = "The new general command sign is " + commandSign + " if you wish to change it type "
						+ cmdPrefixChange + " general (newsign)";
				CommandUpdater.commands();
				return new Message(content);
			}
		} else {
			if (filterCommandOut.startsWith("admin")) {
				filterCommandOut = filterCommandOut.substring(6);
				if (filterCommandOut.length() > 1) {
					content = "The attempted sign change was too long please try again.";
					return new Message(content);
				}

				else if (filterCommandOut.length() < 1) {
					content = "The attempted sign change was too short please try again.";
					return new Message(content);
				} else {
					adminCommandSign = filterCommandOut;
					cmdAdmin = String.valueOf(commandSign) + String.valueOf(adminCommandSign);
					cmdPrefixChange = commandSign + "prefix";
					content = "The new admin command sign is " + adminCommandSign + " if you wish to change it type "
							+ cmdPrefixChange + " admin (newsign)";
					CommandUpdater.commands();
					return new Message(content);
				}

			}
		}
		return null;
	}

	public static Command updateCommand(String cmdSign, String adminCmdSign)
			throws NoSuchMethodException, SecurityException {
		return new CommandBuilder("prefix", cmdSign, "", CommandType.GENERAL,
				"changes the designated prefix to the user desired prefix with limits of one character.",
				"thisCommand (General/Admin) (New Prefix Here)").addFirstExample("thisCommand general $")
						.addSecondExample("thisCommand admin $").addMethod(PrefixChange.class.getMethod("prefixChange"))
						.build();

	}
}
