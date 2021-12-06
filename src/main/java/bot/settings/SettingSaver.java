package bot.settings;

import static main.Main.settings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

import macro.Macros;

public class SettingSaver implements ISetting {

	public static void saveSettings() {
		File settingsFile = main.Main.settingsFile;
		try {
			main.Main.settingsFile.delete();
			main.Main.settingsFile.createNewFile();
			FileWriter fileWriter = new FileWriter(settingsFile, true);
			PrintWriter printer = new PrintWriter(fileWriter);
			printer.print(SETTINGS + SPACE + CURLY_BRACKET_START);
			printer.print(settings.getCurrentVersion() + COMMA);
			printer.print(Boolean.toString(settings.getAutoColorStatus()) + COMMA);
			printer.print(Boolean.toString(settings.getColorChatStatus()) + COMMA);
			printer.print(Boolean.toString(settings.getEmbedMessageStatus()) + COMMA);
			printer.print(Boolean.toString(settings.getTranslatorStatus()) + COMMA);
			printer.print(Boolean.toString(settings.getReverserStatus()) + COMMA);
			printer.print(Boolean.toString(settings.getNoSpaceStatus()) + COMMA);
			printer.print(Boolean.toString(settings.getSpongebobChickenCapsStatus()) + COMMA);
			printer.print(settings.getColorChatColor() + COMMA);
			printer.print(settings.getEmbedColor() + COMMA);
			printer.print(settings.getCurrentFontStyle() + COMMA);
			printer.print(settings.getCurrentFontSize() + COMMA);
			printer.print(BRACKET_START);
			for (int i = 0; i < settings.getNsfwFilters().length; i++) {
				printer.print(Boolean.valueOf(settings.getNsfwFilters()[i]));
				if (i < settings.getNsfwFilters().length - 1) {
					printer.print(COMMA);
				}
			}
			printer.print(BRACKET_END);
			printer.print(COMMA);
			printer.print(settings.getCommandSign() + COMMA);
			printer.print(settings.getAdminCommandSign() + CURLY_BRACKET_END);
			printer.println();
			printer.println();
			printer.print(BWL + SPACE + CURLY_BRACKET_START);
			printer.print(CURLY_BRACKET_END);
			printer.println();
			printer.println();
			printer.print(MACROS + SPACE + CURLY_BRACKET_START);
			for (int i = 0; i < Macros.macros.size(); i++) {
				printer.print(MACRO);
				printer.print(PARENTHESES_START);
				printer.print(Macros.macros.get(i).getName() + COMMA);
				printer.print(Macros.macros.get(i).getAction() + COMMA);
				printer.print(Macros.macros.get(i).getType());
				printer.print(PARENTHESES_END);
				if (i < (Macros.macros.size() - 1))
					printer.print(COMMA);
			}
			printer.print(CURLY_BRACKET_END);

			printer.println();
			printer.close();
			fileWriter.close();
		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}
}
