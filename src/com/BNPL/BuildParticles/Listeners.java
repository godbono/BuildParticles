package com.BNPL.BuildParticles;

import static com.BNPL.BuildParticles.Util.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Listeners implements Listener {

	static Map<String, Positions> clicks = new HashMap<>();

	public class Positions {

		Location start;
		Location end;

		private int set(Action action, Location loc) {
			if (action == Action.LEFT_CLICK_BLOCK) {
				start = loc;
				return 1;
			} else {
				end = loc;
				return 2;
			}
		}

		public boolean isFilled() {
			return start != null && end != null;
		}

	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		// 우클릭 시 offhand, mainhand 이벤트가 각각 실행됨.
		try {
			Method getHandMethod = PlayerInteractEvent.class.getMethod("getHand");
			Object obj = getHandMethod.invoke(e);
			Class<?> EquipmentSlotClazz = Class.forName("org.bukkit.inventory.EquipmentSlot");
			Object offhand = EquipmentSlotClazz.getField("OFF_HAND").get(null);

			if (obj == offhand)
				return;

		} catch (Exception ex) {
		}

		Player player = e.getPlayer();
		String playername = player.getName().toLowerCase();
		Action action = e.getAction();
		Material hand = player.getItemInHand().getType();

		if (player.isOp())
			if (hand == DataManager.getWandType()) {
				if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {

					Positions pos = new Positions();
					if (clicks.containsKey(playername))
						pos = clicks.get(playername);

					Location loc = e.getClickedBlock().getLocation();
					int n = pos.set(action, loc);
					clicks.put(playername, pos);
					e.setCancelled(true);
					sendMessage(player,
							n + "번 포지션의 위치가 설정되었습니다. §7(" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")");

				}
			}

	}

}
