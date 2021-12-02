/**
 * @author		GigiaJ
 * @filename	Message.java
 * @date		Nov 30, 2021
 * @description 
 */

package messages;

import java.io.File;
import java.util.ArrayList;
import main.ClipboardHandler;

public class Message {
	private String content;

	public Message(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}

	public String getContentRaw() {
		return this.getContent();
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void send() {
		PreventMessageEvent.unblockEnterKey();
		ClipboardHandler.getInstance().setClipboardContents(content);
		ClipboardHandler.getInstance().pasteFromClipboard();
		PreventMessageEvent.getBlockTask().cancel(true);
		PreventMessageEvent.blockEnterKey();
	}

	public void delete() {
		//DO NOTHING DONT SEND
	}

	/**
	 * @return
	 */
	public ArrayList<Object> getEmotes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void sendFile(File file) {
		PreventMessageEvent.unblockEnterKey();
		ClipboardHandler.getInstance().copyFile(file);
		ClipboardHandler.getInstance().pasteFromClipboard();
		PreventMessageEvent.getBlockTask().cancel(true);
		PreventMessageEvent.blockEnterKey();
	}

}
