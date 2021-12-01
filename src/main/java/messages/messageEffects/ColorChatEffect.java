package messages.messageEffects;

import commands.color.colorchat.ColorChatImageCreator;
import handler.CommandHandler;
import main.Main;
import messages.MessageEffects;
import messages.Message;

public class ColorChatEffect extends MessageEffects {
    public ColorChatEffect() {
    }

    public static void colorChatEffect(StringBuilder messageToSend, Message message) {
        if (Main.settings.getColorChatStatus()) {
            if (message != null) {
            	/**
                for(int i = 0; i < message.getEmotes().size(); ++i) {
                    int firstIndex = messageToSend.indexOf("<:");
                    int secondIndex = messageToSend.indexOf(">", firstIndex);
                    int endOfEmoteName = messageToSend.indexOf(":", firstIndex + 2);
                    messageToSend.replace(firstIndex, firstIndex + 1, "");
                    messageToSend.replace(secondIndex - 1, secondIndex, "");
                    messageToSend.replace(endOfEmoteName, secondIndex - 1, "");
                }
                EMOJI HANDLING DONE HERE
                **/
            }

            try {
                ColorChatImageCreator.textToImage(messageToSend.toString(), Main.settings.getCurrentFontStyle(), Main.settings.getCurrentFontSize(), CommandHandler.chatColor);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (MessageEffects.imageUrl == null) {
                message.sendFile(Main.imageFile);
            }
        }
        else {
        	new Message(messageToSend.toString()).send();
        }
    }
}
