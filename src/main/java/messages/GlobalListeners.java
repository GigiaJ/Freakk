package messages;

import static main.Main.settings;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;

import eventInfo.MessageInfo;
import lc.kra.system.keyboard.*;
import lc.kra.system.keyboard.event.*;
import lc.kra.system.mouse.GlobalMouseHook;
import lc.kra.system.mouse.event.GlobalMouseAdapter;
import lc.kra.system.mouse.event.GlobalMouseEvent;
import main.ClipboardHandler;
import main.JNA;

public class GlobalListeners {

	final static int DISCORD_CHANNEL_NAME_BAR_PIXEL_X = 1500;
	final static int DISCORD_CHANNEL_NAME_BAR_PIXEL_Y = -1064;

	final static int TEXTBOX_MESSAGE_M_PIXEL_TO_CHECK_X = 393;
	final static int TEXTBOX_MESSAGE_M_PIXEL_TO_CHECK_Y = -64;

	final static int EMOJI_BOX_POINT_X = 356;
	final static int EMOJI_BOX_POINT_Y = -86;

	final static Color DISCORD_CHANNEL_NAME_BAR_COLOR_LIGHT = new Color(255, 255, 255);
	final static Color DISCORD_CHANNEL_NAME_BAR_COLOR_DARK = new Color(54, 57, 63);

	final static Color MESSAGE_M_COLOR_GREY = new Color(72, 103, 119);
	final static Color MESSAGE_M_COLOR_TAN = new Color(246, 235, 229);

	final static Color AUTOCOMPLETE_BOX_COLOR_BLACK = new Color(47, 49, 54);
	final static Color AUTOCOMPLETE_BOX_COLOR_WHITE = new Color(234, 234, 235);

	private final static String LIGHT = "Light";
	private final static String DARK = "Dark";

	static boolean isWithinMessageBox = false;
	static boolean autoCompleteMenuOpen = false;
	private static String colorScheme = "";

	public static void keyboardListener() throws AWTException, InterruptedException {
		GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(false); // use false here to switch to hook instead of
																		// raw input
		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			@Override
			public void keyPressed(GlobalKeyEvent event) {
				Color messageMColorToCheck = null;
				Color autoCompleteMenuColor = null;
				if (colorScheme == LIGHT) {
					messageMColorToCheck = MESSAGE_M_COLOR_TAN;
					autoCompleteMenuColor = AUTOCOMPLETE_BOX_COLOR_WHITE;
				} else {
					messageMColorToCheck = MESSAGE_M_COLOR_GREY;
					autoCompleteMenuColor = AUTOCOMPLETE_BOX_COLOR_BLACK;
				}
				onKeyPressDo(messageMColorToCheck, autoCompleteMenuColor);
				if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE) {
				}
			}

			@Override
			public void keyReleased(GlobalKeyEvent event) {
				Color messageMColorToCheck = null;
				Color autoCompleteMenuColor = null;
				if (colorScheme == LIGHT) {
					messageMColorToCheck = MESSAGE_M_COLOR_TAN;
					autoCompleteMenuColor = AUTOCOMPLETE_BOX_COLOR_WHITE;
				} else {
					messageMColorToCheck = MESSAGE_M_COLOR_GREY;
					autoCompleteMenuColor = AUTOCOMPLETE_BOX_COLOR_BLACK;
				}
				onKeyPressDo(messageMColorToCheck, autoCompleteMenuColor);
			}
		});

	}

	public static void mouseListener() {
		GlobalMouseHook mouseHook = new GlobalMouseHook(); // add true to the constructor, to switch to raw input mode
		mouseHook.addMouseListener(new GlobalMouseAdapter() {
			@Override
			public void mousePressed(GlobalMouseEvent event) {
				onMousePressDo(event);
			}

			@Override
			public void mouseReleased(GlobalMouseEvent event) {
				// System.out.println("Mouse Released At: " + event);
				// String currentBounds = JNA.currentBounds();
				// System.out.println("Window Bounds: " + currentBounds);

			}

			@Override
			public void mouseMoved(GlobalMouseEvent event) {

			}

			@Override
			public void mouseWheel(GlobalMouseEvent event) {

			}

		});

	}

	static void onMousePressDo(GlobalMouseEvent event) {
		if (checkPixel(DISCORD_CHANNEL_NAME_BAR_PIXEL_X, DISCORD_CHANNEL_NAME_BAR_PIXEL_Y,
				DISCORD_CHANNEL_NAME_BAR_COLOR_LIGHT)) {
			colorScheme = LIGHT;
		} else {
			colorScheme = DARK;
		}
		// System.out.println("Mouse Press: " + String.valueOf(isWithinMessageBox));
		/*
		if (settings.getColorChatStatus() == true || settings.getAuthorEmbedStatus() == true
				|| settings.getEmbedMessageStatus() == true) {
			// DarkModeCheck and LightModeCheck
			isWithinMessageBox = checkIfWithinMessageBox(event);
			if (isWithinMessageBox == false) {
				// System.out.println("NOT IN BOX");
				PreventMessageEvent.unblockEnterKey();
			}
		}
		 **/
		if ((event.getButtons() & GlobalMouseEvent.BUTTON_LEFT) != GlobalMouseEvent.BUTTON_NO
				&& (event.getButtons() & GlobalMouseEvent.BUTTON_RIGHT) != GlobalMouseEvent.BUTTON_NO)
			// System.out.println("Both mouse buttons are currenlty pressed!");
			if (event.getButton() == GlobalMouseEvent.BUTTON_MIDDLE) {
			}
	}

	static void onKeyPressDo(Color messageMColorToCheck, Color autoCompleteMenuColor) {
		//System.out.println("Keyboard Press: " + String.valueOf(isWithinMessageBox));
			if (PreventMessageEvent.isDiscord()) {
				if (checkPixel(EMOJI_BOX_POINT_X, EMOJI_BOX_POINT_Y, autoCompleteMenuColor)) {
					autoCompleteMenuOpen = true;
					PreventMessageEvent.unblockEnterKey();
					// System.out.println("EMOJIBOX");
				} else {
					autoCompleteMenuOpen = false;
				}
				if (isWithinMessageBox == false) {
					PreventMessageEvent.unblockEnterKey();
					boolean pixelMatch = checkPixel(TEXTBOX_MESSAGE_M_PIXEL_TO_CHECK_X,
							TEXTBOX_MESSAGE_M_PIXEL_TO_CHECK_Y, messageMColorToCheck);
					if (pixelMatch == true) {
						isWithinMessageBox = false;
						// System.out.println("TEXTBOX");

					} else {
						isWithinMessageBox = true;
					}
				}

				if (isWithinMessageBox == true) {
					//if (autoCompleteMenuOpen == false) {
						//System.out.println("Keyboard Press2: " + String.valueOf(isWithinMessageBox));
						PreventMessageEvent.blockEnterKey();
					//}
				}
			}
	}

	public static boolean checkIfWithinMessageBox(GlobalMouseEvent event) {
		int BOTTOM_LEFT_CORNER_TEXTBOX_X_OFFSET = 375;
		int BOTTOM_LEFT_CORNER_TEXTBOX_Y_OFFSET = -36;
		int TOP_RIGHTT_CORNER_TEXTBOX_Y_OFFSET = -271;
		int TOP_RIGHT_CORNER_TEXTBOX_Y_OFFSET = -87;

		int[] bounds = getBounds();

		double bottomLeftCornerX = bounds[0] + BOTTOM_LEFT_CORNER_TEXTBOX_X_OFFSET;
		double bottomLeftCornerY = bounds[3] + BOTTOM_LEFT_CORNER_TEXTBOX_Y_OFFSET;
		double topRightCornerX = bounds[2] + TOP_RIGHTT_CORNER_TEXTBOX_Y_OFFSET;
		double topRightCornerY = bounds[3] + TOP_RIGHT_CORNER_TEXTBOX_Y_OFFSET;

		if ((event.getX() > bottomLeftCornerX && event.getX() < topRightCornerX)
				&& (event.getY() < bottomLeftCornerY && event.getY() > topRightCornerY)) {
			return true;
		}

		return false;

	}

	public static boolean checkPixel(int pixelXOffset, int pixelYOffset, Color colorToCheck) {
		try {
			int[] bounds = getBounds();
			// Location of the center of the message box where the blinking indicator
			// initially occurs
			int pixelX = bounds[0] + pixelXOffset;
			int pixelY = bounds[3] + pixelYOffset;

			Robot r = new Robot();

			// Normally screen capturing would be more efficient however for a single pixel
			// this can be done in about 10 ms or faster

			Color pixelColor = r.getPixelColor(pixelX, pixelY);

			// System.out.println("Pixel Position: " + pixelX + ", " + pixelY);
			// System.out.print(colorToCheck.toString());
			// System.out.println(pixelColor.toString());

			if (pixelColor.equals(colorToCheck)) {
				return true;
			}

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public static boolean checkMatchInLineOfPixels(int pixelXOffset, int pixelYOffset, Color colorToCheck,
			int amountToCheckX, int amountToCheckY) {
		try {
			int[] bounds = getBounds();
			// Location of the center of the message box where the blinking indicator
			// initially occurs
			int pixelX = bounds[0] + pixelXOffset;
			int pixelY = bounds[3] + pixelYOffset;

			Robot r = new Robot();

			// Normally screen capturing would be more efficient however for a single pixel
			// this can be done in about 10 ms or faster
			for (int i = 0; i < amountToCheckX + amountToCheckY; i++) {
				if (amountToCheckX > amountToCheckY) {
					if (i >= amountToCheckX) {
						break;
					}
				} else {
					if (i >= amountToCheckY) {
						break;
					}
				}
				int pixelToCheckX = 0, pixelToCheckY = 0;

				if (0 < amountToCheckX && amountToCheckY == 0) {
					pixelToCheckX = pixelX + i;
				} else {
					pixelToCheckX = pixelX + i;
				}
				if (0 < amountToCheckY && amountToCheckX == 0) {
					pixelToCheckY = pixelY + i;
				} else {
					pixelToCheckY = pixelY + (i * (amountToCheckY / amountToCheckX));
				}

				Color pixelColor = r.getPixelColor(pixelToCheckX, pixelToCheckY);

				// System.out.println("Pixel Position: " + pixelX + ", " + pixelY);
				// System.out.print(colorToCheck.toString());
				// System.out.println(pixelColor.toString());

				if (pixelColor.equals(colorToCheck)) {
					return true;
				}

			}

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public static int[] getBounds() {
		String currentBounds = JNA.currentBounds();
		int parenthesesCheck = currentBounds.indexOf("(");
		String getContents = currentBounds.substring(parenthesesCheck);
		parenthesesCheck = getContents.indexOf(")");
		String firstBounds = getContents.substring(1, parenthesesCheck);

		parenthesesCheck = currentBounds.lastIndexOf("(");
		getContents = currentBounds.substring(parenthesesCheck);
		String secondBounds = currentBounds.substring(parenthesesCheck + 1, currentBounds.lastIndexOf(")"));

		String firstBoundXString = firstBounds.split(",")[0];
		String firstBoundYString = firstBounds.split(",")[1];
		String secondBoundXString = secondBounds.split(",")[0];
		String secondBoundYString = secondBounds.split(",")[1];

		int firstBoundX = Integer.valueOf(firstBoundXString);
		int firstBoundY = Integer.valueOf(firstBoundYString);
		int secondBoundX = Integer.valueOf(secondBoundXString);
		int secondBoundY = Integer.valueOf(secondBoundYString);

		return new int[] { firstBoundX, firstBoundY, secondBoundX, secondBoundY };
	}
}
