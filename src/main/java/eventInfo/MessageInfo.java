package eventInfo;

import messages.Message;

public class MessageInfo {

	public static Message message = null;

	public synchronized static void MessageCreated(String s) {
		message = new Message(s);
	}

	public synchronized static Message getMessage() {
		return message;
	}
}