package com.ankoki.byeol.commands.converters;

import com.ankoki.byeol.commands.ArgumentConverter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerConverter extends ArgumentConverter<Player> {

	@Override
	public Player convert(String argument) {
		return Bukkit.getPlayer(argument);
	}

	@Override
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}
}
