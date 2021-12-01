package commands.nsfw;

import java.awt.Color;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import messages.Message;


public class Invoker {

	public static Message nsfw(String site, String tags, String imageSite) throws NullPointerException {
		String content = "";
		try {
			if (tags.isEmpty()) {
				tags = "female";
			}
			
			int randomPage = (int) (10 * Math.random()) * 50;
			ArrayList<Image> images = new ArrayList<Image>();
			String address = "";
			StringBuilder copyOfAddress = new StringBuilder();
			int counter = 0;
			do {
				if (counter > 0) {
					randomPage = randomPage - 50;
				}
				address = PageLoader.doInBackground(
						"https://" + site + "/index.php?page=post&s=list&tags=" + tags + "&pid=" + randomPage);
				// Remove the once that aren't in the accepted range
				// Make a modifier to pick what you wish to filter

				// Make array that builds a list of the ID's and then randomly pick from them

				copyOfAddress = new StringBuilder();
				copyOfAddress.append(address);
				Image imageToAdd;
				if (copyOfAddress.toString().contains("<span id=\"s")) {
					while (copyOfAddress.toString().contains("<span id=\"s")) {
						int locationOfAddress = copyOfAddress.indexOf("<span id=\"s");
						copyOfAddress.replace(0, locationOfAddress + 11, "");
						String imageAddress = copyOfAddress.substring(0, copyOfAddress.indexOf("\""));
						copyOfAddress.replace(0, copyOfAddress.indexOf("rating:") + 7, "");
						String rating = copyOfAddress.substring(0, copyOfAddress.indexOf("\"")).trim();
						if (site != Rule34.SITE) 
						if (FilterManager.filters[0] == true && site != Rule34.SITE) {
							if (rating.equalsIgnoreCase(FilterManager.SAFE)) {
								imageToAdd = new Image(imageAddress, rating);
								images.add(imageToAdd);
							}
						}
						if (FilterManager.filters[1] == true || site == Rule34.SITE) {
							if (rating.equalsIgnoreCase(FilterManager.QUESTIONABLE)) {
								imageToAdd = new Image(imageAddress, rating);
								images.add(imageToAdd);
							}
						}
						if (FilterManager.filters[2] == true || site == Rule34.SITE) {
							if (rating.equalsIgnoreCase(FilterManager.EXPLICIT)) {
								imageToAdd = new Image(imageAddress, rating);
								images.add(imageToAdd);
							}
						}
					}
					counter++;

				} else {
					return null;
				}
			} while (images.isEmpty());

			int randomNumber = (int) (100 * Math.random());
			while (images.size() < randomNumber) {
				randomNumber = (int) (100 * Math.random());
			}
			String randomAddress = images.get(randomNumber).getID();
			if (randomAddress.contains("\"")) {
				randomAddress = randomAddress.replace("\"", "");
			}
						
			address = "";
			address = PageLoader.doInBackground("https://" + site + "/index.php?page=post&s=view&id=" + randomAddress);
			copyOfAddress = new StringBuilder();
			copyOfAddress.append(address);
			Matcher imageSearch = Pattern.compile("(?<=a href=\"" + imageSite + ")" + "(.*)(?=//images)")
					.matcher(copyOfAddress.toString());
			imageSearch.find();

			int imageAddressBegin = imageSearch.start();
			int imageAddressEnd = imageSearch.end();

			copyOfAddress.replace(imageAddressEnd, imageAddressEnd + 1, "");
			copyOfAddress.replace(0, imageAddressBegin, "");


			String imageLocation = copyOfAddress.substring(0, copyOfAddress.indexOf("\""));
			imageLocation = "https://" + imageLocation;

			if (imageLocation.equals("html PUBLIC ")) {
				nsfw(site, tags, imageSite);
				throw new NullPointerException();
			}


			content = site + "\n" + "**Tags:** " + tags + "\n" + imageLocation;
			return new Message(content);
		} catch (NullPointerException ignore) {
			return nsfw(site, tags, imageSite);
		}
	}
}
