package handler;

import static commands.CommandList.cmdNext;
import static commands.CommandList.cmdBack;
import static commands.CommandList.cmdSign;
import static main.Main.settings;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import commands.color.ColorSetter;
import commands.color.colorchat.ChatColorSetter;
import eventInfo.MessageInfo;
import macro.Macros;
import messages.MessageEffects;
import messages.Message;

public class CommandHandler extends MessageInfo {
	public static Color embedMessagingColor;
	public static Color chatColor;

	public static void handler() throws InterruptedException, ExecutionException {
		chatColor = ChatColorSetter.color;
		checkForCommands();
		checkForMacros();
		// Checks for any effects applied to the message and then resets the embed
		// settings so they can be used properly for the next message
		MessageEffects.triggerMessageEffects(message.getContent());
		
	}


	public static void checkForCommands() {
		for (int i = 0; i < commands.CommandList.listOfCommands.size(); i++) {
			if (message.getContentRaw().startsWith(commands.CommandList.listOfCommands.get(i).getCommand())) {

				Message commandSend = null;
				// If the command contains a method then it sets the message to the return of
				// the method
				if (commands.CommandList.listOfCommands.get(i).getMethod() != null) {
					try {
						commandSend = (Message) commands.CommandList.listOfCommands.get(i).getMethod()
								.invoke(new Object());						
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}

				if (commandSend != null) {
					message = commandSend;
				}
				if (commandSend == null) {
					message.delete();
				}
				main.SystemTrayManager.executeTray(false);
				bot.settings.SettingSaver.saveSettings();
			}
		}
	}

	private static void checkForMacros() {
		for (int i = 0; i < Macros.macros.size(); i++) {
			if (message.getContentRaw().startsWith(cmdSign + Macros.macros.get(i).getName())) {
				message.setContent(Macros.macros.get(i).getAction());
			}
		}
	}
}
