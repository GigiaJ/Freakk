package translator;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import screenCapturer.SelectorPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TranslatorOverlay extends JPanel {
    private static final long serialVersionUID = 1L;
    public static int sx = -1;
    public static int sy = -1;
    public static int ex = -1;
    public static int ey = -1;
    private static Color bg;

    public TranslatorOverlay(GlobalKeyboardHook hook, Color bgColor) {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                ey = -1;
                ex = -1;
                sy = -1;
                sx = -1;
                sx = e.getX();
                sy = e.getY();
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                ex = e.getX();
                ey = e.getY();
                repaint();
            }

            public void mouseDragged(MouseEvent e) {
                ex = e.getX();
                ey = e.getY();
                repaint();
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        setDoubleBuffered(false);
        setOpaque(false);
        setBackground(bgColor);
        bg = bgColor;
    }

    public void clearSelection() {
        ey = -1;
        ex = -1;
        sy = -1;
        sx = -1;
        repaint();
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
