package messages.messageEffects;

import static main.Main.settings;

import messages.MessageEffects;

public class NoSpaceEffect extends MessageEffects {
	public static StringBuilder noSpaceEffect(StringBuilder messageToSend) {
		if (settings.getNoSpaceStatus() == true) {
			return new StringBuilder(org.apache.commons.lang3.StringUtils.deleteWhitespace(messageToSend.toString()));
		}
		return messageToSend;
	}
}
