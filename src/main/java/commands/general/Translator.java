package commands.general;

import static main.Main.settings;

import commands.Command;
import commands.CommandBuilder;
import commands.CommandType;
import messages.Message;


public class Translator {
	public static Message translateMessages() {
		settings = settings.debuild().setEmbedMessageStatus(false).build();
		settings = settings.debuild().setTranslatorStatus(!settings.getTranslatorStatus()).build();
		return null;
	}

	public static Command updateCommand(String cmdSign, String adminCmdSign)
			throws NoSuchMethodException, SecurityException {
		return new CommandBuilder("translator", cmdSign, "", CommandType.GENERAL,
				"will toggle translations of messages on or off with your configured settings.", "thisCommand")
						.addMethod(Translator.class.getMethod("translateMessages")).build();
	}
}
