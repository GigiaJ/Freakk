package commands.general;

import static main.Main.settings;

import commands.Command;
import commands.CommandBuilder;
import commands.CommandType;
import messages.Message;


public class NoSpace {
	public static Message nospace() {
		settings = settings.debuild().setNoSpaceStatus(!settings.getNoSpaceStatus()).build();
		return null;
	}

	public static Command updateCommand(String cmdSign, String adminCmdSign)
			throws NoSuchMethodException, SecurityException {
		return new CommandBuilder("nospace", cmdSign, "", CommandType.GENERAL,
				"removes all spaces from your message.", "thisCommand")
				.addPermissionRequirements(new String[] {  })
						.addMethod(NoSpace.class.getMethod("nospace")).build();
	}
}

