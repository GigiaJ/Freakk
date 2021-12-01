package commands.general.help;

import java.util.ArrayList;

import commands.CommandList;

public interface HelpInterface {
	String helpTitle = "Help List";
	ArrayList<ArrayList<Entry>> helpPages = new ArrayList<ArrayList<Entry>>();
	static final int COMMANDS_TO_DISPLAY = 4;

	public static void createDiscordHelpPages() {
		helpPages.clear();
		for (int t = 0; t < CommandList.listOfCommands.size();) {
			ArrayList<Entry> helpPage = new ArrayList<Entry>();
			for (int i = 0; i < COMMANDS_TO_DISPLAY; i++) {
				Entry createdEntry = new Entry(CommandList.listOfCommands.get(t));
				helpPage.add(createdEntry);
				t++;
				if (t == CommandList.listOfCommands.size()) {
					i = COMMANDS_TO_DISPLAY;
				}
			}
			helpPages.add(helpPage);
		}
	}
	public static void createGUIHelpPages() {
		helpPages.clear();
		for (int t = 0; t < CommandList.listOfCommands.size();) {
			ArrayList<Entry> helpPage = new ArrayList<Entry>();
			for (int i = 0; i < COMMANDS_TO_DISPLAY; i++) {
				Entry createdEntry = new Entry(CommandList.listOfCommands.get(t));
				helpPage.add(createdEntry);
				t++;
				if (t == CommandList.listOfCommands.size()) {
					i = COMMANDS_TO_DISPLAY;
				}
			}
			helpPages.add(helpPage);
		}
	}
}
