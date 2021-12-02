package bot.settings.settingLoaders;

import static main.Main.settings;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import bot.settings.ISetting;
import bot.settings.SettingBuilder;
import bot.settings.SettingDefault;
import bot.settings.SettingEnum;
import macro.Macro;
import main.VersionEnum;

public class SettingsLoader implements ISetting {
	protected static ArrayList<Object> settingsToLoad = new ArrayList<Object>();

	public static void loadSettings() throws FileNotFoundException, IOException {
		String line = "";
		try (InputStream fis = new FileInputStream(main.Main.settingsFile);
				InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
				BufferedReader br = new BufferedReader(isr);) {
			while ((line = br.readLine()) != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(line);

				if (!sb.toString().startsWith(SETTINGS + SPACE + CURLY_BRACKET_START)
						&& !sb.toString().startsWith(BWL + SPACE + CURLY_BRACKET_START)
						&& !sb.toString().startsWith(MACROS + SPACE + CURLY_BRACKET_START) && !line.isEmpty()) {
					break;
				}
				LoadSettings.checkForSettings(sb);
				LoadBannedWordList.checkForBWL(sb);
				LoadMacros.checkForMacros(sb);
			}
			br.close();
			isr.close();
			fis.close();
		}
		applySettings();
	}

	@SuppressWarnings("unchecked")
	public static void applySettings() {
		SettingBuilder builder = new SettingBuilder();
		if (settingsToLoad.isEmpty() || !settingsToLoad.get(SettingEnum.VERSION.getValue()).equals(VersionEnum.VERSION)) {
			try {
				SettingDefault.setSettingDefault();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!settingsToLoad.isEmpty()) {
			builder.setCurrentVersion((String) settingsToLoad.get(SettingEnum.VERSION.getValue()));
			builder.setAutoColorStatus(
					Boolean.valueOf((String) settingsToLoad.get(SettingEnum.AUTOCOLORSTATUS.getValue())));
			builder.setColorChatStatus(
					Boolean.valueOf((String) settingsToLoad.get(SettingEnum.COLORCHATSTATUS.getValue())));
			builder.setEmbedMessageStatus(
					Boolean.valueOf((String) settingsToLoad.get(SettingEnum.EMBEDMESSAGE.getValue())));
			builder.setAuthorEmbedStatus(
					Boolean.valueOf((String) settingsToLoad.get(SettingEnum.AUTHOREMBED.getValue())));
			builder.setReverserStatus(Boolean.valueOf((String) settingsToLoad.get(SettingEnum.REVERSER.getValue())));
			builder.setNoSpaceStatus(Boolean.valueOf((String) settingsToLoad.get(SettingEnum.NOSPACE.getValue())));
			builder.setSpongeBobChickenCapStatus(Boolean.valueOf((String) settingsToLoad.get(SettingEnum.SPONGEBOBCAPS.getValue())));
			builder.setColorChatColor((String) settingsToLoad.get(SettingEnum.CHAT_COLOR.getValue()));
			builder.setEmbedColor((String) settingsToLoad.get(SettingEnum.EMBED_COLOR.getValue()));
			builder.setCurrentFontStyle((String) settingsToLoad.get(SettingEnum.FONTSTYLE.getValue()));
			builder.setCurrentFontSize(Integer.valueOf((String) settingsToLoad.get(SettingEnum.FONTSIZE.getValue())));
			builder.setNsfwFilters((boolean[]) settingsToLoad.get(SettingEnum.NSFWFILTER.getValue()));
			builder.setCommandSign((String) settingsToLoad.get(SettingEnum.COMMANDSIGN.getValue()));
			builder.setAdminCommandSign((String) settingsToLoad.get(SettingEnum.ADMINCOMMANDSIGN.getValue()));
			if (settingsToLoad.size() >= SettingEnum.BANNED_WORD_LIST.getValue()) {
				builder.setBannedWordList(
						(ArrayList<String>) settingsToLoad.get(SettingEnum.BANNED_WORD_LIST.getValue()));
			}
			if (settingsToLoad.size() >= SettingEnum.MACROS.getValue()) {
				builder.setMacros((ArrayList<Macro>) settingsToLoad.get(SettingEnum.MACROS.getValue()));
			}
			settings = builder.build();
		} else {
			try {
				SettingDefault.setSettingDefault();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
