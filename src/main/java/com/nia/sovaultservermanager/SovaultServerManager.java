package com.nia.sovaultservermanager;

import com.nia.sovaultservermanager.command.MenuCommandExecutor;
import com.nia.sovaultservermanager.command.StatCommandExecutor;
import com.nia.sovaultservermanager.command.TeamCommandExecutor;
import com.nia.sovaultservermanager.discord.webhook.DiscordWebHook;
import com.nia.sovaultservermanager.listener.ClickEventListener;
import com.nia.sovaultservermanager.test.TestCommandExecutor;
import com.nia.sovaultservermanager.util.PropertyLoader;
import com.nia.sovaultservermanager.util.PropertyType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public final class SovaultServerManager extends JavaPlugin {

    private static SovaultServerManager instance;

    private static Properties properties;
    private static Properties stat;
    private static Properties webHookProp;

    private static DiscordWebHook webHook;

    @Override
    public void onEnable() {
        instance = this;

        try {
            properties = new PropertyLoader("messages/message.properties").getProperties();
            stat = new PropertyLoader("messages/stat.properties").getProperties();
            webHookProp = new PropertyLoader("webHook.properties").getProperties();
        } catch (IOException e) {
            getLogger().warning("プロパティファイルを読み込めませんでした。");
            e.printStackTrace();
        }

        try {
            webHook = new DiscordWebHook(webHookProp.getProperty("webHook_url"))
                                        .setUsername(webHookProp.getProperty("webHook_name"))
                                        .setAvatar(webHookProp.getProperty("webHook_avatar"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        sendToDiscord("こんにちは！");

        getCommand("stat").setExecutor(new StatCommandExecutor());
        getCommand("menu").setExecutor(new MenuCommandExecutor());
        getCommand("test").setExecutor(new TestCommandExecutor());
        getCommand("team").setExecutor(new TeamCommandExecutor());

        getServer().getPluginManager().registerEvents(new ClickEventListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static void sendAllPlayer(String message){
        List<Player> players = new ArrayList<>(SovaultServerManager.getInstance().getServer().getOnlinePlayers());
        for (Player player : players){
            player.sendMessage(message);
        }
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
