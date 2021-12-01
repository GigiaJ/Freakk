package messages;

import commands.Command;
import commands.CommandList;
import commands.color.colorchat.EmojiFinder;
import eventInfo.MessageInfo;
import handler.CommandHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import macro.Macro;
import macro.Macros;
import main.JNA;
import main.Main;
import messages.messageEffects.ColorChatEffect;
import messages.messageEffects.NoSpaceEffect;
import messages.messageEffects.ReverserEffect;
import messages.messageEffects.SpongeBobChickenCapsEffect;
import messages.Message;

public class MessageEffects {
    private static final Pattern URL_PATTERN = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", 2);
    private static String messageContent = "";
    protected static String imageUrl = null;
    
    
    public MessageEffects() {
    }

    public static void triggerMessageEffects(String clipboardData) throws InterruptedException {
        messageContent = clipboardData;
        StringBuilder messageToSend = new StringBuilder(messageContent);
        handleMessageSending(messageToSend);

    }

    private static void handleMessageSending(StringBuilder messageToSend) {
    	if (!isCommandOrMacro(messageToSend.toString())) {
            if (EmojiFinder.emojiNames.isEmpty()) {
                EmojiFinder.populateEmojiList();
            }

            EmojiFinder.findEmojis(messageToSend);
            messageToSend = ReverserEffect.reverseMessageEffect(messageToSend);
            messageToSend = NoSpaceEffect.noSpaceEffect(messageToSend);
            messageToSend = SpongeBobChickenCapsEffect.spongeBobChickenCapsEffect(messageToSend);
            ColorChatEffect.colorChatEffect(messageToSend, MessageInfo.message);
        } else {
            new Message(messageToSend.toString()).send();
        }
    }
    

    public static void checkForImage(StringBuilder messageToBuild) {
        if (URL_PATTERN.matcher(messageContent).find()) {
            Matcher matcher = URL_PATTERN.matcher(messageToBuild);
            matcher.find();
            int start = matcher.start();
            int end = matcher.end();
            if (start > 0) {
                imageUrl = messageToBuild.subSequence(start, end).toString();
            }

            if (start == 0) {
                imageUrl = messageToBuild.subSequence(start, end).toString();
            }

            matcher = URL_PATTERN.matcher(messageToBuild);
            matcher.reset();
        }

    }

    public static boolean isCommandOrMacro(String stringToCheck) {
        for(int i = 0; i < CommandList.listOfCommands.size(); ++i) {
            if (stringToCheck.startsWith(((Command)CommandList.listOfCommands.get(i)).getCommand())) {
                return true;
            }
        }

        for(int i = 0; i < Macros.macros.size(); ++i) {
            if (stringToCheck.startsWith(CommandList.cmdSign.toString() + ((Macro)Macros.macros.get(i)).getName())) {
                return true;
            }
        }

        return false;
    }
}