package messages.messageEffects;

import static main.Main.settings;

import messages.MessageEffects;

public class SpongeBobChickenCapsEffect extends MessageEffects {
	public static StringBuilder spongeBobChickenCapsEffect(StringBuilder messageToSend) {
		if (settings.getSpongebobChickenCapsStatus() == true) {
			StringBuilder text = new StringBuilder(org.apache.commons.lang3.StringUtils.lowerCase(messageToSend.toString()));
			int index = 0;
			for (int i = 1; i < text.length(); i++) {
				if (index < text.length() - 1) {
					text = text.replace(index, i, String.valueOf(text.charAt(index)).toUpperCase());
				}
				i++;
				index = index + 2;
			}
			return text;
		}
		return messageToSend;
	}
}