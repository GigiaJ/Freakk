package bot.settings.settingLoaders;

import java.util.ArrayList;
import java.util.Arrays;

public class LoadBannedWordList extends SettingsLoader {
	protected static void checkForBWL(StringBuilder sb) {
		if (sb.toString().startsWith(BWL + SPACE + CURLY_BRACKET_START)) {
			ArrayList<String> bwl = new ArrayList<String>();
			sb.replace(START, sb.indexOf(CURLY_BRACKET_START), EMPTY);
			if (sb.substring(sb.indexOf(CURLY_BRACKET_START)).contains(BRACKET_START)) {
				bwl.addAll(new ArrayList<String>(Arrays
						.asList(sb.substring(sb.indexOf(BRACKET_START) + 1, sb.indexOf(BRACKET_END)).split(COMMA))));
				sb.replace(START, sb.indexOf(BRACKET_END) + 1, ""); // Clears the previous entry
			}
			settingsToLoad.add(bwl);
		}
	}
}
