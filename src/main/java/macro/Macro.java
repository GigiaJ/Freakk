package macro;

public class Macro {

	protected String name;
	protected String action;
	protected MacroType type;

	public Macro(String name, String action, MacroType type) {
		setName(name);
		setAction(action);
		setType(type);
	}

	public Macro(String[] split) {
		this.name = split[0];
		this.action = split[1];
		this.type = MacroType.valueOf(split[2]);
	}

	public MacroType getType() {
		return type;
	}

	public String getAction() {
		return action;
	}

	public String getName() {
		return name;
	}

	public void setType(MacroType type) {
		this.type = type;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setName(String name) {
		this.name = name;
	}

}
