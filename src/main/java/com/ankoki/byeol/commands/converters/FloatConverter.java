package com.ankoki.byeol.commands.converters;

import com.ankoki.byeol.commands.ArgumentConverter;

public class FloatConverter extends ArgumentConverter<Float> {

	@Override
	public Float convert(String argument) {
		try {
			return Float.parseFloat(argument);
		} catch (NumberFormatException ex) {
			return -1F;
		}
	}

	@Override
	public Class<? extends Float> getReturnType() {
		return Float.class;
	}
}
