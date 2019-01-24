package com.BNPL.BuildParticles;

import static com.BNPL.BuildParticles.Util.*;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main instance;
	public static FileConfiguration cf;

	public void onEnable() {
		instance = this;
		CreateConfig();
		BlockParticle.loadAll();
		CommandExcutor();
		RegEvents();
		EnableMsg();
	}

	private void CreateConfig() {
		saveDefaultConfig();
		cf = getConfig();
	}

	private void CommandExcutor() {
		String[] cmd = { "��ƼŬ" };
		for (String s : cmd)
			getCommand(s).setExecutor(new Commands());
	}

	private void RegEvents() {
		getServer().getPluginManager().registerEvents(new Listeners(), this);
	}

	private void EnableMsg() {
		nplog("��a-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
		nplog("");
		nplog("��f��������������������������");
		nplog("��f��������������������������".replace("��", "��b���f"));
		nplog("��f��������������������������".replace("��", "��b���f"));
		nplog("��f��������������������������".replace("��", "��b���f"));
		nplog("��f��������������������������".replace("��", "��b���f"));
		nplog("��f��������������������������".replace("��", "��b���f"));
		nplog("��f��������������������������".replace("��", "��b���f"));
		nplog("��f��������������������������".replace("��", "��b���f"));
		nplog("��f��������������������������".replace("��", "��b���f"));
		nplog("��f��������������������������".replace("��", "��b���f"));
		nplog("��f��������������������������".replace("��", "��b���f"));
		nplog("��f��������������������������");
		nplog("");
		log("�÷������� Ȱ��ȭ �Ǿ����ϴ�!");
		log("������ ��α� : http://blog.naver.com/godbono/");
		log("�� �÷����� ���� : v" + this.getDescription().getVersion());
		nplog("");
		nplog("��a-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
	}
}
