package commands.general.help;

public enum EntryType {
	GUI(0), DISCORD(1);

	private final int value;

	EntryType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
// 97 * -