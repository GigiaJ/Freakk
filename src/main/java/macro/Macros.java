package macro;

import java.util.ArrayList;

import bot.settings.SettingSaver;

public class Macros {
	public static ArrayList<Macro> macros = new ArrayList<Macro>();

	public static void applySettings() {
		if (main.Main.settings.getMacros() != null) {
			macros = main.Main.settings.getMacros();
		}
	}

	public static void deleteMacros(Macro macro) {
		Macro[] macroArray = new Macro[macros.size()];
		for (int t = 0; t < macros.size(); t++) {
			macroArray[t] = macros.get(t);
		}
		for (int i = 0; i < macroArray.length; i++) {
			if (macroArray[i].getName().equals(macro.getName())) {
				macros.remove(i);
			}
		}
		SettingSaver.saveSettings();
	}

	public static void editMacro(Macro macro, Macro newMacro) {

		// Array list was acting buggy and not properly comparing two EQUAL macros so I
		// instead converted it to the type array of macros and then located the match
		// then removed based on int location

		Macro[] macroArray = new Macro[macros.size()];
		for (int t = 0; t < macros.size(); t++) {
			macroArray[t] = macros.get(t);
		}
		for (int i = 0; i < macroArray.length; i++) {
			if (macroArray[i].getName().equals(macro.getName())) {
				macros.remove(i);
			}
		}
		macros.add(newMacro);
		SettingSaver.saveSettings();
	}

	public static void createMessageMacro(String macroName, String macroAction, MacroType type) {
		Macro newMacro = new Macro(macroName, macroAction, type);
		macros.add(newMacro);
		SettingSaver.saveSettings();
	}
}
