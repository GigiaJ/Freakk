package commands.nsfw;

public class Image {
	protected String id;
	protected String rating;
	
	Image(String id, String rating) {
		setID(id);
		setRating(rating);
	}
	public void setID(String id) {
		this.id = id;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getID() {
		return id;
	}
	public String getRating() {
		return rating;
	}
}
