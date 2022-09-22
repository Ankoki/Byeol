package com.ankoki.byeol.commands.converters;

import com.ankoki.byeol.commands.ArgumentConverter;

public class LongConverter extends ArgumentConverter<Long> {

	@Override
	public Long convert(String argument) {
		try {
			return Long.parseLong(argument);
		} catch (NumberFormatException ex) {
			return -1L;
		}
	}

	@Override
	public Class<? extends Long> getReturnType() {
		return Long.class;
	}
}
