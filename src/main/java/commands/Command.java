package commands;

import java.lang.reflect.Method;

public class Command {

	protected final CommandType type;
	protected final String name;
	protected final String cmdSign;
	protected final String adminSign;
	protected final String info;
	protected final String usage;
	protected final String firstExample;
	protected final String secondExample;
	protected final String[] requiredPermissions;
	protected final Method method;
	
	public Command(CommandBuilder builder) {
		this.name = builder.name;
		this.type = builder.type;
		this.cmdSign = builder.cmdSign;
		this.adminSign = builder.adminSign;
		this.info = builder.info;
		this.usage = builder.usage;
		this.firstExample = builder.firstExample;
		this.secondExample = builder.secondExample;
		this.requiredPermissions = builder.requiredPermissions;
		this.method = builder.method;
	}
	
	public String getCommand() {
		return (this.cmdSign + this.adminSign + this.name);
	}
	
	public CommandType getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	
	public String getCmdSign() {
		return cmdSign;
	}
	
	public String getAdminSign() {
		return adminSign;
	}
	
	public String getInfo() {
		return info;
	}
	
	public String getUsage() {
		return usage;
	}
	
	public String getFirstExample() {
		return firstExample;
	}
	
	public String getSecondExample() {
		return secondExample;
	}
	
	public String[] getPermissions() {
		return requiredPermissions;
	}
	
	public Method getMethod() {
		return method;
	}
	
 }
