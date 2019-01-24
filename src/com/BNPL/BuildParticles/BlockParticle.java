package com.BNPL.BuildParticles;

import static com.BNPL.BuildParticles.Main.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;

import com.BNPL.BuildParticles.Listeners.Positions;
import com.BNPL.BuildParticles.ParticleDisplay.ParticleType;

public class BlockParticle {

	private String name;
	private List<Distance> distances;

	private static List<BlockParticle> particles;

	public BlockParticle(String name) {
		this.name = name;
		this.distances = new ArrayList<>();
		loadDistances();
		particles.add(this);
	}

	static void loadAll() {
		particles = new ArrayList<>();
		for (String p : getParticleNamesFromConfig())
			new BlockParticle(p);
	}

	public void setScale(double scale) {
		cf.set("파티클." + name + ".배율", scale);
		save();
	}

	public float getScale() {
		return (float) cf.getDouble("파티클." + name + ".배율", 0.3f);
	}

	public int makeDistances(Positions pos, Location center) {
		int startX = pos.start.getBlockX();
		int startY = pos.start.getBlockY();
		int startZ = pos.start.getBlockZ();

		int endX = pos.end.getBlockX();
		int endY = pos.end.getBlockY();
		int endZ = pos.end.getBlockZ();

		if (startX >= endX) {
			int d = startX;
			startX = endX;
			endX = d;
		}

		if (startY >= endY) {
			int d = startY;
			startY = endY;
			endY = d;
		}

		if (startZ >= endZ) {
			int d = startZ;
			startZ = endZ;
			endZ = d;
		}

		center = center.getBlock().getLocation();
		int count = 0;
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				for (int z = startZ; z <= endZ; z++) {

					int axisX = center.getBlockX() - x;
					int axisY = y - center.getBlockY();
					int axisZ = center.getBlockZ() - z;

					Location loc = new Location(center.getWorld(), x, y, z);
					if (loc.getBlock().getTypeId() != 0) {
						addDistance(new Distance(axisX, axisY, axisZ));
						count++;
					}
				}
			}
		}
		return count;
	}

	private void loadDistances() {
		if (!cf.contains("파티클." + name + ".거리"))
			return;
		for (String s : cf.getStringList("파티클." + name + ".거리"))
			addDistance(Distance.getDistanceFromString(s));
	}

	public void addDistance(Distance dis) {
		distances.add(dis);
		refreshDistance();
	}

	public void removeDistance(Distance dis) {
		distances.remove(dis);
		refreshDistance();
	}

	public void removeDistance(int index) {
		distances.remove(index);
		refreshDistance();
	}

	public void clearDistance() {
		distances.clear();
		refreshDistance();
	}

	public List<Distance> getDistances() {
		return distances;
	}

	private void refreshDistance() {
		List<String> list = new ArrayList<>();
		for (Distance dis : distances)
			list.add(dis.toString());
		cf.set("파티클." + name + ".거리", list.isEmpty() ? null : list);
		save();
	}

	public boolean show(Location loc, String particleName) {
		return show(loc, particleName, getScale());
	}

	public boolean show(Location loc, String particleName, float scale) {

		for (Distance dis : distances) {

			float x = loc.getBlockX() + dis.getX() * scale;
			float y = loc.getBlockY() + dis.getY() * scale;
			float z = loc.getBlockZ() + dis.getZ() * scale;

			try {
				new ParticleDisplay(ParticleType.getTypeFromName(particleName), 0, 1, 0).display(x, y, z);
			} catch (Exception ex) {
				return false;
			}
		}
		return true;
	}

	private void save() {
		Main.instance.saveConfig();
	}

	public String getName() {
		return name;
	}

	public static void addParticle(BlockParticle bp) {
		particles.add(bp);
	}

	public static void removeParticle(BlockParticle bp) {
		particles.remove(bp);
	}

	public static void removeParticle(int i) {
		particles.remove(i);
	}

	public static boolean containsParticle(String name) {
		return cf.getKeys(true).contains("파티클." + name);
	}

	public static BlockParticle getParticle(String name) {
		for (BlockParticle bp : particles)
			if (bp.getName().equals(name))
				return bp;
		return new BlockParticle(name);
	}

	public static List<BlockParticle> getParticles() {
		return particles;
	}

	public static List<String> getParticleNames() {
		List<String> list = new ArrayList<>();
		for (BlockParticle bp : particles)
			list.add(bp.getName());
		return list;
	}

	public static List<String> getParticleNamesFromConfig() {
		Set<String> set = cf.getKeys(true);
		List<String> list = new ArrayList<>();
		for (int i = 0; i < set.size(); ++i) {
			String s = set.toArray()[i].toString();
			if (s.startsWith("파티클.") && s.endsWith(".거리"))
				list.add(s.replace("파티클.", "").replace(".거리", ""));
		}
		return list;
	}

}
