package commands.color;

import static main.Main.settings;

import commands.Command;
import commands.CommandBuilder;
import commands.CommandType;
import messages.Message;


public class AutoColor {
		public static Message autoColor() {
			settings = settings.debuild().setAutoColorStatus(!settings.getAutoColorStatus()).build();
			return null;
		}

		public static Command updateCommand(String cmdSign, String adminCmdSign)
				throws NoSuchMethodException, SecurityException {
			return new CommandBuilder("autocolor", cmdSign, "", CommandType.COLOR,
					"sets the color of your emb to whatever your role color is, regardless of server", "thisCommand").addMethod(AutoColor.class.getMethod("autoColor"))
					.build();
		}
}
