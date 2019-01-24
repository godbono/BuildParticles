package com.BNPL.BuildParticles;

import static com.BNPL.BuildParticles.Main.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Util {

	public static String prefix = replaceColor(cf.getString("prefix"));

	public static void log(String text) {
		Bukkit.getConsoleSender().sendMessage(prefix + text);
	}

	public static void nplog(String text) {
		Bukkit.getConsoleSender().sendMessage(text);
	}

	public static String removeColor(String text) {
		return text.replaceAll("(」|&)[0-9A-FK-ORa-fk-or]", "");
	}

	public static String replaceColor(String text) {
		return text.replace("&1", "」1").replace("&2", "」2").replace("&3", "」3").replace("&4", "」4").replace("&5", "」5")
				.replace("&6", "」6").replace("&7", "」7").replace("&8", "」8").replace("&9", "」9").replace("&0", "」0")
				.replace("&a", "」a").replace("&b", "」b").replace("&c", "」c").replace("&d", "」d").replace("&e", "」e")
				.replace("&f", "」f").replace("&r", "」r").replace("&l", "」l").replace("&o", "」o").replace("&m", "」m");
	}

	public static void sendMessage(CommandSender sender, String text) {
		sender.sendMessage(prefix + text);
	}

	public static void sendMessage(CommandSender sender, String text, boolean isprefix) {
		sender.sendMessage((isprefix ? prefix : "") + text);
	}

	public static Player matchPlayer(String name) {
		name = name.toLowerCase();
		for (Player p : Bukkit.getOnlinePlayers())
			if (p.getName().toLowerCase().contains(name))
				return p;
		return null;
	}

	public static boolean isIntegerPositive(String text) {
		return text.matches("[0-9]+");
	}

	public static boolean isDoublePositive(String text) {
		return text.matches("([0-9]+|[0-9]+[.][0-9]+)");
	}

	public static List<String> makePage(List<String> list, int page, int size, boolean sort) {
		if (page <= 0 || page * size - (size - 1) > list.size())
			return null;
		if (sort)
			Collections.sort(list);
		List<String> contents = new ArrayList<>();
		for (int i = (page - 1) * size; i < page * size; i++) {
			contents.add(list.get(i));
			if (list.size() == (i + 1))
				break;
		}
		return contents;
	}

}
