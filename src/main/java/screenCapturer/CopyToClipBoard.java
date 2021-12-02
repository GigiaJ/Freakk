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

    public synchronized void setText(String text) {
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public synchronized void copyFile(File file) {
    	ArrayList<File> fileList = new ArrayList<File>();
    	fileList.add(file);
    	TransferableFile trans = new TransferableFile(fileList);
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        c.setContents(trans, this);
    }
    
    public synchronized void copyImage(Image i) {
    	TransferableImage trans = new TransferableImage(i);
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        c.setContents(trans, this);
    }

    public void lostOwnership(Clipboard clip, Transferable trans) {

    }

    private abstract static class TransferableEx<T> implements Transferable {
        T i;

        TransferableEx(T i) {
            this.i = i;
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (isDataFlavorSupported(flavor))
                return this.i;
            else
                throw new UnsupportedFlavorException(flavor);
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
    
    private static class TransferableImage extends TransferableEx<Image> {
        TransferableImage(Image i) {
            super(i);
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.imageFlavor};
        }

    }

    private static class TransferableFile extends TransferableEx<List<File>> {
        public TransferableFile(List<File> i) {
            super(i);
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.javaFileListFlavor};
        }

    }
}
