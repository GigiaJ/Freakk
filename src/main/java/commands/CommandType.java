package commands;

public enum CommandType {
	GENERAL(1), COLOR(2), ADVANCED(3), IDENTIFIER(4), NSFW(5), MANAGER(6), MALICIOUS(7);

	private String type = "";

	CommandType(int value) {
		String type = "";
		switch (value) {
		case 1:
			type = "General";
			break;
		case 2:
			type = "Color";
			break;
		case 3:
			type = "Advanced";
			break;
		case 4:
			type = "Identifier";
			break;
		case 5:
			type = "NSFW";
			break;
		case 6:
			type = "Management";
			break;
		case 7:
			type = "Malicious";
			break;
		default:
			type = "None";
			break;
		}
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
}
