package commands.color.colorchat;

import static commands.CommandList.cmdFontSize;
import static main.Main.settings;

import commands.Command;
import commands.CommandBuilder;
import commands.CommandType;
import eventInfo.MessageInfo;
import messages.Message;


public class FontSize {
	public static Message fontSize() {
		Message message = MessageInfo.message;
		String rawMessageContent = message.getContentRaw();
		String filterCommandOut = rawMessageContent.replace(cmdFontSize.getCommand(), "").trim();
		settings = settings.debuild().setCurrentFontSize(Integer.valueOf(filterCommandOut)).build();
		return null;
	}

	public static Command updateCommand(String cmdSign, String adminCmdSign)
			throws NoSuchMethodException, SecurityException {
		return new CommandBuilder("fontsize", cmdSign, "", CommandType.COLOR,
				"sets the color chat font size. (Max of 50)", "thisCommand (Font Size Here)")
						.addFirstExample("thisCommand 40").addSecondExample("thisCommand 16")
						.addMethod(FontSize.class.getMethod("fontSize")).build();
	}
}
