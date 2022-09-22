package com.ankoki.byeol.commands.converters;

import com.ankoki.byeol.commands.ArgumentConverter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class OfflinePlayerConverter extends ArgumentConverter<OfflinePlayer> {

	@Override
	public OfflinePlayer convert(String argument) {
		return Bukkit.getOfflinePlayer(argument);
	}

	@Override
	public Class<? extends OfflinePlayer> getReturnType() {
		return OfflinePlayer.class;
	}
}
