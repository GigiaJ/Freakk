package messages.messageEffects;

import static main.Main.settings;

import messages.MessageEffects;

public class ReverserEffect extends MessageEffects {
	public static StringBuilder reverseMessageEffect(StringBuilder messageToSend) {
		if (settings.getReverserStatus() == true) {
			return new StringBuilder(org.apache.commons.lang3.StringUtils.reverse(messageToSend.toString()));
		}
		return messageToSend;
	}
}