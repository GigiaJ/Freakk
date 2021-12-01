package messages;

public class StringDecrypter {
	public static String decrypt(String input) {
		input = org.apache.commons.lang3.StringUtils.stripAccents(input);
		input = org.apache.commons.lang3.StringUtils.lowerCase(input);
		input = org.apache.commons.lang3.StringUtils.removePattern(input, "[^a-zA-Z0-9\\\\s]");
		return input;
	}
}
