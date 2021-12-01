package commands;

import java.lang.reflect.Method;

public class CommandBuilder {
	protected final CommandType type;
	protected final String name;
	protected final String cmdSign;
	protected final String adminSign;
	protected final String info;
	protected final String usage;
	protected String firstExample;
	protected String secondExample;
	protected String[] requiredPermissions;
	protected Method method;

	public CommandBuilder(String name, String cmdSign, String adminSign, CommandType type, String info, String usage) {
		this.name = name;
		this.cmdSign = cmdSign;
		this.adminSign = adminSign;
		this.type = type;
		this.info = info;
		while (usage.contains("thisCommand")) {
			String thisCommand = cmdSign + adminSign + name;
			usage = usage.replaceAll("thisCommand", thisCommand);
		}
		this.usage = usage;
	}

	public CommandBuilder addFirstExample(String firstExample) {
		while (firstExample.contains("thisCommand")) {
			String thisCommand = this.cmdSign + this.adminSign + this.name;
			firstExample = firstExample.replaceAll("thisCommand", thisCommand);
		}
		this.firstExample = firstExample;
		return this;
	}

	public CommandBuilder addSecondExample(String secondExample) {
		while (secondExample.contains("thisCommand")) {
			String thisCommand = this.cmdSign + this.adminSign + this.name;
			secondExample = secondExample.replaceAll("thisCommand", thisCommand);
		}
		this.secondExample = secondExample;
		return this;
	}

	public CommandBuilder addPermissionRequirements(String[] permissions) {
		this.requiredPermissions = permissions;
		return this;
	}

	public CommandBuilder addMethod(Method method) {
		this.method = method;
		return this;
	}

	public Command build() {
		return new Command(this);
	}
}
