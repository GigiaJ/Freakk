package commands.general;

import bot.settings.SettingSaver;
import commands.Command;
import commands.CommandBuilder;
import commands.CommandType;
import messages.Message;


public class CloseBot {
	public synchronized static Message closeBot() {
		SettingSaver.saveSettings();
		Thread t = new Thread() {
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.exit(0);
			}
		};
		t.start();
		return null;
	}

	public static Command updateCommand(String cmdSign, String adminCmdSign)
			throws NoSuchMethodException, SecurityException {
		return new CommandBuilder("close", cmdSign, "", CommandType.GENERAL, "will shut down the bot.", "thisCommand")
				.addMethod(CloseBot.class.getMethod("closeBot")).build();
	}
}