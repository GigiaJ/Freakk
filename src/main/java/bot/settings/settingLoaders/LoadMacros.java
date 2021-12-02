package bot.settings.settingLoaders;

import java.util.ArrayList;

import macro.Macro;

public class LoadMacros extends SettingsLoader {
	protected static void checkForMacros(StringBuilder sb) {
		ArrayList<Macro> macros = new ArrayList<>();
		if (sb.toString().startsWith(MACROS + SPACE + CURLY_BRACKET_START)) {
			sb.replace(START, sb.indexOf(CURLY_BRACKET_START), "");
			while (sb.substring(0).contains(PARENTHESES_START)) {
				for (int i = START; i < sb
						.substring((sb.indexOf(MACRO + PARENTHESES_START) + (MACRO + PARENTHESES_START).length()),
								sb.indexOf(PARENTHESES_END))
						.split(REGEX).length; i++) {
					String macroString = sb.substring((sb.indexOf(MACRO + PARENTHESES_START) + (MACRO + PARENTHESES_START).length()),
							sb.indexOf(PARENTHESES_END));
					Macro macro = new Macro(macroString.split(COMMA));
					macros.add(macro);
							/*new Macro(
							macro.split(REGEX)[i]/*
									macro.substring(0,
											sb.substring(
													(sb.indexOf(MACRO + PARENTHESES_START)
															+ (MACRO + PARENTHESES_START).length()),
													sb.indexOf(PARENTHESES_END)).split(REGEX)[i].length())

									.split(COMMA)));*/
				}
				sb.replace(0, sb.indexOf(PARENTHESES_END) + 1, "");
			}
		settingsToLoad.add(macros);
		}
	}
}
