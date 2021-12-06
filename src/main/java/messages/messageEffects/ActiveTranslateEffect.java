package messages.messageEffects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static main.Main.settings;

public class ActiveTranslateEffect {
	public static StringBuilder translater(StringBuilder messageToSend) {
		if (settings.getTranslatorStatus() == true) {
			try {
				return new StringBuilder(translate("en", "fr", messageToSend.toString()).replaceAll("&#39;", "’"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return messageToSend;
	}

	private static String translate(String langFrom, String langTo, String text) throws IOException {
		// INSERT YOU URL HERE
		String urlStr = "https://script.google.com/macros/s/AKfycbz-UIrn-eCpqj5QUCU8RVzgubHsIfTaTnzFlTA5CdzOHLkrStXTYsqFBlF2j_dAHxAHnA/exec" +
				"?q=" + URLEncoder.encode(text, "UTF-8") +
				"&target=" + langTo +
				"&source=" + langFrom;
		URL url = new URL(urlStr);
		StringBuilder response = new StringBuilder();
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
}
