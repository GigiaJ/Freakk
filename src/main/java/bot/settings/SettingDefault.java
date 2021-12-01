package bot.settings;

import static main.Main.settings;

import java.io.IOException;

import main.VersionEnum;

public class SettingDefault {
	public static void setSettingDefault() throws IOException {
		SettingBuilder builder = new SettingBuilder();
		builder.setCurrentVersion(VersionEnum.VERSION.getVersion());
		builder.setAutoColorStatus(false);
		builder.setColorChatStatus(false);
		builder.setEmbedMessageStatus(false);
		builder.setAuthorEmbedStatus(false);	
		builder.setReverserStatus(false);
		builder.setNoSpaceStatus(false);
		builder.setSpongeBobChickenCapStatus(false);
		builder.setColorChatColor("#ff0000");
		builder.setEmbedColor("#ff0000");
		builder.setCurrentFontStyle("Whitney");
		builder.setCurrentFontSize(14);
		builder.setNsfwFilters(new boolean[] {true, false, false});
		builder.setCommandSign("/");
		builder.setAdminCommandSign("/");
		builder.setMacros(null);
		builder.setBannedWordList(null);
		settings = builder.build();
		main.StartUp.deleteDirectory(main.Main.baseFolder);
		main.StartUp.checkForFiles();
		SettingSaver.saveSettings();
	}
}
