package commands.general;

import static main.Main.settings;

import commands.Command;
import commands.CommandBuilder;
import commands.CommandType;
import messages.Message;


public class SpongeBobChickenCaps {
	public static Message spongeBobChickenCaps() {
		settings = settings.debuild().setSpongeBobChickenCapStatus(!settings.getSpongebobChickenCapsStatus()).build();
		return null;
	}

	public static Command updateCommand(String cmdSign, String adminCmdSign)
			throws NoSuchMethodException, SecurityException {
		return new CommandBuilder("spongebobderp", cmdSign, "", CommandType.GENERAL,
				"converts your message to the goofy spongebob meme atrocity.", "thisCommand")
				.addPermissionRequirements(new String[] {  })
						.addMethod(SpongeBobChickenCaps.class.getMethod("spongeBobChickenCaps")).build();
	}
}

