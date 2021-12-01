package macro;

public enum MacroType {
	NONE(0),
	MESSAGE(1),
	JOIN(2),
	LEAVE(3);

	private final int value;
		MacroType(int value)
	    {
	        this.value = value;
	    }
	    public int getValue()
	    {
	        return value;
	    }
}
