package bot.settings.settingLoaders;

public class LoadSettings extends SettingsLoader {
	protected static void checkForSettings(StringBuilder sb) {
		if (sb.toString().startsWith(SETTINGS + SPACE + CURLY_BRACKET_START)) {
			sb.replace(START, sb.indexOf(CURLY_BRACKET_START) + 1, EMPTY);
			while (sb.substring(START, sb.indexOf(CURLY_BRACKET_END)).contains(COMMA)) {
				if (sb.toString().substring(START, sb.indexOf(COMMA)).contains(BRACKET_START)) {
					if (sb.substring(START, sb.indexOf(BRACKET_END))
							.equals(sb.substring(sb.indexOf(BRACKET_START), sb.indexOf(BRACKET_END)))) {
						boolean[] nsfwFilters = new boolean[3];
						for (int i = 0; i < sb.substring(sb.indexOf(BRACKET_START), sb.indexOf(BRACKET_END)).split(", ").length; i++) {
							nsfwFilters[i] = Boolean.valueOf(sb.substring(sb.indexOf(BRACKET_START) + 1, sb.indexOf(BRACKET_END)).split(", ")[i]);
						}	
						settingsToLoad.add(nsfwFilters);
						sb.replace(sb.indexOf(BRACKET_START), sb.indexOf(BRACKET_END), EMPTY);
					}
				} else {
					settingsToLoad.add(sb.substring(START, sb.indexOf(COMMA)));
				}				
				sb.replace(START, sb.indexOf(COMMA) + 2, EMPTY);		
			}
			settingsToLoad.add(sb.substring(START, sb.indexOf(CURLY_BRACKET_END)));
		}
	}
}
