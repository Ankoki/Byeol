package com.ankoki.byeol.commands;

import com.ankoki.byeol.Byeol;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandHandler {

	private static final CommandHandler INSTANCE = new CommandHandler();

	/**
	 * Returns the CommandHandler instance.
	 * @return the instance.
	 */
	public static CommandHandler get() {
		return INSTANCE;
	}

	private final List<ArgumentConverter<?>> converters = new ArrayList<>();

	/**
	 * Registers created converters.
	 * @param converters the converters.
	 */
	public void registerConverters(ArgumentConverter<?>... converters) {
		this.converters.addAll(List.of(converters));
	}

	/**
	 * Checks if a converter for a type exists.
	 * @param clazz the class to check for.
	 * @return true if it exists.
	 */
	public boolean hasConverter(Class<?> clazz) {
		for (ArgumentConverter<?> converter : converters) {
			if (converter.getReturnType() == clazz) return true;
		} return false;
	}

	/**
	 * Gets the converter for a class.
	 * @param clazz the class to check for.
	 * @return the converter for a class, if it doesn't exist, return null;
	 */
	@Nullable
	public ArgumentConverter<?> getConverter(Class<?> clazz) {
		for (ArgumentConverter<?> converter : converters) {
			if (converter.getReturnType() == clazz) return converter;
		} return null;
	}

	/**
	 * Registers a class to have its annotated methods be a command.
	 * @param clazz the class to register.
	 * @return true if successful.
	 */
	public boolean registerCommand(Class<?> clazz) {
		boolean allSucceeded = true;
		for (Method method : clazz.getDeclaredMethods()) {
			method.setAccessible(true);
			if (method.isAnnotationPresent(CommandHook.class) &&
					(method.getReturnType() == void.class || method.getReturnType() == boolean.class)) {
				CommandHook hook = method.getAnnotation(CommandHook.class);
				Command command = new Command(hook.name()) {
					@Override
					public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
						final Class<?>[] types = method.getParameterTypes();
						final Object[] parameters = new Object[types.length];
						int i = 0;
						boolean skipFirst = true;
						if (types.length > 0 && types[0].isAssignableFrom(CommandSender.class)) {
							parameters[0] = sender;
							i++;
						} else skipFirst = false;
						for (Class<?> parameter : types) {
							if (skipFirst) {
								skipFirst = false;
								continue;
							}
							if (args.length < (i - 1)) parameters[i] = null;
							else if (parameter.isAssignableFrom(String.class)) parameters[i] = args[i];
							else if (!CommandHandler.get().hasConverter(parameter)) parameters[i] = null;
							else parameters[i] = CommandHandler.get().getConverter(parameter).convert(args[i]);
							i++;
						}
						if (types.length > 0 &&
								types[types.length - 1] == String.class &&
								args.length > (types[0].isAssignableFrom(CommandSender.class) ? types.length - 1 : types.length)) {
							final int size = args.length;
							final String[] fin = new String[size];
							int index = 0;
							for (String arg : args) {
								if (index < (types.length - (types[0].isAssignableFrom(CommandSender.class) ? 2 : 1))) {
									index++;
									continue;
								}
								fin[index] = arg + " ";
								index++;
							}
							String last = fin[fin.length - 1];
							fin[fin.length - 1] = last.substring(0, last.length() - 1);
							parameters[parameters.length - 1] = String.join("", fin);
						}
						try {
							Object obj = method.invoke(clazz.newInstance(), parameters);
							if (obj instanceof Boolean bool) return bool;
							return true;
						} catch (ReflectiveOperationException ex) {
							ex.printStackTrace();
						} return true;
					}
				};
				allSucceeded = Bukkit.getCommandMap().register(Byeol.get().getPlugin().getName(), command) && allSucceeded;
			}
		}
		return allSucceeded;
	}
}
