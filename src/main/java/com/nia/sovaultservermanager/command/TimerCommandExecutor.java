package com.nia.sovaultservermanager.command;

import com.nia.sovaultservermanager.SovaultServerManager;
import com.nia.sovaultservermanager.util.ChannelType;
import com.nia.sovaultservermanager.util.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.List;

public class TimerCommandExecutor implements CommandExecutor {

    private List<Player> players;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        players = new ArrayList<>(SovaultServerManager.getInstance().getServer().getOnlinePlayers());
        if (args.length == 2) {
            if (args[0].equals("start")) {
                try {
                    SovaultServerManager.getInstance().setTimerTime(Integer.parseInt(args[1]));
                } catch (Exception e) {
                    Utils.sendMessageChannel(sender, ChannelType.MAIN_ERROR, "第一引数には数値を入力してください。");
                    return true;
                }
                Utils.sendMessageChannel(sender, ChannelType.MAIN_SYSTEM, Utils.getMessageConfig().getProperty("command.timer.start"));
                for (Player player : players){
                    player.sendTitle(Utils.getMessageConfig().getProperty("command.timer.startTitle"), "", 10, 70, 20);
                }
                SovaultServerManager.getInstance().setTimerTask( new Timer(SovaultServerManager.getInstance(), SovaultServerManager.getInstance().getTimerTime()).runTaskTimer(SovaultServerManager.getInstance(), 0L, 20L) );
                return true;
            }
        }else if (args.length == 1){
            if (args[0].equals("stop")){
                Utils.sendMessageChannel(sender, ChannelType.MAIN_SYSTEM, Utils.getMessageConfig().getProperty("command.timer.stop"));
                SovaultServerManager.getInstance().getTimerTask().cancel();
                return true;
            }else if (args[0].equals("?") || args[0].equals("help")){
                Utils.sendMessageLine(3, "command.timer.help", ChannelType.NONE, sender);
                return true;
            }
        }
        Utils.sendMessageLine(2, "command.timer.error", ChannelType.MAIN_ERROR, sender);
        return true;
    }

    private class Timer extends BukkitRunnable {

        int time;
        JavaPlugin plugin;
        List<Player> players;

        public Timer(JavaPlugin plugin, int time){
            players = new ArrayList<>(SovaultServerManager.getInstance().getServer().getOnlinePlayers());
            this.plugin = plugin;
            this.time = time;
        }

        @Override
        public void run() {
            if(time <= 0){
                for (Player player : players){
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(String.valueOf(Utils.ConvertSecToTime(time))));
                    player.sendTitle(Utils.getMessageConfig().getProperty("command.timer.stopTitle"), "", 10, 70, 20);
                }
                plugin.getServer().getScheduler().cancelTask(SovaultServerManager.getInstance().getTimerTask().getTaskId());
            }else{
                if (time == 3) {
                    for (Player player : players) {
                        player.sendTitle(ChatColor.GREEN.toString() + ChatColor.BOLD + "3", "", 0, 70, 0);
                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                    }
                }else if (time == 2){
                    for (Player player : players) {
                        player.sendTitle(ChatColor.YELLOW.toString() + ChatColor.BOLD + "2", "", 0, 70, 0);
                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                    }
                }else if (time == 1){
                    for (Player player : players) {
                        player.sendTitle(ChatColor.RED.toString() + ChatColor.BOLD + "1", "", 0, 70, 0);
                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                    }
                }
                for (Player player : players) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(String.valueOf(Utils.ConvertSecToTime(time))));
                }
            }
            time--;
        }
    }
}
