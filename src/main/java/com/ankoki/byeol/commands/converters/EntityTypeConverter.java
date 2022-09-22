package com.ankoki.byeol.commands.converters;

import com.ankoki.byeol.commands.ArgumentConverter;
import org.bukkit.entity.EntityType;

public class EntityTypeConverter extends ArgumentConverter<EntityType> {

	@Override
	public EntityType convert(String argument) {
		return EntityType.fromName(argument);
	}

	@Override
	public Class<? extends EntityType> getReturnType() {
		return EntityType.class;
	}
}
