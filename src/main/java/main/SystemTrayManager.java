package main;

import bot.settings.SettingSaver;
import commands.color.AutoColor;
import commands.color.colorchat.ColorChat;
import commands.general.AuthorEmbeding;
import commands.general.CloseBot;
import commands.general.MessageEmbeding;
import commands.general.NoSpace;
import commands.general.Reverser;
import commands.general.SpongeBobChickenCaps;
import commands.general.help.HelpGUI;
import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import macro.MacroManager;
import screenCapturer.ScreenCapturer;

public class SystemTrayManager implements ItemListener, ActionListener {
    static Menu displayMenu = new Menu("Settings");
    static Menu embeddedEffects = new Menu("Embedded Effects");
    static CheckboxMenuItem cb1 = new CheckboxMenuItem("Color Chat");
    static CheckboxMenuItem cb2 = new CheckboxMenuItem("Embedded Messages");
    static CheckboxMenuItem cb3 = new CheckboxMenuItem("Author Emb Messages");
    static CheckboxMenuItem cb4 = new CheckboxMenuItem("Auto Color");
    static CheckboxMenuItem cb5 = new CheckboxMenuItem("Reverser");
    static CheckboxMenuItem cb6 = new CheckboxMenuItem("No Space");
    static CheckboxMenuItem cb7 = new CheckboxMenuItem("SpongeBobDerp");
    static MenuItem exit = new MenuItem("Exit");
    static MenuItem logout = new MenuItem("Logout");
    static MenuItem aboutItem = new MenuItem("Credits");
    static MenuItem macro = new MenuItem("Macro");
    static MenuItem help = new MenuItem("Help");
    static MenuItem screenCapture = new MenuItem("Screen Capturer");
    static ItemListener listener = null;
    static BufferedImage img = null;
    static TrayIcon trayIcon = null;

    public static boolean systemTrayCheck() {
        return SystemTray.isSupported();
    }

    public SystemTrayManager() {
        if (!OSCheck.isMac() && systemTrayCheck()) {
            cb1.addItemListener(this);
            cb2.addItemListener(this);
            cb3.addItemListener(this);
            cb4.addItemListener(this);
            cb5.addItemListener(this);
            cb6.addItemListener(this);
            cb7.addItemListener(this);
            logout.addActionListener(this);
            exit.addActionListener(this);
            macro.addActionListener(this);
            help.addActionListener(this);
            screenCapture.addActionListener(this);
            aboutItem.addActionListener(this);

            try {
                URL imgLoc = this.getClass().getResource("../images/SkipperTrayLogo.png");
                if (imgLoc != null) {
                    img = ImageIO.read(imgLoc);
                }
                trayIcon = new TrayIcon(img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void executeTray(boolean initial) {
        if (OSCheck.isWindows() && systemTrayCheck()) {
            PopupMenu popup = new PopupMenu();
            MenuItem colorChatFont = new MenuItem("Color Chat Font:" + Main.settings.getCurrentFontStyle());
            MenuItem colorChatSize = new MenuItem("Color Chat Size:" + Integer.toString(Main.settings.getCurrentFontSize()));
            MenuItem colorChatColor = new MenuItem("Color Chat Color:" + Main.settings.getColorChatColor());
            MenuItem embColor = new MenuItem("Embedded Message Color:" + Main.settings.getEmbedColor());
            if (initial) {
                listener = new SystemTrayManager();
                SystemTray tray = SystemTray.getSystemTray();
                stateChange();
                popup.add(aboutItem);
                popup.addSeparator();
                popup.add(cb1);
                popup.add(cb2);
                popup.add(cb3);
                popup.add(cb4);
                popup.add(embeddedEffects);
                embeddedEffects.add(cb5);
                embeddedEffects.add(cb6);
                embeddedEffects.add(cb7);
                popup.addSeparator();
                popup.add(displayMenu);
                displayMenu.add(colorChatFont);
                displayMenu.add(colorChatSize);
                displayMenu.add(colorChatColor);
                displayMenu.add(embColor);
                popup.add(macro);
                popup.add(screenCapture);
                popup.add(help);
                popup.add(logout);
                popup.add(exit);
                trayIcon.setPopupMenu(popup);

                try {
                    tray.add(trayIcon);
                } catch (AWTException var8) {
                    System.out.println("TrayIcon could not be added.");
                }
            }

            if (!initial) {
                stateChange();
                displayMenu.removeAll();
                displayMenu.add(colorChatFont);
                displayMenu.add(colorChatSize);
                displayMenu.add(colorChatColor);
                displayMenu.add(embColor);
            }
        }

    }

    public void itemStateChanged(ItemEvent e) {
        Object source = e.getItemSelectable();
        if (source == cb1) {
            cb1.setState(cb1.getState());
            systemTrayCheckBox(1);
        }

        if (source == cb2) {
            cb3.setState(false);
            cb2.setState(cb2.getState());
            systemTrayCheckBox(2);
        }

        if (source == cb3) {
            cb2.setState(false);
            cb3.setState(cb3.getState());
            systemTrayCheckBox(3);
        }

        if (source == cb4) {
            cb4.setState(cb4.getState());
            systemTrayCheckBox(4);
        }

        if (source == cb5) {
            cb5.setState(cb5.getState());
            systemTrayCheckBox(5);
        }

        if (source == cb6) {
            cb6.setState(cb6.getState());
            systemTrayCheckBox(6);
        }

        if (source == cb7) {
            cb7.setState(cb7.getState());
            systemTrayCheckBox(7);
        }

        SettingSaver.saveSettings();
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == logout && OSCheck.isWindows()) {
            try {
                while(TaskManagement.isProcessRunning("browsercore64.exe")) {
                    TaskManagement.killProcess("browsercore64.exe");
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            StartUp.deleteDirectory(Main.browserCookiesFolder);
            CloseBot.closeBot();
        }

        if (source == exit) {
            CloseBot.closeBot();
        }

        if (source == help) {
            HelpGUI.main((String[])null);
        }

        if (source == macro) {
            MacroManager.main((String[])null);
        }

        if (source == aboutItem) {
            CreditsInterface.main((String[])null);
        }

        if (source == screenCapture) {
            ScreenCapturer.main((String[])null);
        }

    }

    private static void stateChange() {
        if (Main.settings.getColorChatStatus()) {
            cb1.setState(true);
        } else {
            cb1.setState(false);
        }

        if (Main.settings.getEmbedMessageStatus()) {
            cb3.setState(false);
            cb2.setState(true);
        } else {
            cb2.setState(false);
        }

        if (Main.settings.getAuthorEmbedStatus()) {
            cb2.setState(false);
            cb3.setState(true);
        } else {
            cb3.setState(false);
        }

        if (Main.settings.getAutoColorStatus()) {
            cb4.setState(true);
        } else {
            cb4.setState(false);
        }

        if (Main.settings.getReverserStatus()) {
            cb5.setState(true);
        } else {
            cb5.setState(false);
        }

        if (Main.settings.getNoSpaceStatus()) {
            cb6.setState(true);
        } else {
            cb6.setState(false);
        }

        if (Main.settings.getSpongebobChickenCapsStatus()) {
            cb7.setState(true);
        } else {
            cb7.setState(false);
        }

    }

    static void systemTrayCheckBox(Integer settingToChange) {
        if (settingToChange == 1) {
            ColorChat.colorChat();
        }

        if (settingToChange == 2) {
            MessageEmbeding.embedMessage();
        }

        if (settingToChange == 3) {
            AuthorEmbeding.authorEmbedMessage();
        }

        if (settingToChange == 4) {
            AutoColor.autoColor();
        }

        if (settingToChange == 5) {
            Reverser.reverser();
        }

        if (settingToChange == 6) {
            NoSpace.nospace();
        }

        if (settingToChange == 7) {
            SpongeBobChickenCaps.spongeBobChickenCaps();
        }

        SettingSaver.saveSettings();
    }
}
