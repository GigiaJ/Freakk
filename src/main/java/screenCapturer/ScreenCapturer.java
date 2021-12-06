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
import java.util.List;
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
import main.Main;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.tesseract.*;
import org.bytedeco.leptonica.*;
import org.bytedeco.leptonica.global.lept;

public class ScreenCapturer extends JFrame {
    private static final long serialVersionUID = 1L;
    private ExamplePanel selectionPane;
    public static GlobalKeyboardHook keyboardHook;
    private static final Color BACKGROUND = new Color(0, 0, 128, 50);
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
            ImageInformation imageInfo = new ImageInformation(ExamplePanel.sx, ExamplePanel.sy, ExamplePanel.ex, ExamplePanel.ey);
            img = robot.createScreenCapture(new Rectangle(imageInfo.getTopRightX(), imageInfo.getTopRightY(), imageInfo.getHeight(), imageInfo.getWidth()));
            if (!image) {
                text = retrieveText(ScreenCapturer.saveImageToTemporaryFile(img));
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
        this.selectionPane = new ExamplePanel();
        this.setContentPane(this.selectionPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ScreenCapturer();
            }
        });
    }

    public static File saveImageToTemporaryFile(BufferedImage imageToSave) throws IOException {
        imageToSave = convertToGrayscale(imageToSave);
        File temporaryFile = File.createTempFile("ScreenCopy", ".png");
        temporaryFile.delete();
        String formatName = "png";
        Iterator iw = ImageIO.getImageWritersByFormatName("png");

        while(iw.hasNext()) {
            ImageWriter writer = (ImageWriter)iw.next();
            ImageWriteParam writeParam = writer.getDefaultWriteParam();
            ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(1);
            IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
            if (!metadata.isReadOnly() && metadata.isStandardMetadataFormatSupported()) {
                setDPI(metadata);
                ImageOutputStream stream = ImageIO.createImageOutputStream(temporaryFile);

                try {
                    writer.setOutput(stream);
                    writer.write(metadata, new IIOImage(imageToSave, (List)null, metadata), writeParam);
                    break;
                } finally {
                    stream.close();
                }
            }
        }

        return temporaryFile;
    }

    public String retrieveText(File temporaryFile) throws IOException {
        String text = "";
        TessBaseAPI api = new TessBaseAPI();
        String path = "C:\\Users\\Gigia\\Downloads\\";//Main.baseFolder.getAbsolutePath() + "\\";
        api.Init(path, "eng");
        PIX image = lept.pixRead(temporaryFile.getAbsolutePath());
        api.SetImage(image);
        BytePointer outText = api.GetUTF8Text();
        text = outText.getString();
        api.End();
        outText.deallocate();
        lept.pixDestroy(image);
        api.close();
        image.close();
        outText.close();
        return text;
    }

    private static void setDPI(IIOMetadata metadata) throws IIOInvalidTreeException {
        double INCH_TO_CM = 2.54D;
        double DPI = 400D;
        double dotsPerMilli = DPI / 10 / INCH_TO_CM;
        IIOMetadataNode horiz = new IIOMetadataNode("HorizontalPixelSize");
        horiz.setAttribute("value", Double.toString(dotsPerMilli));
        IIOMetadataNode vert = new IIOMetadataNode("VerticalPixelSize");
        vert.setAttribute("value", Double.toString(dotsPerMilli));
        IIOMetadataNode dim = new IIOMetadataNode("Dimension");
        dim.appendChild(horiz);
        dim.appendChild(vert);
        IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0");
        root.appendChild(dim);
        metadata.mergeTree("javax_imageio_1.0", root);
    }

    private static BufferedImage convertToGrayscale(BufferedImage image) {
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
        return image;
    }


    public static class ExamplePanel extends JPanel {
        private static final long serialVersionUID = 1L;
        public static int sx = -1;
        public static int sy = -1;
        public static int ex = -1;
        public static int ey = -1;

        public ExamplePanel() {
            MouseAdapter mouseAdapter = new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    ExamplePanel.ey = -1;
                    ExamplePanel.ex = -1;
                    ExamplePanel.sy = -1;
                    ExamplePanel.sx = -1;
                    ExamplePanel.sx = e.getX();
                    ExamplePanel.sy = e.getY();
                    ExamplePanel.this.repaint();
                }

                public void mouseReleased(MouseEvent e) {
                    ExamplePanel.ex = e.getX();
                    ExamplePanel.ey = e.getY();
                    ExamplePanel.this.repaint();
                }

                public void mouseDragged(MouseEvent e) {
                    ExamplePanel.ex = e.getX();
                    ExamplePanel.ey = e.getY();
                    ExamplePanel.this.repaint();
                }
            };
            this.addMouseListener(mouseAdapter);
            this.addMouseMotionListener(mouseAdapter);
            this.setDoubleBuffered(false);
            this.setOpaque(false);
            this.setBackground(ScreenCapturer.BACKGROUND);
            ScreenCapturer.keyboardHook.addKeyListener(new GlobalKeyAdapter() {
                public void keyPressed(GlobalKeyEvent e) {
                    if (e.getVirtualKeyCode() == 192) {
                        ExamplePanel.ey = -1;
                        ExamplePanel.ex = -1;
                        ExamplePanel.sy = -1;
                        ExamplePanel.sx = -1;
                        ExamplePanel.this.repaint();
                    }

                }
            });
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setComposite(AlphaComposite.Clear);
            g2.setBackground(new Color(255, 255, 255, 0));
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            g2.setComposite(AlphaComposite.Src.derive(0.5F));
            g2.setPaint(this.getBackground());
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            g2.setComposite(AlphaComposite.Src.derive(1.0F));
            g2.setPaint(Color.WHITE);
            g2.drawString("Press Escape to exit", 10, 20);
            g2.drawString("Press C to capture", 10, 35);
            g2.drawString("Press T to copy text from image", 10, 50);
            g2.drawString("Press ` to place or remove from on overlaying", 10, 65);
            g2.drawString("Press P to open MS Paint", 10, 80);
            g2.drawString("Press N to paste text from image to notepad", 10, 95);
            if (sx != -1 && sy != -1 && ex != -1 && ey != -1) {
                int asx = Math.min(sx, ex);
                int asy = Math.min(sy, ey);
                int w = Math.abs(ex - sx);
                int h = Math.abs(ey - sy);
                g2.setComposite(AlphaComposite.Src);
                g2.setPaint(new Color(255, 255, 255, 0));
                g2.fillRect(asx, asy, w, h);
                g2.setPaint(new Color(0, 0, 0, 1));
                g2.fillRect(asx, asy, w, h);
                g2.setComposite(AlphaComposite.SrcOver);
                g2.setStroke(new BasicStroke(2.0F));
                g2.setPaint(new Color(1.0F, 1.0F, 1.0F, 0.15F));
                g2.drawRect(asx - 1, asy - 1, w + 2, h + 2);
            }

        }
    }
}
