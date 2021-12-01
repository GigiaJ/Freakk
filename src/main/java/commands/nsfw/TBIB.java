package commands.nsfw;

import messages.Message;

import static commands.CommandList.cmdTBIB;

import commands.Command;
import commands.CommandBuilder;
import commands.CommandType;
import eventInfo.MessageInfo;

public class TBIB extends Invoker {
	private static final String SITE = "tbib.org";
	private static final String IMAGE_SITE = "//";
	public static Message tbib() {
		Message message = MessageInfo.message;
		String rawMessageContent = message.getContentRaw();
		String filterCommandOut = rawMessageContent.replace(cmdTBIB.getCommand(), "").trim();
		String tags = filterCommandOut.replaceAll(" ", "+");
		return nsfw(SITE, tags, IMAGE_SITE);
	}
	
	public static Command updateCommand(String cmdSign, String adminCmdSign) throws NoSuchMethodException, SecurityException {
		return new CommandBuilder("tbib", cmdSign, "", CommandType.NSFW,
				"searches TBIB for a random image with the given tag", "thisCommand (Tag(s) Here)")
				.addFirstExample("thisCommand Boobs Ass Blonde").addSecondExample("thisCommand Boobs")
				.addMethod(TBIB.class.getMethod("tbib")).build();
	}
}
