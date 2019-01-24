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
			log("��Ŷ �������δ� ����� �Ұ����� ��ɾ� �Դϴ�.");
			return false;
		}
		Player p = (Player) sender;
		String playername = p.getName().toLowerCase();
		if (label.equals("��ƼŬ")) {
			if (!p.isOp()) {
				sendMessage(p, "����� ������ �����ϴ�.");
				return false;
			}
			if (args.length == 0) {
				sendMessage(p, "��c--- --- --- --- [ ��fBuildParticles ��c] --- --- --- ---", false);
				sendMessage(p, "", false);
				sendMessage(p, "/��ƼŬ ���� <����> ��a: ������ ��ġ�� ������ �����մϴ�.");
				sendMessage(p, "/��ƼŬ ��ȯ <����> <��ƼŬ��> [�÷��̾�] ��a: ������ ��ƼŬ�� ����մϴ�.");
				sendMessage(p, "/��ƼŬ �ϵ� ��a: ���� �����ϴ� ������ �տ� �� ���������� �����մϴ�.");
				sendMessage(p, "/��ƼŬ ���� <����> <����> ��a: ��ƼŬ�� ������ �����մϴ�. ��7(�⺻�� 0.3)");
				sendMessage(p, "/��ƼŬ ��� ��a: ������ ��ƼŬ���� ����� Ȯ���մϴ�.");
				sendMessage(p, "/��ƼŬ ��ƼŬ��� ��a: ��ƼŬ���� �̸��� Ȯ���մϴ�.");
				sendMessage(p, "", false);
				sendMessage(p, "��c--- --- --- --- [ ��fBuildParticles ��c] --- --- --- ---", false);
				return false;
			}
			if (args[0].equals("����")) {
				if (args.length != 2) {
					sendMessage(p, "/��ƼŬ ���� <����> ��a: ������ ��ġ�� ������ �����մϴ�.");
					return false;
				}
				if (!Listeners.clicks.containsKey(playername)) {
					sendMessage(p, "��a" + DataManager.getWandType().name() + " ��f��(��) �̿��Ͽ� �� ������ ������ �ּ���.");
					return false;
				}
				Positions pos = Listeners.clicks.get(playername);
				if (!pos.isFilled()) {
					sendMessage(p, "��a" + DataManager.getWandType().name() + " ��f��(��) �̿��Ͽ� �� ������ ������ �ּ���.");
					return false;
				}
				BlockParticle bp = BlockParticle.getParticle(args[1]);
				bp.clearDistance();
				int count = bp.makeDistances(pos, p.getLocation());
				sendMessage(p, "��a" + count + "�� ��f���� ��ġ�� ����Ǿ����ϴ�.");
				return false;
			}
			if (args[0].equals("��ȯ")) {
				if (args.length < 3) {
					sendMessage(p, "/��ƼŬ ��ȯ <����> <��ƼŬ��> [�÷��̾�] ��a: ������ ��ƼŬ�� ����մϴ�.");
					return false;
				}
				Player target = p;
				if (args.length > 3)
					target = matchPlayer(args[3]);

				if (!BlockParticle.containsParticle(args[1])) {
					sendMessage(p, "�������� �ʴ� ���� �Դϴ�. ��e(/��ƼŬ ���)");
					return false;
				}

				if (target == null) {
					sendMessage(p, "�÷��̾ ã�� �� �����ϴ�.");
					return false;
				}

				BlockParticle bp = BlockParticle.getParticle(args[1]);
				if (!bp.show(target.getLocation(), args[2])) {
					sendMessage(p, "��ƼŬ���� �ùٸ��� �ʽ��ϴ�. ��e(/��ƼŬ ��ƼŬ���)");
					return false;
				}
				return false;
			}
			if (args[0].equals("�ϵ�")) {
				Material hand = p.getItemInHand().getType();
				if (hand == Material.AIR) {
					sendMessage(p, "�տ� �������� �����ϴ�.");
					return false;
				}
				DataManager.setWandType(hand);
				sendMessage(p, "������ �Ϸ�Ǿ����ϴ�.");
				return false;
			}
			if (args[0].equals("����")) {
				if (args.length != 3) {
					sendMessage(p, "/��ƼŬ ��ȯ <����> <��ƼŬ��> [�÷��̾�] ��a: ������ ��ƼŬ�� ����մϴ�.");
					return false;
				}
				if (!BlockParticle.containsParticle(args[1])) {
					sendMessage(p, "�������� �ʴ� ���� �Դϴ�. ��e(/��ƼŬ ���)");
					return false;
				}

				if (!isDoublePositive(args[2])) {
					sendMessage(p, "������ �ùٸ��� �ʽ��ϴ�.");
					return false;
				}

				BlockParticle bp = BlockParticle.getParticle(args[1]);
				bp.setScale(Double.parseDouble(args[2]));
				sendMessage(p, "������ �Ϸ�Ǿ����ϴ�.");
				return false;
			}
			if (args[0].equals("���")) {
				int page = args.length > 1 && isIntegerPositive(args[1]) ? Integer.parseInt(args[1]) : 1;
				List<String> names = BlockParticle.getParticleNames();
				List<String> list = makePage(names, page, 10, true);
				if (list == null) {
					sendMessage(p, "�������� �ʴ� ������ �Դϴ�.");
					return false;
				}
				sendMessage(p, "", false);
				sendMessage(p, "��e[ ��f" + page + " / " + (int) Math.ceil((double) names.size() / 10) + " ������ ��e]");
				sendMessage(p, "", false);
				for (String s : list)
					sendMessage(p, s + " ��7(" + BlockParticle.getParticle(s).getDistances().size() + " ��)");
				sendMessage(p, "", false);
				return false;
			}
			if (args[0].equals("��ƼŬ���")) {
				int page = args.length > 1 && isIntegerPositive(args[1]) ? Integer.parseInt(args[1]) : 1;
				List<String> names = ParticleType.getParticleNames();
				List<String> list = makePage(names, page, 13, true);
				if (list == null) {
					sendMessage(p, "�������� �ʴ� ������ �Դϴ�.");
					return false;
				}
				sendMessage(p, "", false);
				sendMessage(p, "��e[ ��f" + page + " / " + (int) Math.ceil((double) names.size() / 13) + " ������ ��e]");
				sendMessage(p, "", false);
				for (String s : list)
					sendMessage(p, s.toUpperCase());
				sendMessage(p, "", false);
				return false;
			}
			sendMessage(p, "��c--- --- --- --- [ ��fBuildParticles ��c] --- --- --- ---", false);
			sendMessage(p, "", false);
			sendMessage(p, "/��ƼŬ ���� <����> ��a: ������ ��ġ�� ������ �����մϴ�.");
			sendMessage(p, "/��ƼŬ ��ȯ <����> <��ƼŬ��> [�÷��̾�] ��a: ������ ��ƼŬ�� ����մϴ�.");
			sendMessage(p, "/��ƼŬ �ϵ� ��a: ���� �����ϴ� ������ �տ� �� ���������� �����մϴ�.");
			sendMessage(p, "/��ƼŬ ���� <����> <����> ��a: ��ƼŬ�� ������ �����մϴ�. ��7(�⺻�� 0.3)");
			sendMessage(p, "/��ƼŬ ��� ��a: ������ ��ƼŬ���� ����� Ȯ���մϴ�.");
			sendMessage(p, "/��ƼŬ ��ƼŬ��� ��a: ��ƼŬ���� �̸��� Ȯ���մϴ�.");
			sendMessage(p, "", false);
			sendMessage(p, "��c--- --- --- --- [ ��fBuildParticles ��c] --- --- --- ---", false);
			return false;
		}
		return false;
	}

}
