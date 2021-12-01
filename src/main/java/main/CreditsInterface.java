package main;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window.Type;

import javax.swing.JFrame;
import javax.swing.JTextPane;

public class CreditsInterface {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreditsInterface window = new CreditsInterface();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreditsInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/SkipperLogo.png")));
		frame.setType(Type.POPUP);
		frame.setBounds(100, 100, 137, 170);
		frame.getContentPane().setLayout(null);
		
		JTextPane txtpnLogoCreatedBy = new JTextPane();
		txtpnLogoCreatedBy.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		txtpnLogoCreatedBy.setEditable(false);
		txtpnLogoCreatedBy.setText("Logo Created By \r\nAugust Reavau#2400\r\n\r\nBot created by \r\nG #7185");
		txtpnLogoCreatedBy.setBounds(0, 0, 121, 131);
		frame.getContentPane().add(txtpnLogoCreatedBy);
	}
}
