package commands.general;

import static main.Main.settings;

import commands.Command;
import commands.CommandBuilder;
import commands.CommandType;
import messages.Message;


public class AuthorEmbeding {
	public static Message authorEmbedMessage() {
		settings = settings.debuild().setEmbedMessageStatus(false).build();
		settings = settings.debuild().setAuthorEmbedStatus(!settings.getAuthorEmbedStatus()).build();
		return null;
	}

	public static Command updateCommand(String cmdSign, String adminCmdSign)
			throws NoSuchMethodException, SecurityException {
		return new CommandBuilder("authoremb", cmdSign, "", CommandType.GENERAL,
				"will toggle embing messages on or off with your icon and name at the top.", "thisCommand")
						.addMethod(AuthorEmbeding.class.getMethod("authorEmbedMessage")).build();
	}
}
