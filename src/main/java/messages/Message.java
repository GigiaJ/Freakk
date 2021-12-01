/**
 * @author		GigiaJ
 * @filename	Message.java
 * @date		Nov 30, 2021
 * @description 
 */

package messages;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import screenCapturer.CopyToClipBoard;

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
		Robot robot;
		try {
			robot = new Robot();
	        robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyRelease(KeyEvent.VK_V);
	        robot.keyPress(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delete() {
		this.content = "";
		//DONT SEND IT
	}

	/**
	 * @return
	 */
	public ArrayList<Object> getEmotes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void sendFile(File file) {
		CopyToClipBoard clipboardCopier = new CopyToClipBoard();
		clipboardCopier.copyFile(file);
		Robot robot;
		try {
			robot = new Robot();
	        robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_V);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyRelease(KeyEvent.VK_V);
	        robot.keyPress(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
