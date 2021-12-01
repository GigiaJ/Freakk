package commands.general.help;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class HelpGUI implements HelpInterface {
	private String lastResult = "";
	private boolean previousSearchExists;
	private JFrame frmSkipperHelp;
	private final int COMMANDS_HEADER_VALUE = 0;
	private final int SEARCH_RESULT_HEADER_VALUE = 1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HelpGUI window = new HelpGUI();
					window.frmSkipperHelp.setResizable(false);
					window.frmSkipperHelp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HelpGUI() {
		HelpInterface.createGUIHelpPages();
		initialize();
	}

	private JTextField itemToSearchFor;

	private void initialize() {

		// 830 x 490

		frmSkipperHelp = new JFrame();
		frmSkipperHelp.getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
		frmSkipperHelp.getContentPane().setBackground(Color.WHITE);
		frmSkipperHelp.setSize(810, 490);
		frmSkipperHelp.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frmSkipperHelp
				.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/SkipperLogo.png")));
		frmSkipperHelp.setTitle("Help - Skipper");
		frmSkipperHelp.setType(Type.NORMAL);
		// frame.setExtendedState(Frame.);
		frmSkipperHelp.setAlwaysOnTop(true);
		frmSkipperHelp.getContentPane().setLayout(null);

		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(Color.LIGHT_GRAY);
		headerPanel.setBounds(0, 0, 814, 39);
		frmSkipperHelp.getContentPane().add(headerPanel);
		headerPanel.setLayout(null);

		JTextArea txtrSearch = new JTextArea();
		txtrSearch.setEditable(false);
		txtrSearch.setFont(new Font("Arial", Font.PLAIN, 12));
		txtrSearch.setBackground(Color.LIGHT_GRAY);
		txtrSearch.setText("Search:");
		txtrSearch.setBounds(10, 0, 51, 22);
		headerPanel.add(txtrSearch);

		JSeparator sidePanelHeaderSeperator = new JSeparator();
		sidePanelHeaderSeperator.setBounds(0, 37, 205, 2);
		headerPanel.add(sidePanelHeaderSeperator);

		JSeparator detailPanelSeperator = new JSeparator();
		detailPanelSeperator.setBounds(204, 37, 610, 2);
		headerPanel.add(detailPanelSeperator);

		JPanel commandDetailsPanel = new JPanel();
		commandDetailsPanel.setBounds(205, 39, 609, 401);
		frmSkipperHelp.getContentPane().add(commandDetailsPanel);
		commandDetailsPanel.setLayout(null);

		JPanel detailsPanelHeader = new JPanel();
		detailsPanelHeader.setBackground(Color.LIGHT_GRAY);
		detailsPanelHeader.setBounds(0, 0, 609, 20);
		commandDetailsPanel.add(detailsPanelHeader);

		Panel detailsPanelBody = new Panel();
		detailsPanelBody.setBounds(0, 20, 588, 380);
		commandDetailsPanel.add(detailsPanelBody);
		detailsPanelBody.setLayout(null);

		detailsPanelBody.add(helpInfoTextArea());
		detailsPanelBody.add(helpInfoTitleArea());
		detailsPanelBody.add(helpInfoSearchTitleArea());
		detailsPanelBody.add(helpInfoSearchTextArea());

		JPanel commandsSidePanel = new JPanel();
		commandsSidePanel.setBackground(Color.WHITE);
		commandsSidePanel.setBounds(0, 39, 205, 401);
		frmSkipperHelp.getContentPane().add(commandsSidePanel);
		commandsSidePanel.setLayout(null);

		JPanel sidePanelHeader = new JPanel();

		JScrollPane sidePanelBody = new JScrollPane();
		updateSidePanelBody(sidePanelBody, detailsPanelBody, commandsSidePanel, sidePanelHeader, COMMANDS_HEADER_VALUE);

		itemToSearchFor = new JTextField();
		itemToSearchFor.setFont(new Font("Arial", Font.PLAIN, 12));
		itemToSearchFor.setBounds(65, 0, 86, 20);
		headerPanel.add(itemToSearchFor);
		itemToSearchFor.setColumns(10);
		
		itemToSearchFor.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					commandsSidePanel.removeAll();
					searchForResult(itemToSearchFor.getText(), sidePanelBody, commandsSidePanel, sidePanelHeader,
							detailsPanelBody, SEARCH_RESULT_HEADER_VALUE);
					commandsSidePanel.revalidate();
					commandsSidePanel.repaint();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}			
		});
		
		Button searchButton = new Button("Go");
		searchButton.setBackground(Color.GRAY);
		searchButton.setBounds(157, 0, 28, 22);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				commandsSidePanel.removeAll();
				searchForResult(itemToSearchFor.getText(), sidePanelBody, commandsSidePanel, sidePanelHeader,
						detailsPanelBody, SEARCH_RESULT_HEADER_VALUE);
				commandsSidePanel.revalidate();
				commandsSidePanel.repaint();
			}
		});
		headerPanel.add(searchButton);

	}

	private void updateSidePanelHeader(Panel detailsPanelBody, JScrollPane sidePanelBody, JPanel sidePanelHeader,
			JPanel commandsSidePanel, int currentDisplayType) {
		sidePanelHeader.setBackground(Color.LIGHT_GRAY);
		sidePanelHeader.setBounds(0, 0, 205, 21);
		commandsSidePanel.add(sidePanelHeader);
		sidePanelHeader.setLayout(null);
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Commands", "Search Results" }));
		comboBox.setSelectedIndex(currentDisplayType);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (comboBox.getSelectedItem().toString().equals("Commands")) {
					detailsPanelBody.removeAll();
					commandsSidePanel.removeAll();
					updateSidePanelBody(sidePanelBody, detailsPanelBody, commandsSidePanel, sidePanelHeader,
							COMMANDS_HEADER_VALUE);
					commandsSidePanel.revalidate();
					commandsSidePanel.repaint();
					detailsPanelBody.revalidate();
					detailsPanelBody.repaint();
					detailsPanelBody.add(helpInfoTextArea());
					detailsPanelBody.add(helpInfoTitleArea());
					detailsPanelBody.add(helpInfoSearchTitleArea());
					detailsPanelBody.add(helpInfoSearchTextArea());
				}
				if (comboBox.getSelectedItem().toString().equals("Search Results")) {
					detailsPanelBody.removeAll();
					commandsSidePanel.removeAll();
					showLastResult(previousSearchExists, sidePanelBody, commandsSidePanel, sidePanelHeader,
							detailsPanelBody, SEARCH_RESULT_HEADER_VALUE);
					commandsSidePanel.revalidate();
					commandsSidePanel.repaint();
					detailsPanelBody.revalidate();
					detailsPanelBody.repaint();
					detailsPanelBody.add(helpInfoTextArea());
					detailsPanelBody.add(helpInfoTitleArea());
					detailsPanelBody.add(helpInfoSearchTitleArea());
					detailsPanelBody.add(helpInfoSearchTextArea());
				}
			}
		});
		comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBox.setBounds(0, 0, 205, 20);
		sidePanelHeader.add(comboBox);
	}

	private void updateSidePanelBody(JScrollPane sidePanelBody, Panel detailsPanelBody, JPanel commandsSidePanel,
			JPanel sidePanelHeader, int headerDisplayValue) {
		sidePanelHeader.removeAll();
		updateSidePanelHeader(detailsPanelBody, sidePanelBody, sidePanelHeader, commandsSidePanel, headerDisplayValue);
		sidePanelHeader.revalidate();
		sidePanelHeader.repaint();
		sidePanelBody.setBounds(0, 21, 205, 380);
		commandsSidePanel.add(sidePanelBody);
		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Commands") {
			private static final long serialVersionUID = 6226685685735439798L;

			{
				for (int t = 0; t < commands.CommandType.values().length; t++) {
					ArrayList<DefaultMutableTreeNode> listOfNodes = new ArrayList<DefaultMutableTreeNode>();
					for (int i = 0; i < commands.CommandList.listOfCommands.size(); i++) {
						// Creates numerous so we can quickly make the list of commands and add to it
						// later if we desire without changing this code
						DefaultMutableTreeNode generalNode = new DefaultMutableTreeNode("General");
						DefaultMutableTreeNode advancedNode = new DefaultMutableTreeNode("Advanced");
						DefaultMutableTreeNode maliciousNode = new DefaultMutableTreeNode("Malicious");
						DefaultMutableTreeNode managementNode = new DefaultMutableTreeNode("Management");
						DefaultMutableTreeNode identifierNode = new DefaultMutableTreeNode("Identifier");
						DefaultMutableTreeNode nsfwNode = new DefaultMutableTreeNode("NSFW");
						DefaultMutableTreeNode colorNode = new DefaultMutableTreeNode("Color");
						// Adds them to a list to quickly use them later in the loop
						listOfNodes.add(generalNode);
						listOfNodes.add(colorNode);
						listOfNodes.add(advancedNode);
						listOfNodes.add(identifierNode);
						listOfNodes.add(nsfwNode);
						listOfNodes.add(managementNode);
						listOfNodes.add(maliciousNode);
						if (commands.CommandList.listOfCommands.get(i).getType().getType()
								.equals(commands.CommandType.values()[t].getType())) {
							listOfNodes.get(t).add(
									new DefaultMutableTreeNode(commands.CommandList.listOfCommands.get(i).getName()));
						}

					}
					add(listOfNodes.get(t));
				}
			}
		}));
		tree.setFont(new Font("Arial", Font.PLAIN, 12));
		tree.setShowsRootHandles(true);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

				/* if nothing is selected */
				if (node == null)
					return;

				/* retrieve the node that was selected */
				for (int i = 0; i < commands.CommandList.listOfCommands.size(); i++) {
					Object nodeInfo = node.getUserObject();
					if (commands.CommandList.listOfCommands.get(i).getName().equals(nodeInfo)) {
						commands.CommandList.listOfCommands.get(i);
						detailsPanelBody.removeAll();
						updateDetailBody(detailsPanelBody, commands.CommandList.listOfCommands.get(i).getName(),
								commands.CommandList.listOfCommands.get(i).getUsage(),
								commands.CommandList.listOfCommands.get(i).getInfo(),
								commands.CommandList.listOfCommands.get(i).getFirstExample(),
								commands.CommandList.listOfCommands.get(i).getSecondExample(),
								commands.CommandList.listOfCommands.get(i).getPermissions());
						detailsPanelBody.revalidate();
						detailsPanelBody.repaint();
					}
				}
				/* React to the node selection. */

			}
		});
		sidePanelBody.add(tree);
		sidePanelBody.setViewportView(tree);
	}

	private void updateDetailBody(Panel panel, String commandName, String commandUsage, String commandInfo,
			String commandFirstExample, String commandSecondExample, String[] commandPermissions) {

		Panel detailsPanelBody = panel;
		detailsPanelBody.setBounds(0, 20, 609, 380);
		detailsPanelBody.setLayout(null);

		JTextPane txtpnCommandName = new JTextPane();
		txtpnCommandName.setText(org.apache.commons.lang3.StringUtils.capitalize(commandName));
		txtpnCommandName.setBackground(UIManager.getColor("Panel.background"));
		txtpnCommandName.setFont(new Font("Arial", Font.BOLD, 14));
		txtpnCommandName.setEditable(false);
		txtpnCommandName.setBounds(238, 11, 180, 29);
		detailsPanelBody.add(txtpnCommandName);

		JTextPane txtpnSyntax = new JTextPane();
		txtpnSyntax.setBackground(UIManager.getColor("Panel.background"));
		txtpnSyntax.setText("Syntax: " + commandUsage);
		txtpnSyntax.setFont(new Font("Arial", Font.PLAIN, 14));
		txtpnSyntax.setEditable(false);
		txtpnSyntax.setBounds(10, 55, 589, 29);
		detailsPanelBody.add(txtpnSyntax);

		JTextPane txtpnInfo = new JTextPane();
		txtpnInfo.setBackground(UIManager.getColor("Panel.background"));
		txtpnInfo.setText("This command " + commandInfo);
		txtpnInfo.setFont(new Font("Arial", Font.PLAIN, 14));
		txtpnInfo.setEditable(false);
		txtpnInfo.setBounds(10, 95, 589, 82);
		detailsPanelBody.add(txtpnInfo);

		if (commandFirstExample != null) {
			JTextPane txtpnFirstExample = new JTextPane();
			txtpnFirstExample.setBackground(UIManager.getColor("Panel.background"));
			txtpnFirstExample.setText("Example: " + commandFirstExample);
			txtpnFirstExample.setFont(new Font("Arial", Font.PLAIN, 14));
			txtpnFirstExample.setEditable(false);
			txtpnFirstExample.setBounds(10, 266, 589, 29);
			detailsPanelBody.add(txtpnFirstExample);
		}

		if (commandSecondExample != null) {
			JTextPane txtpnSecondExample = new JTextPane();
			txtpnSecondExample.setBackground(UIManager.getColor("Panel.background"));
			txtpnSecondExample.setText("Example: " + commandSecondExample);
			txtpnSecondExample.setFont(new Font("Arial", Font.PLAIN, 14));
			txtpnSecondExample.setEditable(false);
			txtpnSecondExample.setBounds(10, 306, 589, 29);
			detailsPanelBody.add(txtpnSecondExample);
		}

		if (commandPermissions != null) {
			JTextPane permissionsPane = new JTextPane();
			String permissions = "";
			for (int i = 0; i < commandPermissions.length; i++) {
				if (commandPermissions.length > 1 && i < commandPermissions.length - 1) {
				permissions += commandPermissions[i] + ", ";
				}
				else {
					permissions += commandPermissions[i];
				}
				if (i == commandPermissions.length) {
					permissions += commandPermissions[i];
				}
			}
			permissionsPane.setText("Required Permissions: " + permissions);
			permissionsPane.setFont(new Font("Arial", Font.PLAIN, 14));
			permissionsPane.setEditable(false);
			permissionsPane.setBackground(SystemColor.menu);
			permissionsPane.setBounds(10, 346, 589, 29);
			detailsPanelBody.add(permissionsPane);
		}
	}

	private void searchForResult(String searchValue, JScrollPane sidePanelBody, JPanel commandsSidePanel,
			JPanel sidePanelHeader, Panel detailsPanelBody, int headerDisplayValue) {
		lastResult = searchValue;
		previousSearchExists = true;
		sidePanelHeader.removeAll();
		updateSidePanelHeader(detailsPanelBody, sidePanelBody, sidePanelHeader, commandsSidePanel, headerDisplayValue);
		sidePanelHeader.revalidate();
		sidePanelHeader.repaint();
		sidePanelBody.setBounds(0, 21, 205, 380);
		commandsSidePanel.add(sidePanelBody);
		JList list = new JList();
		list.setFont(new Font("Arial", Font.PLAIN, 12));
		ArrayList<String> matchedResults = new ArrayList<String>();
		for (int i = 0; i < commands.CommandList.listOfCommands.size(); i++) {
			if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(commands.CommandList.listOfCommands.get(i).getName(), searchValue)) {
				matchedResults.add(commands.CommandList.listOfCommands.get(i).getName());
			}
			if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(commands.CommandList.listOfCommands.get(i).getInfo(), searchValue)) {
				if (!matchedResults.contains(commands.CommandList.listOfCommands.get(i).getName())) {
					matchedResults.add(commands.CommandList.listOfCommands.get(i).getName());
				}
			}
			if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(commands.CommandList.listOfCommands.get(i).getCommand(), searchValue)) {
				if (!matchedResults.contains(commands.CommandList.listOfCommands.get(i).getName())) {
					matchedResults.add(commands.CommandList.listOfCommands.get(i).getName());
				}
			}
			if (commands.CommandList.listOfCommands.get(i).getFirstExample() != null) {
				if (commands.CommandList.listOfCommands.get(i).getFirstExample().contains(searchValue)) {
					if (!matchedResults.contains(commands.CommandList.listOfCommands.get(i).getName())) {
						matchedResults.add(commands.CommandList.listOfCommands.get(i).getName());
					}
				}
			}
			if (commands.CommandList.listOfCommands.get(i).getSecondExample() != null) {
				if (commands.CommandList.listOfCommands.get(i).getSecondExample().contains(searchValue)) {
					if (!matchedResults.contains(commands.CommandList.listOfCommands.get(i).getName())) {
						matchedResults.add(commands.CommandList.listOfCommands.get(i).getName());
					}
				}
			}
		}

		list.setModel(new AbstractListModel() {
			private static final long serialVersionUID = 1L;

			public int getSize() {
				return matchedResults.size();
			}

			public Object getElementAt(int index) {
				return matchedResults.get(index);
			}
		});

		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				/* retrieve the node that was selected */
				for (int i = 0; i < commands.CommandList.listOfCommands.size(); i++) {
					Object selectedValue = list.getSelectedValue();
					if (commands.CommandList.listOfCommands.get(i).getName().equals(selectedValue)) {
						commands.CommandList.listOfCommands.get(i);
						detailsPanelBody.removeAll();
						updateDetailBody(detailsPanelBody, commands.CommandList.listOfCommands.get(i).getName(),
								commands.CommandList.listOfCommands.get(i).getUsage(),
								commands.CommandList.listOfCommands.get(i).getInfo(),
								commands.CommandList.listOfCommands.get(i).getFirstExample(),
								commands.CommandList.listOfCommands.get(i).getSecondExample(),
								commands.CommandList.listOfCommands.get(i).getPermissions());
						detailsPanelBody.revalidate();
						detailsPanelBody.repaint();
					}
				}
				/* React to the node selection. */

			}
		});
		sidePanelBody.add(list);
		sidePanelBody.setViewportView(list);
	}

	private void showLastResult(boolean previousSearchExists, JScrollPane sidePanelBody, JPanel commandsSidePanel,
			JPanel sidePanelHeader, Panel detailsPanelBody, int headerDisplayValue) {
		sidePanelHeader.removeAll();
		updateSidePanelHeader(detailsPanelBody, sidePanelBody, sidePanelHeader, commandsSidePanel, headerDisplayValue);
		sidePanelHeader.revalidate();
		sidePanelHeader.repaint();
		sidePanelBody.setBounds(0, 21, 205, 380);
		commandsSidePanel.add(sidePanelBody);
		if (previousSearchExists == true) {
			JList list = new JList();
			list.setFont(new Font("Arial", Font.PLAIN, 12));
			ArrayList<String> matchedResults = new ArrayList<String>();
			for (int i = 0; i < commands.CommandList.listOfCommands.size(); i++) {
				if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(commands.CommandList.listOfCommands.get(i).getName(), lastResult)) {
					matchedResults.add(commands.CommandList.listOfCommands.get(i).getName());
				}
				if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(commands.CommandList.listOfCommands.get(i).getInfo(), lastResult)) {
					if (!matchedResults.contains(commands.CommandList.listOfCommands.get(i).getName())) {
						matchedResults.add(commands.CommandList.listOfCommands.get(i).getName());
					}
				}
				if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(commands.CommandList.listOfCommands.get(i).getCommand(), lastResult)) {
					if (!matchedResults.contains(commands.CommandList.listOfCommands.get(i).getName())) {
						matchedResults.add(commands.CommandList.listOfCommands.get(i).getName());
					}
				}
				if (commands.CommandList.listOfCommands.get(i).getFirstExample() != null) {
					if (commands.CommandList.listOfCommands.get(i).getFirstExample().contains(lastResult)) {
						if (!matchedResults.contains(commands.CommandList.listOfCommands.get(i).getName())) {
							matchedResults.add(commands.CommandList.listOfCommands.get(i).getName());
						}
					}
				}
				if (commands.CommandList.listOfCommands.get(i).getSecondExample() != null) {
					if (commands.CommandList.listOfCommands.get(i).getSecondExample().contains(lastResult)) {
						if (!matchedResults.contains(commands.CommandList.listOfCommands.get(i).getName())) {
							matchedResults.add(commands.CommandList.listOfCommands.get(i).getName());
						}
					}
				}
			}

			list.setModel(new AbstractListModel() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public int getSize() {
					return matchedResults.size();
				}

				public Object getElementAt(int index) {
					return matchedResults.get(index);
				}
			});

			list.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					/* retrieve the node that was selected */
					for (int i = 0; i < commands.CommandList.listOfCommands.size(); i++) {
						Object selectedValue = list.getSelectedValue();
						if (commands.CommandList.listOfCommands.get(i).getName().equals(selectedValue)) {
							commands.CommandList.listOfCommands.get(i);
							detailsPanelBody.removeAll();
							updateDetailBody(detailsPanelBody, commands.CommandList.listOfCommands.get(i).getName(),
									commands.CommandList.listOfCommands.get(i).getUsage(),
									commands.CommandList.listOfCommands.get(i).getInfo(),
									commands.CommandList.listOfCommands.get(i).getFirstExample(),
									commands.CommandList.listOfCommands.get(i).getSecondExample(),
									commands.CommandList.listOfCommands.get(i).getPermissions());
							detailsPanelBody.revalidate();
							detailsPanelBody.repaint();
						}
					}
					/* React to the node selection. */

				}
			});
			sidePanelBody.add(list);
			sidePanelBody.setViewportView(list);
		}
		if (previousSearchExists == false) {
			JTextPane searchResultNoneTextPane = new JTextPane();
			searchResultNoneTextPane.setFont(new Font("Arial", Font.PLAIN, 12));
			searchResultNoneTextPane.setEditable(false);
			searchResultNoneTextPane.setText("No previous search");
			sidePanelBody.add(searchResultNoneTextPane);
			sidePanelBody.setViewportView(searchResultNoneTextPane);
		}
	}

	private Component helpInfoTextArea() {
		JTextArea detailPanelExplaination = new JTextArea();
		detailPanelExplaination.setEditable(false);
		detailPanelExplaination.setBackground(UIManager.getColor("Panel.background"));
		detailPanelExplaination.setWrapStyleWord(true);
		detailPanelExplaination.setFont(new Font("Arial", Font.PLAIN, 13));
		detailPanelExplaination.setLineWrap(true);
		detailPanelExplaination.setText(
				"By clicking one of the icons on the left you can open a sub-menu to that shows the contents in that menu. Clicking on a sub-menu will display the commands within. By clicking on a command it will then load the help page for the given command.");
		detailPanelExplaination.setBounds(10, 47, 568, 52);
		return detailPanelExplaination;
	}

	private Component helpInfoTitleArea() {
		JTextPane detailPanelTitle = new JTextPane();
		detailPanelTitle.setBackground(UIManager.getColor("Panel.background"));
		detailPanelTitle.setFont(new Font("Arial", Font.BOLD, 22));
		detailPanelTitle.setEditable(false);
		detailPanelTitle.setText("Using the help system");
		detailPanelTitle.setBounds(10, 11, 276, 36);
		return detailPanelTitle;
	}

	private Component helpInfoSearchTitleArea() {
		JTextPane detailPanelSearchTitle = new JTextPane();
		detailPanelSearchTitle.setText("Searching");
		detailPanelSearchTitle.setFont(new Font("Arial", Font.BOLD, 18));
		detailPanelSearchTitle.setEditable(false);
		detailPanelSearchTitle.setBackground(SystemColor.menu);
		detailPanelSearchTitle.setBounds(10, 110, 276, 36);
		return detailPanelSearchTitle;
	}

	private Component helpInfoSearchTextArea() {
		JTextArea detailPanelSearchExplaination = new JTextArea();
		detailPanelSearchExplaination.setWrapStyleWord(true);
		detailPanelSearchExplaination.setText(
				"By typing in parts or the full command you'll be supplied a list of commands that match the criteria. Upon clicking one of the results it'll bring up that commands respective help page. If you click the drop down menu it'll allow you to swap from the search results to the entire list of commands. If you do this from the commands tab it'll bring up the last search results if applicable.");
		detailPanelSearchExplaination.setLineWrap(true);
		detailPanelSearchExplaination.setFont(new Font("Arial", Font.PLAIN, 13));
		detailPanelSearchExplaination.setEditable(false);
		detailPanelSearchExplaination.setBackground(SystemColor.menu);
		detailPanelSearchExplaination.setBounds(10, 145, 568, 79);
		return detailPanelSearchExplaination;
	}
}
