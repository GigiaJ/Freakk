package commands.color.colorchat;

import static commands.CommandList.cmdFontSet;
import static main.Main.settings;

import commands.Command;
import commands.CommandBuilder;
import commands.CommandType;
import eventInfo.MessageInfo;
import messages.Message;


public class FontStyle {
	public static Message fontStyle() {
		Message message = MessageInfo.message;
		String rawMessageContent = message.getContentRaw();
		String filterCommandOut = rawMessageContent.replace(cmdFontSet.getCommand(), "").trim();
		settings = settings.debuild().setCurrentFontStyle(filterCommandOut).build();
		return null;
	}

	public static Command updateCommand(String cmdSign, String adminCmdSign)
			throws NoSuchMethodException, SecurityException {
		return new CommandBuilder("fontstyle", cmdSign, "", CommandType.COLOR, "sets the color chat font style.",
				"thisCommand (Font Style Here)").addFirstExample("thisCommand Comic Sans MS")
				.addSecondExample("thisCommand Arial").addMethod(FontStyle.class.getMethod("fontStyle")).build();
	}
}
