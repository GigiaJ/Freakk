package commands.general;

import static main.Main.settings;

import commands.Command;
import commands.CommandBuilder;
import commands.CommandType;
import messages.Message;


public class Reverser {
	public static Message reverser() {
		settings = settings.debuild().setReverserStatus(!settings.getReverserStatus()).build();
		return null;
	}

	public static Command updateCommand(String cmdSign, String adminCmdSign)
			throws NoSuchMethodException, SecurityException {
		return new CommandBuilder("reverser", cmdSign, "", CommandType.GENERAL,
				"reverses your messages if you have either of the message embedding commands active.", "thisCommand")
				.addPermissionRequirements(new String[] {  })
						.addMethod(Reverser.class.getMethod("reverser")).build();
	}
}

