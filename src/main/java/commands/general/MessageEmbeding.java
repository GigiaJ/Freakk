package commands.general;

import static main.Main.settings;

import commands.Command;
import commands.CommandBuilder;
import commands.CommandType;
import messages.Message;


public class MessageEmbeding {
	public static Message embedMessage() {
		settings = settings.debuild().setAuthorEmbedStatus(false).build();
		settings = settings.debuild().setEmbedMessageStatus(!settings.getEmbedMessageStatus()).build();
		return null;
	}

	public static Command updateCommand(String cmdSign, String adminCmdSign)
			throws NoSuchMethodException, SecurityException {
		return new CommandBuilder("embmsg", cmdSign, "", CommandType.GENERAL, "will toggle embing messages on or off.",
				"thisCommand")
						.addMethod(MessageEmbeding.class.getMethod("embedMessage")).build();
	}
}
