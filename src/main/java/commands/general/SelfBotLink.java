package commands.general;

import commands.Command;
import commands.CommandBuilder;
import commands.CommandType;
import messages.Message;


public class SelfBotLink {

	public static Message botLink() {
		return new Message("LINK");
	}

	public static Command updateCommand(String cmdSign, String adminCmdSign)
			throws NoSuchMethodException, SecurityException {
		return new CommandBuilder("link", cmdSign, "", CommandType.GENERAL,
				"provides a link to allow others to download the bot.", "thisCommand")
						.addMethod(SelfBotLink.class.getMethod("botLink")).build();
	}
}
