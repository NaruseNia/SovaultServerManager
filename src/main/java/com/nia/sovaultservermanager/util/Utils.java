package com.nia.sovaultservermanager.util;

import com.nia.sovaultservermanager.SovaultServerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.springframework.lang.NonNull;

import java.lang.reflect.Constructor;
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

    @Deprecated
    public static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public static void sendTitlePacket(Player player, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {
        try {
            Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + title + "\"}");
            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            Object packet = titleConstructor.newInstance(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle,
                    fadeInTime, showTime, fadeOutTime);

            Object chatsTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + subtitle + "\"}");
            Constructor<?> timingTitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);
            Object timingPacket = timingTitleConstructor.newInstance(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatsTitle,
                    fadeInTime, showTime, fadeOutTime);

            sendPacket(player, packet);
            sendPacket(player, timingPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server."
                    + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
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
