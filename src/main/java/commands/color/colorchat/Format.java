package commands.color.colorchat;

import java.util.ArrayList;
import java.util.Iterator;

public class Format {
	protected String text;
	protected ArrayList<Formatting> format;
	protected int adjustedStartPos;
	protected int adjustedEndPos;
	protected int textStartPos;
	protected int textEndPos;
	protected int startPos;
	protected int endPos;
	protected int displacement;
	protected String url;

	public Format(String text, Formatting format, int startPos, int endPos) {
		this.text = text.substring(format.getFormattingOffset(), text.length() - format.getFormattingOffset());
		this.format = new ArrayList();
		this.format.add(format);
		if (!format.name().equals(Formatting.EMOJI.name())) {
			this.textStartPos = startPos + format.getFormattingOffset();
			this.textEndPos = endPos - format.getFormattingOffset();
			this.startPos = startPos;
			this.endPos = endPos;
			this.adjustedStartPos = startPos;
			this.adjustedEndPos = endPos - format.getFormattingOffset() * 2;
			this.displacement = format.getFormattingOffset() * 2;
		} else {
			this.startPos = startPos;
			this.endPos = startPos + 3;
			this.adjustedStartPos = startPos;
			this.adjustedEndPos = startPos + 3;
			this.displacement = 3;
		}

	}

	public Format reformat(int startPos, int endPos, Formatting format) {
		if (this.isInsideFormatting(startPos, endPos)) {
			if (this.preventFormattingConflicts(format)) {
				return this;
			}

			this.text = this.text.substring(format.getFormattingOffset(),
					this.text.length() - format.getFormattingOffset());
		}

		if (this.isOutsideFormatting(startPos, endPos) && this.preventFormattingConflicts(format)) {
			return this;
		} else if (this.getFormat().contains(Formatting.EMOJI)) {
			return this;
		} else {
			this.format.add(format);
			if (startPos < this.startPos) {
				this.startPos = startPos;
			}

			if (endPos > this.endPos) {
				this.endPos = endPos;
			}

			this.adjustedStartPos = this.startPos;
			this.adjustedEndPos = this.endPos - this.getDisplacement();
			return this;
		}
	}

	public boolean isAlreadyFormatted(int startPos, int endPos) {
		if (this.isInsideFormatting(startPos, endPos)) {
			return true;
		} else {
			return this.isOutsideFormatting(startPos, endPos);
		}
	}

	public boolean isInsideFormatting(int startPos, int endPos) {
		return this.startPos >= startPos && this.endPos <= endPos;
	}

	public boolean isOutsideFormatting(int startPos, int endPos) {
		return this.startPos <= startPos && this.endPos >= endPos;
	}

	public boolean isIntersectingFormatting(int startPos, int endPos) {
		if (startPos <= this.endPos && endPos >= this.endPos) {
			return true;
		} else {
			return startPos <= this.startPos && endPos >= this.startPos;
		}
	}

	public boolean preventFormattingConflicts(Formatting format) {
		if (format.equals(Formatting.STAR_ITALICS)) {
			if (this.format.contains(Formatting.BOLDITALICS)) {
				return true;
			}

			if (this.format.contains(Formatting.BOLD)) {
				return true;
			}
		}

		if (format.equals(Formatting.BOLD) && this.format.contains(Formatting.BOLDITALICS)) {
			return true;
		} else {
			if (format.equals(Formatting.UNDERSCORE_ITALICS)) {
				if (this.format.contains(Formatting.UNDERLINEITALICS)) {
					return true;
				}

				if (this.format.contains(Formatting.UNDERLINE)) {
					return true;
				}
			}

			return format.equals(Formatting.UNDERLINE) && this.format.contains(Formatting.UNDERLINEITALICS);
		}
	}

	public ArrayList<Formatting> getFormat() {
		return this.format;
	}

	public void setFormat(ArrayList<Formatting> format) {
		this.format = format;
	}

	public int getTextStartPos() {
		return this.textStartPos;
	}

	public void setTextStartPos(int textStartPos) {
		this.textStartPos = textStartPos;
	}

	public int getTextEndPos() {
		return this.textEndPos;
	}

	public void setTextEndPos(int textEndPos) {
		this.textEndPos = textEndPos;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public int getStartPos() {
		return this.startPos;
	}

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	public int getEndPos() {
		return this.endPos;
	}

	public void setEndPos(int endPos) {
		this.endPos = endPos;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getAdjustedStartPos() {
		return this.adjustedStartPos;
	}

	public void setAdjustedStartPos(int adjustedStartPos) {
		this.adjustedStartPos = adjustedStartPos;
	}

	public int getAdjustedEndPos() {
		return this.adjustedEndPos;
	}

	public void setAdjustedEndPos(int adjustedEndPos) {
		this.adjustedEndPos = adjustedEndPos;
	}

	public int getDisplacement() {
		int displacement = 0;

		Formatting format;
		for (Iterator var3 = this.format.iterator(); var3.hasNext(); displacement += format.getFormattingOffset() * 2) {
			format = (Formatting) var3.next();
		}

		return displacement;
	}
}
