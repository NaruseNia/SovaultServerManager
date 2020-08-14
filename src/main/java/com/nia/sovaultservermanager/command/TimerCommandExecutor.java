package com.nia.sovaultservermanager.command;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.gmail.nossr50.chat.ChatManagerFactory;
import com.gmail.nossr50.commands.chat.ChatCommand;
import com.nia.sovaultservermanager.SovaultServerManager;
import com.nia.sovaultservermanager.gui.sign.SignGUI;
import com.nia.sovaultservermanager.permission.SSMPermissions;
import com.nia.sovaultservermanager.util.ChannelType;
import com.nia.sovaultservermanager.util.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_12_R1.ExceptionEntityNotFound;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.rmi.CORBA.Util;
import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;

import static com.nia.sovaultservermanager.permission.SSMPermissions.menuPermission;

public class TimerCommandExecutor implements CommandExecutor {

    private List<Player> players;
    private final ProtocolManager protocolLib = ProtocolLibrary.getProtocolManager();
    private final SovaultServerManager plugin = SovaultServerManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player || sender.hasPermission(SSMPermissions.timerPermission)) {
            players = new ArrayList<>(plugin.getServer().getOnlinePlayers());
            if (args.length == 3) {
                switch (args[0]) {
                    case "start":
                        try {
                            plugin.getTimerTask().cancel();
                        }catch (Exception e){
                            //
                        }
                        try {
                            plugin.setTimerTime(Integer.parseInt(args[1]));
						} catch (Exception e) {
                            Utils.sendMessageChannel(sender, ChannelType.MAIN_ERROR, ChatColor.RESET + "第一引数には数値を入力してください。");
                            return true;
                        }

						if (SovaultServerManager.config().contains("titles." + args[2] + "sound"))plugin.setTimerSound(SovaultServerManager.config().get("titles." + args[2] + "sound").toString());
                        if (SovaultServerManager.config().contains("titles." + args[2] + "title.json"))plugin.setTimerTitle(SovaultServerManager.config().get("titles." + args[2] + ".title.json").toString());
                        if (SovaultServerManager.config().contains("titles." + args[2] + "subtitle.json"))plugin.setTimerSubtitle(SovaultServerManager.config().get("titles." + args[2] + ".subtitle.json").toString());

                        Utils.sendMessageChannel(sender, ChannelType.MAIN_SYSTEM, Utils.getMessageConfig().getProperty("command.timer.start"));
                        for (Player player : players) {
                            player.sendTitle(Utils.getMessageConfig().getProperty("command.timer.startTitle"), "", 10, 70, 20);
                        }
                        plugin.setTimerTask(new Timer(SovaultServerManager.getInstance(), SovaultServerManager.getInstance().getTimerTime()).runTaskTimer(SovaultServerManager.getInstance(), 0L, 20L));
                        return true;
                    case "set":
						if (args[1].equals("sound")) {
							new AnvilGUI(plugin, (Player) sender, "Put json here.", (player, reply) -> {
								sender.sendMessage(reply);
								SovaultServerManager.config().set("titles." + args[2] + "sound", reply);
								plugin.saveConfig();
								return null;
							})
						}
                        if (args[1].equals("title")) {
                            new AnvilGUI(plugin, (Player) sender, "Put json here.", (player, reply) -> {
                                sender.sendMessage(reply);
                                SovaultServerManager.config().set("titles." + args[2] + ".title.json", reply);
                                plugin.saveConfig();
                                return null;
                            });
                            return true;
                        } else if (args[1].equals("subtitle")) {
                            new AnvilGUI(plugin, (Player) sender, "Put json here.", (player, reply) -> {
                                sender.sendMessage(reply);
                                SovaultServerManager.config().set("titles." + args[2] + ".subtitle.json", reply);
                                plugin.saveConfig();
                                return null;
                            });
                            return true;
                        }
                        break;
                    case "remove":
                        if (SovaultServerManager.config().contains("titles." + args[1])) {
                            SovaultServerManager.config().set("titles." + args[1], null);
                            plugin.saveConfig();
                            return true;
                        }
                        return true;
                }
            } else if (args.length == 1) {
                if (args[0].equals("stop")) {
                    Utils.sendMessageChannel(sender, ChannelType.MAIN_SYSTEM, Utils.getMessageConfig().getProperty("command.timer.stop"));
                    plugin.getTimerTask().cancel();
                    return true;
                }else if (args[0].equals("list")){
                    Utils.sendMessageChannel(sender, ChannelType.MAIN_SYSTEM, ChatColor.GREEN + "タイトルセット一覧");
                    for (String key : SovaultServerManager.config().getConfigurationSection("titles").getKeys(false)) {
                        Utils.sendMessageChannel(sender, ChannelType.MAIN_SYSTEM, ChatColor.GREEN + key + ":");
                        
						if (SovaultServerManager.config().get("titles." + key + ".title.json") != null) Utils.sendMessageChannel(sender, ChannelType.MAIN_SYSTEM, ChatColor.GOLD + "   title:" + SovaultServerManager.config().get("titles." + key + ".title.json").toString());
                        
						if (SovaultServerManager.config().get("titles." + key + ".subtitle.json") != null) Utils.sendMessageChannel(sender, ChannelType.MAIN_SYSTEM, ChatColor.GOLD + "   subtitle:" + SovaultServerManager.config().get("titles." + key + ".subtitle.json").toString());
                    }
                    return true;
                } else if (args[0].equals("?") || args[0].equals("help")) {
                    Utils.sendMessageLine(4, "command.timer.help", ChannelType.NONE, sender);
                    return true;
                }
            }
            Utils.sendMessageLine(2, "command.timer.error", ChannelType.MAIN_ERROR, sender);
            return true;
        }
        Utils.sendMessageChannel(sender, ChannelType.MAIN_ERROR, String.format(Utils.getMessageConfig().getProperty("command.error.permission"), SSMPermissions.menuPermission.getName()));
        return true;
    }

    private class Timer extends BukkitRunnable {

        int time;
        JavaPlugin plugin;
        List<Player> players;

        public Timer(JavaPlugin plugin, int time){
            players = new ArrayList<>(plugin.getServer().getOnlinePlayers());
            this.plugin = plugin;
            this.time = time;
        }

        @Override
        public void run() {
            if(time <= 0){
                for (Player player : players){
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(String.valueOf(Utils.ConvertSecToTime(time))));
					Utils.sendTitlePacket(player, SovaultServerManager.getInstance().getTimerTitle, SovaultServerManager.getInstance().getTimerSubtitle());
					player.playSound(player.getLocation(), SovaultServerManager.getTimerSound(), 1.0f, 1.0f);
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
