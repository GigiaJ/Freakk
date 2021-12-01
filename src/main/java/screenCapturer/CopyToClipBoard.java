/**
 * @author		GigiaJ
 * @filename	CopyToClipBoard.java
 * @date		Mar 27, 2020
 * @description 
 */

package screenCapturer;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CopyToClipBoard implements ClipboardOwner {
    public CopyToClipBoard() {
    }

    public void setText(String text) {
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public void copyFile(File file) {
    	ArrayList<File> fileList = new ArrayList<File>();
    	fileList.add(file);
    	TransferableFile trans = new TransferableFile(fileList);
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        c.setContents(trans, this);
    }
    
    public void copyImage(Image i) {
    	TransferableImage trans = new TransferableImage(i);
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        c.setContents(trans, this);
    }

    public void lostOwnership(Clipboard clip, Transferable trans) {
    }
    
    private class TransferableImage implements Transferable {
    	private Image i;

        public TransferableImage(Image i) {
            this.i = i;
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        	return this.i;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[]{DataFlavor.imageFlavor};
            return flavors;
        }
        
        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            DataFlavor[] flavors = this.getTransferDataFlavors();
            for(int i = 0; i < flavors.length; ++i) {
                if (flavor.equals(flavors[i])) {
                    return true;
                }
            }

            return false;
        }
    }

    private class TransferableFile implements Transferable {
    	private List<File> i;

        public TransferableFile(List<File> i) {
            this.i = i;
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        	return this.i;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[]{DataFlavor.javaFileListFlavor};
            return flavors;
        }
        
        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            DataFlavor[] flavors = this.getTransferDataFlavors();
            for(int i = 0; i < flavors.length; ++i) {
                if (flavor.equals(flavors[i])) {
                    return true;
                }
            }

            return false;
        }
    }
}
