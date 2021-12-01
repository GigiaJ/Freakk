package commands.general.help.laterUse;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;

public class BodyDetailPanel  {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BodyDetailPanel window = new BodyDetailPanel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @return 
	 */
	public BodyDetailPanel() {
		updateDetailBody("signchange", "sampletext", "sampletext", "sampletext", "sampletext");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateDetailBody(String commandName, String commandUsage, String commandInfo, String commandFirstExample, String commandSecondExample) {
		frame = new JFrame();
		//frame.setBounds(100, 100, 450, 300);
		frame.setSize(830, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel commandDetailsPanel = new JPanel();
		commandDetailsPanel.setBounds(205, 39, 609, 401);
		frame.getContentPane().add(commandDetailsPanel);
		commandDetailsPanel.setLayout(null);
		
		JPanel detailsPanelBody = new JPanel();
		detailsPanelBody.setBounds(0, 20, 609, 380);
		commandDetailsPanel.add(detailsPanelBody);
		detailsPanelBody.setLayout(null);
		
		JTextPane txtpnCommandName = new JTextPane();
		txtpnCommandName.setText(org.apache.commons.lang3.StringUtils.capitalize(commandName));
		txtpnCommandName.setBackground(UIManager.getColor("Panel.background"));
		txtpnCommandName.setFont(new Font("Arial", Font.BOLD, 16));
		txtpnCommandName.setEditable(false);
		txtpnCommandName.setBounds(238, 11, 131, 29);
		detailsPanelBody.add(txtpnCommandName);
		
		JTextPane txtpnSyntax = new JTextPane();
		txtpnSyntax.setBackground(UIManager.getColor("Panel.background"));
		txtpnSyntax.setText("Syntax: " + commandUsage);
		txtpnSyntax.setFont(new Font("Arial", Font.PLAIN, 16));
		txtpnSyntax.setEditable(false);
		txtpnSyntax.setBounds(10, 55, 589, 29);
		detailsPanelBody.add(txtpnSyntax);
		
		JTextPane txtpnInfo = new JTextPane();
		txtpnInfo.setBackground(UIManager.getColor("Panel.background"));
		txtpnInfo.setText("This command " + commandInfo);
		txtpnInfo.setFont(new Font("Arial", Font.PLAIN, 16));
		txtpnInfo.setEditable(false);
		txtpnInfo.setBounds(10, 95, 589, 82);
		detailsPanelBody.add(txtpnInfo);
		
		JPanel commandSidePanel = new JPanel();
		commandSidePanel.setBounds(0, 39, 205, 401);
		frame.getContentPane().add(commandSidePanel);
		commandSidePanel.setLayout(null);
		
		JScrollPane sidePanelBody = new JScrollPane();
		sidePanelBody.setBounds(0, 21, 205, 380);
		commandSidePanel.add(sidePanelBody);
		

		
		JPanel sidePanelHeader = new JPanel();
		sidePanelHeader.setBackground(Color.LIGHT_GRAY);
		sidePanelHeader.setBounds(0, 0, 205, 21);
		commandSidePanel.add(sidePanelHeader);
		sidePanelHeader.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Commands", "Search Results"}));
		comboBox.setSelectedIndex(1);
		comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBox.setBounds(0, 0, 205, 20);
		sidePanelHeader.add(comboBox);
		
		//if (commandFirstExample != null) {
		JTextPane txtpnFirstExample = new JTextPane();
		txtpnFirstExample.setBackground(UIManager.getColor("Panel.background"));
		txtpnFirstExample.setText("Example: " + commandFirstExample);
		txtpnFirstExample.setFont(new Font("Arial", Font.PLAIN, 16));
		txtpnFirstExample.setEditable(false);
		txtpnFirstExample.setBounds(10, 266, 589, 29);
		detailsPanelBody.add(txtpnFirstExample);
		//}
		
		//if (commandSecondExample != null) {
		JTextPane txtpnSecondExample = new JTextPane();
		txtpnSecondExample.setBackground(UIManager.getColor("Panel.background"));
		txtpnSecondExample.setText("Example: " + commandSecondExample);
		txtpnSecondExample.setFont(new Font("Arial", Font.PLAIN, 16));
		txtpnSecondExample.setEditable(false);
		txtpnSecondExample.setBounds(10, 306, 589, 29);
		detailsPanelBody.add(txtpnSecondExample);
		

		//}
	}
}
