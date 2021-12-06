package translator;

import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.GDI32Util;
import commands.color.colorchat.ColorChatImageCreator;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import main.JNA;
import main.Main;
import messages.GlobalListeners;
import screenCapturer.ImageInformation;
import screenCapturer.ScreenCapturer;
import screenCapturer.SelectorPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Translator extends JFrame {
    public static GlobalKeyboardHook keyboardHook;
    private static final Color BACKGROUND = new Color(0, 0, 128, 50);
    boolean overlay = false;
    private ExecutorService es;

    public Translator() {
        this.setDefaultCloseOperation(2);
        es = Executors.newSingleThreadExecutor();
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
            }
        });
        this.setExtendedState(6);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
        this.setUndecorated(true);
        this.setBackground(new Color(255, 255, 255, 0));
        this.populate();
        this.setType(Type.UTILITY);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
    }

    private void close() {
        this.dispose();
    }

    private void toggleVisible() {
        if (!overlay) {
            ImageInformation information = new ImageInformation(SelectorPanel.sx, SelectorPanel.sy, SelectorPanel.ex, SelectorPanel.ey);
            this.setBackground(new Color(0, 0, 0, 255));
            this.setLocation(information.getTopRightX(), information.getTopRightY());
            this.setSize(information.getHeight(), information.getWidth());

            es.submit(new Runnable() {
                @Override
                public void run() {
                    long count = 0;
                    while(true) {
                        if (count % 1000000000 == 0) {
                            paint(information);
                        }
                        count++;
                    }
                }
            });

            overlay = true;
        }
        else {
            this.dispose();
            new Translator();
            this.setLocation(0, 0);
            populate();
            overlay = false;
        }
    }

    private void paint(ImageInformation information) {
        BufferedImage img;
        String text = "";
        WinDef.HWND discord = JNA.User32.INSTANCE.FindWindowA(null, "kjhjkh - Discord");
        img = GDI32Util.getScreenshot(discord);
        Rectangle size = WindowUtils.getWindowLocationAndSize(discord);

        int[] newBounds = new int[]{information.getTopRightX() - size.x,
                information.getTopRightY() - size.y,
                information.getWidth(),
                information.getWidth()};
        img = img.getSubimage(newBounds[0], newBounds[1], newBounds[2], newBounds[3]);
        try {
            text = ScreenCapturer.retrieveText(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.paintComponent(getGraphics(), information.getTopRightX(), information.getTopRightY(), information.getHeight(), information.getWidth(), text);
    }

    public void paintComponent(Graphics g, int sx, int sy, int ex, int ey, String text) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setComposite(AlphaComposite.Clear);
        g2.setBackground(new Color(255, 255, 255, 100));
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2.setComposite(AlphaComposite.Src.derive(0.5F));
        g2.setPaint(this.getBackground());
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2.setComposite(AlphaComposite.Src.derive(1.0F));
        g2.setPaint(Color.WHITE);
        //Handle String Wrapping And Stuff Here
        int offset = 14;
        int y = offset;
        for (String s : text.split("G ")) {
            g2.drawString(s, 0, y);
            y += offset;
        }

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

    private boolean isFrameVisible() {
        return this.isShowing();
    }

    private void populate() {
        SelectorPanel selectionPane = new SelectorPanel(keyboardHook, BACKGROUND);
        this.setContentPane(selectionPane);
    }

    public static Color getColor() {
        return BACKGROUND;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Translator::new);
    }
}
