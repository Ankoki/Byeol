package com.ankoki.byeol.commands.converters;

import com.ankoki.byeol.commands.ArgumentConverter;

public class DoubleConverter extends ArgumentConverter<Double> {

	@Override
	public Double convert(String argument) {
		try {
			return Double.parseDouble(argument);
		} catch (NumberFormatException ex) {
			return -1D;
		}
	}

	@Override
	public Class<? extends Double> getReturnType() {
		return Double.class;
	}
}
