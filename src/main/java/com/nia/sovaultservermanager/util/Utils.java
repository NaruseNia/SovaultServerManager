package com.nia.sovaultservermanager.util;

import com.nia.sovaultservermanager.SovaultServerManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.springframework.lang.NonNull;

import javax.swing.text.html.parser.Entity;
import java.sql.Time;
import java.util.Objects;
import java.util.Properties;

public class Utils {

    @NonNull
    private static final Properties message = Objects.requireNonNull(SovaultServerManager.getProperties(PropertyType.MAIN));

    @NonNull
    public static Properties getMessageConfig() {
        return message;
    }

    public static void sendMessageLine(int line, String property, ChannelType channel, CommandSender target){
        String channelText;
        if (channel.equals(ChannelType.MAIN_SYSTEM)) channelText = message.getProperty("channel.main.system");
        else if (channel.equals(ChannelType.MAIN_ERROR)) channelText = message.getProperty("channel.main.error");
        else if (channel.equals(ChannelType.MAIN_IMPORTANT)) channelText = message.getProperty("channel.main.important");
        else if (channel.equals(ChannelType.MAIN_WARN)) channelText = message.getProperty("channel.main.warn");
        else channelText = "";
        for (int i = 1; i <= line; i++) {
            target.sendMessage(channelText + message.getProperty(property + i));
        }
    }

    public static void sendMessageChannel(CommandSender target, ChannelType channel, String messageIn){
        String channelText;
        if (channel.equals(ChannelType.MAIN_SYSTEM)) channelText = message.getProperty("channel.main.system");
        else if (channel.equals(ChannelType.MAIN_ERROR)) channelText = message.getProperty("channel.main.error");
        else if (channel.equals(ChannelType.MAIN_IMPORTANT)) channelText = message.getProperty("channel.main.important");
        else if (channel.equals(ChannelType.MAIN_WARN)) channelText = message.getProperty("channel.main.warn");
        else channelText = "";
        target.sendMessage(channelText + messageIn);
    }

    public static Time ConvertTickToTime(int tickIn){
        int hour, min, sec;
        int secIn = tickIn / 20;

        hour = secIn / 3600;
        min  = (secIn % 3600) / 60;
        sec  = secIn % 60;

        return Time.valueOf( hour + ":" + min + ":" + sec );
    }

    public static Time ConvertSecToTime(int secIn){
        int hour, min, sec;

        hour = secIn / 3600;
        min  = (secIn % 3600) / 60;
        sec  = secIn % 60;

        return Time.valueOf( hour + ":" + min + ":" + sec );
    }
}
