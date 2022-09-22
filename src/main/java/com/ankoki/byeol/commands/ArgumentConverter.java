package com.ankoki.byeol.commands;

public abstract class ArgumentConverter<To> {
	public abstract To convert(String argument);
	public abstract Class<? extends To> getReturnType();
}
