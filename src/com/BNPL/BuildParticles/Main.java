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
		String[] cmd = { "파티클" };
		for (String s : cmd)
			getCommand(s).setExecutor(new Commands());
	}

	private void RegEvents() {
		getServer().getPluginManager().registerEvents(new Listeners(), this);
	}

	private void EnableMsg() {
		nplog("§a-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
		nplog("");
		nplog("§f□□□□□□□□□□□□□□□□□□□□□□□□□");
		nplog("§f□■■■■□□■□□□■□■■■■□□■□□□□□".replace("■", "§b■§f"));
		nplog("§f□■□□□■□■□□□■□■□□□■□■□□□□□".replace("■", "§b■§f"));
		nplog("§f□■□□□■□■■□□■□■□□□■□■□□□□□".replace("■", "§b■§f"));
		nplog("§f□■□□□■□■■□□■□■□□□■□■□□□□□".replace("■", "§b■§f"));
		nplog("§f□■■■■□□■□■□■□■□□□■□■□□□□□".replace("■", "§b■§f"));
		nplog("§f□■□□□■□■□■□■□■■■■□□■□□□□□".replace("■", "§b■§f"));
		nplog("§f□■□□□■□■□□■■□■□□□□□■□□□□□".replace("■", "§b■§f"));
		nplog("§f□■□□□■□■□□■■□■□□□□□■□□□□□".replace("■", "§b■§f"));
		nplog("§f□■□□□■□■□□□■□■□□□□□■□□□□□".replace("■", "§b■§f"));
		nplog("§f□■■■■□□■□□□■□■□□□□□■■■■■□".replace("■", "§b■§f"));
		nplog("§f□□□□□□□□□□□□□□□□□□□□□□□□□");
		nplog("");
		log("플러그인이 활성화 되었습니다!");
		log("제작자 블로그 : http://blog.naver.com/godbono/");
		log("본 플러그인 버전 : v" + this.getDescription().getVersion());
		nplog("");
		nplog("§a-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
	}
}
