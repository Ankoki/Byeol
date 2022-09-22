package com.ankoki.byeol.commands.converters;

import com.ankoki.byeol.commands.ArgumentConverter;

import java.text.NumberFormat;
import java.text.ParseException;

public class NumberConverter extends ArgumentConverter<Number> {

	@Override
	public Number convert(String argument) {
		try {
			return NumberFormat.getInstance().parse(argument);
		} catch (ParseException ex) {
			return -1;
		}
	}

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
}
