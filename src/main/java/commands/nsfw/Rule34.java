package commands.nsfw;

import messages.Message;

import static commands.CommandList.cmdRule34;

import commands.Command;
import commands.CommandBuilder;
import commands.CommandType;
import eventInfo.MessageInfo;

public class Rule34 extends Invoker {
	static final String SITE = "rule34.xxx";
	private static final String IMAGE_SITE = "https://";
	
	public static Message rule34() {
		Message message = MessageInfo.message;
		String rawMessageContent = message.getContentRaw();
		String filterCommandOut = rawMessageContent.replace(cmdRule34.getCommand(), "").trim();
		String tags = filterCommandOut.replaceAll(" ", "+");
		return nsfw(SITE, tags, IMAGE_SITE);
	}

	public static Command updateCommand(String cmdSign, String adminCmdSign) throws NoSuchMethodException, SecurityException {
		return new CommandBuilder("rule34", cmdSign, "", CommandType.NSFW,
				"searches Rule34 for a random image with the given tag", "thisCommand (Tag(s) Here)")
				.addFirstExample("thisCommand Boobs Ass Blonde").addSecondExample("thisCommand Boobs")
				.addMethod(Rule34.class.getMethod("rule34")).build();
	}
}
