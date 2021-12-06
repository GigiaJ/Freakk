/**
 * @author		GigiaJ
 * @filename	ScreenCapture.java
 * @date		Mar 27, 2020
 * @description 
 */

package screenCapturer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.stream.IntStream;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import main.ClipboardHandler;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ScreenCapturer extends JFrame {
    private static final long serialVersionUID = 1L;
    public static GlobalKeyboardHook keyboardHook;
    private static final Color BACKGROUND = new Color(200, 100, 128, 50);
    private static Robot robot;

    public ScreenCapturer() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        this.setDefaultCloseOperation(2);
        keyboardHook = new GlobalKeyboardHook(true);
        keyboardHook.addKeyListener(new GlobalKeyAdapter() {

            public void keyPressed(GlobalKeyEvent event) {
            }

            public void keyReleased(GlobalKeyEvent e) {
                if (e.getVirtualKeyCode() == 27) {
                    close();
                }

                if (e.getVirtualKeyCode() == 192) {
                    toggleVisible();
                }

                if (isFrameVisible() && isFocused()) {
                    switch(e.getVirtualKeyCode()) {
                        case(67):
                            set(true);
                            break;
                        case(78):
                            openApp("notepad.exe", false);
                            break;
                        case(80):
                            openApp("mspaint.exe", true);
                            break;
                        case(84):
                            set(false);
                            break;
                    }
                }

            }
        });
        this.setExtendedState(6);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
        this.setUndecorated(true);
        this.setBackground(new Color(255, 255, 255, 0));
        this.populate();
        this.setType(Type.UTILITY);
        this.setVisible(true);
    }

    /**
     * Opens an app executable and then assigns the associated highlighted text to the clipboard through the set
     * method
     * @param executable        the application to open
     * @param image             is the clipboard data to be set an image or text
     */
    private void openApp(String executable, boolean image) {
        try {
            Runtime.getRuntime().exec(executable);
            set(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the clipboard data with the highlighted data based on whether the user wishes to extract the text
     * or simply copy the image selection
     * @param image             is the clipboard data to be set an image or text
     */
    private void set(boolean image) {
        try {
            BufferedImage img;
            String text = "";
            ImageInformation imageInfo = new ImageInformation(SelectorPanel.sx, SelectorPanel.sy, SelectorPanel.ex, SelectorPanel.ey);
            img = robot.createScreenCapture(new Rectangle(imageInfo.getTopRightX(), imageInfo.getTopRightY(), imageInfo.getHeight(), imageInfo.getWidth()));
            if (!image) {
                text = retrieveText(img);
                ClipboardHandler.getInstance().setText(text);
            } else {
                ClipboardHandler.getInstance().copyImage(img);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        this.dispose();
    }

    private void toggleVisible() {
        this.setVisible(!this.isVisible());
        if (!this.isShowing()) {
            this.setAlwaysOnTop(!this.isShowing());
        }

    }

    private boolean isFrameVisible() {
        return this.isShowing();
    }

    private void populate() {
        SelectorPanel selectionPane = new SelectorPanel(keyboardHook, BACKGROUND);
        this.setContentPane(selectionPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ScreenCapturer();
            }
        });
    }

    /**
     * Uses the tesseracts API to extract text from the underlying screen
     * @param image         the image to extract text from
     * @return              the extracted text
     * @throws IOException  if no file is found then an IOException is thrown
     */
    public static String retrieveText(BufferedImage image) throws IOException {
        String text = "";
        convertToGrayscale(image);
        Tesseract tesseract = new Tesseract();
        String path = "C:\\Users\\Gigia\\Downloads\\";//Main.baseFolder.getAbsolutePath() + "\\";
        tesseract.setDatapath(path);
        tesseract.setOcrEngineMode(ITessAPI.TessOcrEngineMode.OEM_LSTM_ONLY);
        try {
            text = tesseract.doOCR(image);

        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return text;
    }


    /**
     * Converts a passed image's rgb values to grayscale
     * @param image         the image whose rgb values are to be adjusted
     */
    private static void convertToGrayscale(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {

                Color c = new Color(image.getRGB(j, i));
                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);
                Color newColor = new Color(red + green + blue,

                        red + green + blue, red + green + blue);

                image.setRGB(j, i, newColor.getRGB());
            }
        }
    }
}
