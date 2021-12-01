package macro;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class MacroManager {

	private JFrame frame;
	private static MacroType type = MacroType.NONE;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MacroManager window = new MacroManager();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MacroManager() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 500, 400);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/SkipperLogo.png")));
		frame.setTitle("Macro Management");
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
		);
		
		mainMenu(panel);

		frame.getContentPane().setLayout(groupLayout);
	}
	
	public static void panelInput(JPanel panel) {
		panel.removeAll();
		MacroPanelSelect.MacroOptions(type, panel);
		panel.revalidate();
		panel.repaint();
		
	}
	
	public static void mainMenu(JPanel panel) {
		JTextPane txtpnSelectADesired = new JTextPane();
		txtpnSelectADesired.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		txtpnSelectADesired.setBackground(Color.LIGHT_GRAY);
		txtpnSelectADesired.setEditable(false);
		txtpnSelectADesired.setText("Select a desired action");
		
		JButton joinButton = new JButton("Join Macro");
		joinButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				type = MacroType.JOIN;
				panelInput(panel);
			}
		});
		
		JButton leaveButton = new JButton("Leave Macro");
		leaveButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		leaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				type = MacroType.LEAVE;
				panelInput(panel);
			}
		});
		
		JButton messageButton = new JButton("Message Macro");
		messageButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		messageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				type = MacroType.MESSAGE;
				panelInput(panel);
			}
		});
		
		JTextPane txtpnAllowsYouTo_1 = new JTextPane();
		txtpnAllowsYouTo_1.setEditable(false);
		txtpnAllowsYouTo_1.setText("Allows you to create, delete, view, or edit existing join macros");
		txtpnAllowsYouTo_1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		txtpnAllowsYouTo_1.setBackground(Color.LIGHT_GRAY);
		
		JTextPane txtpnAllowsYouTo_2 = new JTextPane();
		txtpnAllowsYouTo_2.setEditable(false);
		txtpnAllowsYouTo_2.setText("Allows you to create, delete, view, or edit existing leave macros");
		txtpnAllowsYouTo_2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		txtpnAllowsYouTo_2.setBackground(Color.LIGHT_GRAY);
		
		JTextPane txtpnAllowsYouTo = new JTextPane();
		txtpnAllowsYouTo.setEditable(false);
		txtpnAllowsYouTo.setBackground(Color.LIGHT_GRAY);
		txtpnAllowsYouTo.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		txtpnAllowsYouTo.setText("Allows you to create, delete, view, or edit existing message macros");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(26, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(118)
							.addComponent(txtpnSelectADesired, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(messageButton)
							.addGap(6)
							.addComponent(txtpnAllowsYouTo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(leaveButton, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(txtpnAllowsYouTo_2, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(joinButton, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(txtpnAllowsYouTo_1, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE)))
					.addGap(18))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtpnSelectADesired, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(messageButton)
						.addComponent(txtpnAllowsYouTo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(leaveButton)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(txtpnAllowsYouTo_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(6)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(joinButton)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(txtpnAllowsYouTo_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(240, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
	}
}
