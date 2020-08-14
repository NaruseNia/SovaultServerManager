package com.nia.sovaultservermanager;

import com.nia.sovaultservermanager.command.MenuCommandExecutor;
import com.nia.sovaultservermanager.command.StatCommandExecutor;
import com.nia.sovaultservermanager.command.TeamCommandExecutor;
import com.nia.sovaultservermanager.command.TimerCommandExecutor;
import com.nia.sovaultservermanager.discord.webhook.DiscordWebHook;
import com.nia.sovaultservermanager.listener.ClickEventListener;
import com.nia.sovaultservermanager.test.TestCommandExecutor;
import com.nia.sovaultservermanager.util.PropertyLoader;
import com.nia.sovaultservermanager.util.PropertyType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public final class SovaultServerManager extends JavaPlugin {

    private static SovaultServerManager instance;

    private BukkitTask timerTask = null;
	private Sound timerSound;
    private int timerTime;
    private String timerTitle;
    private String timerSubtitle;

    private static Properties properties;
    private static Properties stat;
    private static Properties webHookProp;

    private static DiscordWebHook webHook;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        //プロパティファイルの読み込み
        try {
            properties = new PropertyLoader("messages/message.properties").getProperties();
            stat = new PropertyLoader("messages/stat.properties").getProperties();
            webHookProp = new PropertyLoader("webHook.properties").getProperties();
        } catch (IOException e) {
            getLogger().warning("プロパティファイルを読み込めませんでした。");
            e.printStackTrace();
        }

        //DiscordのWebHookの作成
        try {
            webHook = new DiscordWebHook(new URL( webHookProp.getProperty("webHook_url") ))
                .setUsername(webHookProp.getProperty("webHook_name"))
                .setAvatar(webHookProp.getProperty("webHook_avatar"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "     ___           ___           ___              ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "    /\\__\\         /\\__\\         /\\  \\       ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "   /:/ _/_       /:/ _/_       |::\\  \\          ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "  /:/ /\\  \\     /:/ /\\  \\      |:|:\\  \\     ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + " /:/ /::\\  \\   /:/ /::\\  \\   __|:|\\:\\  \\   ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "/:/_/:/\\:\\__\\ /:/_/:/\\:\\__\\ /::::|_\\:\\__\\" + ChatColor.DARK_GREEN + "  SovaultServerManager " + ChatColor.AQUA + SSMMeta.VERSION.get());
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "\\:\\/:/ /:/  / \\:\\/:/ /:/  / \\:\\~~\\  \\/__/ " + ChatColor.DARK_GRAY + " Created by Naruse Nia. Luckperm no marupakuri!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + " \\::/ /:/  /   \\::/ /:/  /   \\:\\  \\          ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "  \\/_/:/  /     \\/_/:/  /     \\:\\  \\         ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "    /:/  /        /:/  /       \\:\\__\\          ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "    \\/__/         \\/__/         \\/__/          ");
        Bukkit.getConsoleSender().sendMessage("");

        getCommand("stat").setExecutor(new StatCommandExecutor());
        getCommand("menu").setExecutor(new MenuCommandExecutor());
        getCommand("test").setExecutor(new TestCommandExecutor());
        getCommand("team").setExecutor(new TeamCommandExecutor());
        getCommand("timer").setExecutor(new TimerCommandExecutor());


        getServer().getPluginManager().registerEvents(new ClickEventListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static FileConfiguration config(){
        return SovaultServerManager.getInstance().getConfig();
    }

    public BukkitTask getTimerTask() {
        return timerTask;
    }

	public SoundType getTimerSound(){
		return timerSound;
	}

	public void setTimerSound(Sound sound){
		this.timerSound = sound;
	}

    public String getTimerTitle() {
        return timerTitle;
    }

    public String getTimerSubtitle() {
        return timerSubtitle;
    }

    public void setTimerTitle(String timerTitle) {
        this.timerTitle = timerTitle;
    }

    public void setTimerSubtitle(String timerSubtitle) {
        this.timerSubtitle = timerSubtitle;
    }

    public void setTimerTask(BukkitTask timerTask) {
        this.timerTask = timerTask;
    }

    public int getTimerTime() {
        return timerTime;
    }

    public void setTimerTime(int timerTime) {
        this.timerTime = timerTime;
    }

    public static void sendAllPlayer(String message){
        getInstance().getServer().broadcastMessage(message);
    }

    public static void sendToDiscord(String message){
        try {
            webHook.setContent(message).send();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getProperties(PropertyType type) {
        if (type.equals(PropertyType.MAIN)) return properties;
        if (type.equals(PropertyType.STAT)) return stat;
        return null;
    }

    public static SovaultServerManager getInstance() {
        return instance;
    }

}
