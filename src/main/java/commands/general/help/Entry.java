package commands.general.help;

import commands.Command;

public class Entry {
	protected int type;
	protected Command command;
	private final int EMB_LENGTH_CAP = 97;

	protected String entry;

	public Entry(Command command) {
		StringBuilder entry = new StringBuilder();
		String entryBuilder = "";
		for (int i = 0; i < EMB_LENGTH_CAP; i++) {
			entryBuilder += "-";
		}
		entry.append(entryBuilder + "\n");
		// Title
		entry.append("**" + command.getType().getType() + ")" + "** "
				+ org.apache.commons.lang3.StringUtils.capitalize(command.getName()) + "\n");
		// Syntax
		entry.append("**" + "Syntax:" + "** " + command.getUsage() + "\n");
		// Info
		entry.append("**" + command.getInfo() + "**" + "\n");
		// Example
		if (command.getFirstExample() != null) {
			entry.append("Example: " + "\n");
			entry.append("\t" + command.getFirstExample() + "\n");
			if (command.getSecondExample() != null) {
				entry.append("\t" + command.getSecondExample() + "\n");
			}
		}
		if (command.getPermissions() != null) {
			String[] commandPermissions = command.getPermissions();
			String permissions = "";
			for (int i = 0; i < commandPermissions.length; i++) {
				if (commandPermissions.length > 1 && i < commandPermissions.length - 1) {
					permissions += commandPermissions[i] + ", ";
				} else {
					permissions += commandPermissions[i];
				}
				if (i == commandPermissions.length) {
					permissions += commandPermissions[i];
				}
			}

			entry.append("Required Permissions: " + permissions + "\n");
		}
		entry.append(entryBuilder + "\n");
		this.entry = entry.toString();
	}

	public String getEntry() {
		return entry;
	}

}
