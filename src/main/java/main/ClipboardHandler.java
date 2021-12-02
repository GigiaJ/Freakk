package main;

import screenCapturer.CopyToClipBoard;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.*;

public final class ClipboardHandler extends CopyToClipBoard {

  private static Robot robot;
  private static final ClipboardHandler instance = new ClipboardHandler();

  private ClipboardHandler(){
    try {
      robot = new Robot();
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the instance associated with this singleton class
   * @return  the single instance of this object
   */
  public static ClipboardHandler getInstance() {
    return instance;
  }

   /**
   * Empty implementation of the ClipboardOwner interface.
   */
   @Override public void lostOwnership(Clipboard clipboard, Transferable contents){
     //do nothing
   }

  /**
  * Place a String on the clipboard, and make this class the
  * owner of the Clipboard's contents.
  */
  public synchronized void setClipboardContents(String string){
    StringSelection stringSelection = new StringSelection(string);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(stringSelection, this);
  }

  /**
  * Get the String residing on the clipboard.
  *
  * @return any text found on the Clipboard; if none found, return an
  * empty String.
  */
  public synchronized String getClipboardContents() {
    String result = "";
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    //odd: the Object param of getContents is not currently used
    Transferable contents = clipboard.getContents(null);
    boolean hasTransferableText =
      (contents != null) &&
      contents.isDataFlavorSupported(DataFlavor.stringFlavor)
    ;
    if (hasTransferableText) {
      try {
        result = (String)contents.getTransferData(DataFlavor.stringFlavor);
      }
      catch (UnsupportedFlavorException | IOException ex){
        System.out.println(ex);
        ex.printStackTrace();
      }
    }
    return result;
  }

  public synchronized void copyToClipboard() {
    robot.keyPress(KeyEvent.VK_CONTROL);
    robot.keyPress(KeyEvent.VK_A);
    robot.keyRelease(KeyEvent.VK_A);
    robot.keyPress(KeyEvent.VK_X);
    robot.keyRelease(KeyEvent.VK_X);
    robot.keyRelease(KeyEvent.VK_CONTROL);
  }

  public synchronized void pasteFromClipboard() {
    robot.keyPress(KeyEvent.VK_CONTROL);
    robot.keyPress(KeyEvent.VK_V);
    robot.keyRelease(KeyEvent.VK_CONTROL);
    robot.keyRelease(KeyEvent.VK_V);
    robot.keyPress(KeyEvent.VK_ENTER);
    robot.keyRelease(KeyEvent.VK_ENTER);
  }
  
} 