package commands.nsfw;

import messages.Message;


import static commands.CommandList.cmdGelbooru;

import commands.Command;
import commands.CommandBuilder;
import commands.CommandType;
import eventInfo.MessageInfo;

public class Gelbooru extends Invoker {
	private static final String SITE = "gelbooru.com";
	private static final String IMAGE_SITE = "https://";
	public static Message gelbooru() {
		Message message = MessageInfo.message;
		String rawMessageContent = message.getContentRaw();
		String filterCommandOut = rawMessageContent.replace(cmdGelbooru.getCommand(), "").trim();
		String tags = filterCommandOut.replaceAll(" ", "+");
		return nsfw(SITE, tags, IMAGE_SITE);
	}

	public static Command updateCommand(String cmdSign, String adminCmdSign) throws NoSuchMethodException, SecurityException {
		return new CommandBuilder("gelbooru", cmdSign, "", CommandType.NSFW,
				"searches Gelbooru for a random image with the given tag", "thisCommand (Tag(s) Here)")
				.addFirstExample("thisCommand Boobs Ass Blonde").addSecondExample("thisCommand Boobs")
				.addMethod(Gelbooru.class.getMethod("gelbooru")).build();
	}
}
