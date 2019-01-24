package com.BNPL.BuildParticles;

import static com.BNPL.BuildParticles.Util.*;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.BNPL.BuildParticles.Listeners.Positions;
import com.BNPL.BuildParticles.ParticleDisplay.ParticleType;

public class Commands implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			log("버킷 권한으로는 사용이 불가능한 명령어 입니다.");
			return false;
		}
		Player p = (Player) sender;
		String playername = p.getName().toLowerCase();
		if (label.equals("파티클")) {
			if (!p.isOp()) {
				sendMessage(p, "당신은 권한이 없습니다.");
				return false;
			}
			if (args.length == 0) {
				sendMessage(p, "§c--- --- --- --- [ §fBuildParticles §c] --- --- --- ---", false);
				sendMessage(p, "", false);
				sendMessage(p, "/파티클 설정 <블럭명> §a: 지정한 위치의 블럭들을 저장합니다.");
				sendMessage(p, "/파티클 소환 <블럭명> <파티클명> [플레이어] §a: 설정된 파티클을 출력합니다.");
				sendMessage(p, "/파티클 완드 §a: 블럭을 지정하는 도구를 손에 든 아이템으로 설정합니다.");
				sendMessage(p, "/파티클 배율 <블럭명> <배율> §a: 파티클의 배율을 설정합니다. §7(기본값 0.3)");
				sendMessage(p, "/파티클 목록 §a: 설정된 파티클들의 목록을 확인합니다.");
				sendMessage(p, "/파티클 파티클목록 §a: 파티클들의 이름을 확인합니다.");
				sendMessage(p, "", false);
				sendMessage(p, "§c--- --- --- --- [ §fBuildParticles §c] --- --- --- ---", false);
				return false;
			}
			if (args[0].equals("설정")) {
				if (args.length != 2) {
					sendMessage(p, "/파티클 설정 <블럭명> §a: 지정한 위치의 블럭들을 저장합니다.");
					return false;
				}
				if (!Listeners.clicks.containsKey(playername)) {
					sendMessage(p, "§a" + DataManager.getWandType().name() + " §f을(를) 이용하여 블럭 범위를 설정해 주세요.");
					return false;
				}
				Positions pos = Listeners.clicks.get(playername);
				if (!pos.isFilled()) {
					sendMessage(p, "§a" + DataManager.getWandType().name() + " §f을(를) 이용하여 블럭 범위를 설정해 주세요.");
					return false;
				}
				BlockParticle bp = BlockParticle.getParticle(args[1]);
				bp.clearDistance();
				int count = bp.makeDistances(pos, p.getLocation());
				sendMessage(p, "§a" + count + "개 §f블럭의 위치가 저장되었습니다.");
				return false;
			}
			if (args[0].equals("소환")) {
				if (args.length < 3) {
					sendMessage(p, "/파티클 소환 <블럭명> <파티클명> [플레이어] §a: 설정된 파티클을 출력합니다.");
					return false;
				}
				Player target = p;
				if (args.length > 3)
					target = matchPlayer(args[3]);

				if (!BlockParticle.containsParticle(args[1])) {
					sendMessage(p, "존재하지 않는 블럭명 입니다. §e(/파티클 목록)");
					return false;
				}

				if (target == null) {
					sendMessage(p, "플레이어를 찾을 수 없습니다.");
					return false;
				}

				BlockParticle bp = BlockParticle.getParticle(args[1]);
				if (!bp.show(target.getLocation(), args[2])) {
					sendMessage(p, "파티클명이 올바르지 않습니다. §e(/파티클 파티클목록)");
					return false;
				}
				return false;
			}
			if (args[0].equals("완드")) {
				Material hand = p.getItemInHand().getType();
				if (hand == Material.AIR) {
					sendMessage(p, "손에 아이템이 없습니다.");
					return false;
				}
				DataManager.setWandType(hand);
				sendMessage(p, "설정이 완료되었습니다.");
				return false;
			}
			if (args[0].equals("배율")) {
				if (args.length != 3) {
					sendMessage(p, "/파티클 소환 <블럭명> <파티클명> [플레이어] §a: 설정된 파티클을 출력합니다.");
					return false;
				}
				if (!BlockParticle.containsParticle(args[1])) {
					sendMessage(p, "존재하지 않는 블럭명 입니다. §e(/파티클 목록)");
					return false;
				}

				if (!isDoublePositive(args[2])) {
					sendMessage(p, "배율이 올바르지 않습니다.");
					return false;
				}

				BlockParticle bp = BlockParticle.getParticle(args[1]);
				bp.setScale(Double.parseDouble(args[2]));
				sendMessage(p, "설정이 완료되었습니다.");
				return false;
			}
			if (args[0].equals("목록")) {
				int page = args.length > 1 && isIntegerPositive(args[1]) ? Integer.parseInt(args[1]) : 1;
				List<String> names = BlockParticle.getParticleNames();
				List<String> list = makePage(names, page, 10, true);
				if (list == null) {
					sendMessage(p, "존재하지 않는 페이지 입니다.");
					return false;
				}
				sendMessage(p, "", false);
				sendMessage(p, "§e[ §f" + page + " / " + (int) Math.ceil((double) names.size() / 10) + " 페이지 §e]");
				sendMessage(p, "", false);
				for (String s : list)
					sendMessage(p, s + " §7(" + BlockParticle.getParticle(s).getDistances().size() + " 블럭)");
				sendMessage(p, "", false);
				return false;
			}
			if (args[0].equals("파티클목록")) {
				int page = args.length > 1 && isIntegerPositive(args[1]) ? Integer.parseInt(args[1]) : 1;
				List<String> names = ParticleType.getParticleNames();
				List<String> list = makePage(names, page, 13, true);
				if (list == null) {
					sendMessage(p, "존재하지 않는 페이지 입니다.");
					return false;
				}
				sendMessage(p, "", false);
				sendMessage(p, "§e[ §f" + page + " / " + (int) Math.ceil((double) names.size() / 13) + " 페이지 §e]");
				sendMessage(p, "", false);
				for (String s : list)
					sendMessage(p, s.toUpperCase());
				sendMessage(p, "", false);
				return false;
			}
			sendMessage(p, "§c--- --- --- --- [ §fBuildParticles §c] --- --- --- ---", false);
			sendMessage(p, "", false);
			sendMessage(p, "/파티클 설정 <블럭명> §a: 지정한 위치의 블럭들을 저장합니다.");
			sendMessage(p, "/파티클 소환 <블럭명> <파티클명> [플레이어] §a: 설정된 파티클을 출력합니다.");
			sendMessage(p, "/파티클 완드 §a: 블럭을 지정하는 도구를 손에 든 아이템으로 설정합니다.");
			sendMessage(p, "/파티클 배율 <블럭명> <배율> §a: 파티클의 배율을 설정합니다. §7(기본값 0.3)");
			sendMessage(p, "/파티클 목록 §a: 설정된 파티클들의 목록을 확인합니다.");
			sendMessage(p, "/파티클 파티클목록 §a: 파티클들의 이름을 확인합니다.");
			sendMessage(p, "", false);
			sendMessage(p, "§c--- --- --- --- [ §fBuildParticles §c] --- --- --- ---", false);
			return false;
		}
		return false;
	}

}
