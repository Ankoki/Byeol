package com.ankoki.byeol.commands.converters;

import com.ankoki.byeol.commands.ArgumentConverter;

public class IntegerConverter extends ArgumentConverter<Integer> {

	@Override
	public Integer convert(String argument) {
		try {
			return Integer.parseInt(argument);
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
}
