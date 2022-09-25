package com.ankoki.byeol;

import com.ankoki.byeol.commands.CommandHandler;
import com.ankoki.byeol.commands.converters.*;
import com.ankoki.byeol.metrics.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public final class Byeol {
	private Byeol() {}

	private static final Byeol INSTANCE = new Byeol();

	public static Byeol get() {
		return INSTANCE;
	}

	private final Logger logger = new Logger("BYEOL", null) {
		@Override
		public void log(LogRecord record) {
			super.log(record);
		}
	};
	private JavaPlugin plugin;
	private Metrics metrics;

	/**
	 * Gets the plugin Byeol is registered with.
	 * @return the registered plugin if set.
	 */
	@Nullable
	public JavaPlugin getPlugin() {
		return plugin;
	}

	/**
	 * Gets the Byeol logger.
	 * @return Byeol's logger.
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * Runs all tasks Byeol needs to function.
	 * @param plugin the plugin to base it on.
	 */
	public void setup(JavaPlugin plugin) {
		this.plugin = plugin;
		this.logger.setParent(Bukkit.getLogger());
		this.logger.setLevel(Level.ALL);
		this.metrics = new Metrics(plugin,16476);
		// Thank you bStats for this good package checking feature:)
		final String defaultPackage =
				new String(new byte[]{'c', 'o', 'm', '.', 'a', 'n', 'k', 'o', 'k', 'i', '.', 'b', 'y', 'e', 'o', 'l'});
		if (Byeol.class.getPackage().getName().startsWith(defaultPackage)) {
			final List<String> authorsList = plugin.getDescription().getAuthors();
			final String authors = String.join(", ", authorsList);
			logger.warning("The plugin '" + plugin.getName() + "' has not relocated the Byeol library from its default package.");
			logger.warning("This can cause issues if other plugins on the server use it. Please contact the developer(s) " +
					(authors.isEmpty() ? "" : "(" + authors + ") ") + "and make them aware of this.");
		}
		CommandHandler.get().registerConverters(
				new IntegerConverter(),
				new LongConverter(),
				new DoubleConverter(),
				new FloatConverter(),
				new NumberConverter(),
				new PlayerConverter(),
				new OfflinePlayerConverter(),
				new EntityTypeConverter()
		);
		this.getLogger().info(plugin.getName() + " has successfully hooked into Byeol.");
	}
}
