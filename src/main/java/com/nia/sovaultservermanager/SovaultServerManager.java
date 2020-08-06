package com.nia.sovaultservermanager;

import com.nia.sovaultservermanager.command.MenuCommandExecutor;
import com.nia.sovaultservermanager.command.StatCommandExecutor;
import com.nia.sovaultservermanager.command.TeamCommandExecutor;
import com.nia.sovaultservermanager.discord.webhook.DiscordWebHook;
import com.nia.sovaultservermanager.listener.ClickEventListener;
import com.nia.sovaultservermanager.test.TestCommandExecutor;
import com.nia.sovaultservermanager.util.PropertyLoader;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public final class SovaultServerManager extends JavaPlugin {

    private static SovaultServerManager instance;

    private static Properties properties;
    private static Properties stat;

    private static DiscordWebHook webHook;

    @Override
    public void onEnable() {
        instance = this;

        try {
            properties = new PropertyLoader("messages/message.properties").getProperties();
        } catch (IOException e) {
            getLogger().warning("プロパティファイルを読み込めませんでした。");
            e.printStackTrace();
        }

        try {
            webHook = new DiscordWebHook("https://discordapp.com/api/webhooks/738518396563488808/LrEE5tLsAhAqvgAJz4uu1nYml5xFGUSHQqBOGc6kfYhno1855ZfZt7ZnskeTbS6iA1p9")
                                            .setUsername("SSM");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            stat = new PropertyLoader("messages/stat.properties").getProperties();
        } catch (IOException e) {
            getLogger().warning("プロパティファイルを読み込めませんでした。");
            e.printStackTrace();
        }

        //sendToDiscord("こんにちは！");

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

    public static Properties getStatLocalize() {
        return stat;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static SovaultServerManager getInstance() {
        return instance;
    }

}
