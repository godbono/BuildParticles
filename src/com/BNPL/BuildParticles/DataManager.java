package com.BNPL.BuildParticles;

import static com.BNPL.BuildParticles.Main.*;

import org.bukkit.Material;

public class DataManager {

	public static void setWandType(Material mat) {
		cf.set("wand type", mat.name());
		save();
	}

	public static Material getWandType() {
		return Material.getMaterial(cf.getString("wand type").toUpperCase());
	}

	private static void save() {
		Main.instance.saveConfig();
	}

}
