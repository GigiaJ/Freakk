package macro;

import static commands.CommandList.cmdSign;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

public class MacroPanelSelect extends JPanel {
	protected static int TOTAL_COLUMNS = 2;

	public MacroPanelSelect() {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 * 
	 * @return
	 */
	public static void MacroOptions(MacroType type, JPanel panel) {
		panel.setBackground(Color.LIGHT_GRAY);
		JTextPane informNoSelectPane = new JTextPane();
		informNoSelectPane.setBackground(Color.LIGHT_GRAY);
		informNoSelectPane.setEditable(false);
		informNoSelectPane.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		informNoSelectPane.setText("Please select a desired option");

		JButton btnNewMacro = new JButton("Create New Macro");
		btnNewMacro.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnNewMacro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.removeAll();
				createMacro(type, panel);
				panel.revalidate();
				panel.repaint();
			}
		});

		JButton btnViewMacros = new JButton("View Macros");
		btnViewMacros.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnViewMacros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.removeAll();
				viewMacros(type, panel);
				panel.revalidate();
				panel.repaint();
			}
		});
		GroupLayout groupLayout = new GroupLayout(panel);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(140)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnViewMacros, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewMacro).addComponent(informNoSelectPane, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(164, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(informNoSelectPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnNewMacro)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnViewMacros)
						.addContainerGap(206, Short.MAX_VALUE)));
		panel.setLayout(groupLayout);
	}

	static JTable table = new JTable() {
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	public static void viewMacros(MacroType type, JPanel panel) {

		table.setFont(new Font("Times New Roman", Font.PLAIN, 12));

		JTextPane editInput = new JTextPane();
		editInput.setFont(new Font("Times New Roman", Font.PLAIN, 12));

		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		for (int i = 0; i < Macros.macros.size(); i++) {
			keys.add(Macros.macros.get(i).getName());
			values.add(Macros.macros.get(i).getAction());
		}
		
		String[] columns = new String[] { "Macro Name", "Macro Action" };
		Object[][] data = new Object[keys.size()][TOTAL_COLUMNS];

		for (int i = 0; i < keys.size(); i++) {
			for (int t = 0; t < TOTAL_COLUMNS; t++) {
				data[i][t] = keys.get(i);
				t = t + 1;
				data[i][t] = values.get(i);
			}
		}
		table.setModel(new DefaultTableModel(data, columns));

		JButton submit = new JButton("Submit");
		submit.setFont(new Font("Times New Roman", Font.PLAIN, 12));

		JButton mainMenu = new JButton("Return");
		mainMenu.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		mainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.removeAll();
				MacroManager.mainMenu(panel);
			}
		});

		JButton editMacro = new JButton("Edit Macro");
		editMacro.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		editMacro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table.setCellSelectionEnabled(true);
				int column = table.getSelectedColumn();
				int row = table.getSelectedRow();
				if (row != -1) {
					Object edittingCell = table.getValueAt(row, column);
					editInput.setText(edittingCell.toString());
					submit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							boolean fail = false;
							Macro macro = null;
							Macro newMacro = null;
							String macroToChange = "";
							String actionToChange = "";
							if (editInput.getText().trim().equals("")) {
								fail = true;
								editInput.setText("Please don't leave this field blank.");
							}
							for (int i = 0; i < commands.CommandList.listOfCommands.size(); i++) {
								if (editInput.getText().equals(commands.CommandList.listOfCommands.get(i).getName()) && (column == 0)) {
									editInput.setText("This prefix is already being used by Skipper's commands.");
									fail = true;
								}
							}

							for (int i = 0; i < Macros.macros.size(); i++) {
								if (editInput.getText().equals(Macros.macros.get(i).getName()) && (column == 0)) {
									editInput.setText("This macro already exists.");
									fail = true;
								}
							}
							if (editInput.getText().length() > 2000) {
								editInput.setText("Message is longer than 2000 characters, please shorten it by "
										+ (2000 - editInput.getText().length()) + ".");
								fail = true;
							}
							if (fail == false) {
								if (keys.contains(edittingCell.toString())) {
									for (int i = 0; i < Macros.macros.size(); i++) {
										if (Macros.macros.get(i).getName().equals(edittingCell.toString())) {
											macroToChange = keys.get(i);
											actionToChange = values.get(i);
											macro = new Macro(macroToChange.trim(), actionToChange.trim(),
													MacroType.MESSAGE);
											newMacro = new Macro(editInput.getText(), actionToChange,
													MacroType.MESSAGE);

										}
									}
								}
								if (values.contains(edittingCell.toString())) {
									for (int i = 0; i < Macros.macros.size(); i++) {
										if (Macros.macros.get(i).getAction().equals(edittingCell.toString())) {
											macroToChange = keys.get(i);
											actionToChange = values.get(i);
											macro = new Macro(macroToChange.trim(), actionToChange.trim(),
													MacroType.MESSAGE);
											newMacro = new Macro(macroToChange, editInput.getText(), MacroType.MESSAGE);
										}
									}
								}
								if (editInput.getText().equals("")) {
									editInput.setText("Please don't leave this field blank.");
								}

								Macros.editMacro(macro, newMacro);

								panel.removeAll();
								viewMacros(type, panel);
								panel.revalidate();
								panel.repaint();
							}
						}
					});
				}
			}
		});

		JButton deleteMacro = new JButton("Delete Macro");
		deleteMacro.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		deleteMacro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table.setCellSelectionEnabled(true);
				int column = table.getSelectedColumn();
				int row = table.getSelectedRow();

				if (row != -1) {
					Object edittingCell = table.getValueAt(row, column);
					String macroToDelete = "";
					String actionToDelete = "";
					Macro macro = null;
					if (keys.contains(edittingCell.toString()) && (column == 0)) {
						for (int i = 0; i < Macros.macros.size(); i++) {
							if (Macros.macros.get(i).getName().equals(edittingCell.toString())) {
								macroToDelete = keys.get(row);
								actionToDelete = values.get(row);
								macro = new Macro(macroToDelete.trim(), actionToDelete.trim(), MacroType.MESSAGE);

							}
						}
					}
					if (values.contains(edittingCell.toString()) && column == 1) {
						for (int i = 0; i < Macros.macros.size(); i++) {
							if (Macros.macros.get(i).getAction().equals(edittingCell.toString())) {
								macroToDelete = keys.get(row);
								actionToDelete = values.get(row);
								macro = new Macro(macroToDelete.trim(), actionToDelete.trim(), MacroType.MESSAGE);
							}
						}
					}
					Macros.deleteMacros(macro);
					panel.removeAll();
					viewMacros(type, panel);
					panel.revalidate();
					panel.repaint();
				}
			}
		});

		JTextPane title = new JTextPane();
		title.setBackground(Color.LIGHT_GRAY);
		title.setEditable(false);
		title.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		title.setText("Highlight a cell and select a below option");

		GroupLayout groupLayout = new GroupLayout(panel);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(table, GroupLayout.PREFERRED_SIZE, panel.getWidth(), GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup().addContainerGap(131, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup().addComponent(deleteMacro)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(editMacro))
								.addComponent(editInput, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(submit)
								.addComponent(mainMenu))
						.addGap(54))
				.addGroup(groupLayout.createSequentialGroup().addContainerGap(145, Short.MAX_VALUE)
						.addComponent(title, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE).addGap(134)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap(20, Short.MAX_VALUE)
						.addComponent(title, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
						.addComponent(table, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(submit)
								.addComponent(editInput, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(editMacro)
								.addComponent(mainMenu).addComponent(deleteMacro))
						.addGap(20)));
		panel.setLayout(groupLayout);

	}

	public static void createMacro(MacroType type, JPanel panel) {
		if (type == MacroType.MESSAGE) {
			panel.setBackground(Color.LIGHT_GRAY);

			JTextPane instructMacroPane = new JTextPane();
			instructMacroPane.setBackground(Color.LIGHT_GRAY);
			instructMacroPane.setEditable(false);
			instructMacroPane.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			instructMacroPane.setText("Please enter the desired macro in the space below");

			JTextPane macroSlashPane = new JTextPane();
			macroSlashPane.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			macroSlashPane.setText(cmdSign);
			macroSlashPane.setBackground(Color.LIGHT_GRAY);
			macroSlashPane.setEditable(false);

			JTextPane macroNamePane = new JTextPane();
			macroNamePane.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JTextPane macroUseExplainPane = new JTextPane();
			macroUseExplainPane.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			macroUseExplainPane.setBackground(Color.LIGHT_GRAY);
			macroUseExplainPane.setEditable(false);
			macroUseExplainPane.setText("This will be how the macro is used");

			JTextPane macroMessagePane = new JTextPane();
			macroMessagePane.setFont(new Font("Times New Roman", Font.PLAIN, 12));

			JTextPane macroExplainPane = new JTextPane();
			macroExplainPane.setBackground(Color.LIGHT_GRAY);
			macroExplainPane.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			macroExplainPane.setEditable(false);
			macroExplainPane.setText("This will be the text that the macro sends");

			JTextPane blankInformPane = new JTextPane();
			blankInformPane.setBackground(Color.LIGHT_GRAY);
			blankInformPane.setFont(new Font("Times New Roman", Font.PLAIN, 12));

			JButton submitButtom = new JButton("Submit");
			submitButtom.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			submitButtom.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					boolean fail = false;
					for (int i = 0; i < commands.CommandList.listOfCommands.size(); i++) {
						if (macroNamePane.getText().equals(commands.CommandList.listOfCommands.get(i).getName())) {
							blankInformPane.setText("This prefix is already being used by Skipper's commands.");
							fail = true;
						}
					}

					for (int i = 0; i < Macros.macros.size(); i++) {
						if (macroNamePane.getText().equals(Macros.macros.get(i).getName())) {
							blankInformPane.setText("This macro already exists.");
							fail = true;
						}
					}
					if (macroNamePane.getText().isEmpty()) {
						blankInformPane.setText("Please don't leave the macro name blank.");
						fail = true;
					}
					if (macroMessagePane.getText().isEmpty()) {
						blankInformPane.setText("Please don't leave the macro message blank.");
						fail = true;
					}
					if (macroMessagePane.getText().length() > 2000) {
						blankInformPane.setText("Message is longer than 2000 characters, please shorten it by "
								+ (2000 - macroMessagePane.getText().length()) + ".");
						fail = true;
					}

					if (fail == false) {
						Macros.createMessageMacro(macroNamePane.getText(), macroMessagePane.getText(),
								MacroType.MESSAGE);
						panel.removeAll();
						MacroManager.mainMenu(panel);
					}
				}
			});

			GroupLayout groupLayout = new GroupLayout(panel);
			groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
					.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup().addGap(22).addComponent(instructMacroPane,
									GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup().addGap(42)
									.addComponent(
											macroSlashPane, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
											.addComponent(macroMessagePane, GroupLayout.DEFAULT_SIZE, 301,
													Short.MAX_VALUE)
											.addComponent(macroNamePane)))
							.addGroup(groupLayout.createSequentialGroup().addGap(116)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
											.addComponent(blankInformPane, GroupLayout.PREFERRED_SIZE, 178,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(macroExplainPane, GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(27, Short.MAX_VALUE))
					.addGroup(groupLayout.createSequentialGroup().addGap(126)
							.addComponent(macroUseExplainPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE)
							.addContainerGap(151, Short.MAX_VALUE))
					.addGroup(
							groupLayout
									.createSequentialGroup().addGap(166).addComponent(submitButtom,
											GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(181)));
			groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup().addContainerGap()
							.addComponent(instructMacroPane, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addGap(40)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(macroSlashPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(macroNamePane))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(macroUseExplainPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(macroMessagePane, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(macroExplainPane, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(submitButtom, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
									Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(blankInformPane, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addGap(10)));
			panel.setLayout(groupLayout);

		}
	}

}
