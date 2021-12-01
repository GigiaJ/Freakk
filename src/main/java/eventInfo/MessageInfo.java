package eventInfo;

import java.util.concurrent.ExecutionException;

import handler.CommandHandler;
import messages.Message;

public class MessageInfo {

	public static Message message = null;

	public static void MessageCreated(String s) {
		message = new Message(s);
		try {
			CommandHandler.handler();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
}