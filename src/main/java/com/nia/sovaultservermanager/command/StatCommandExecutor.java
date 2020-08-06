package com.nia.sovaultservermanager.command;

import com.nia.sovaultservermanager.SovaultServerManager;
import com.nia.sovaultservermanager.util.PropertyType;
import com.nia.sovaultservermanager.util.Utils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import sun.plugin.dom.core.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class StatCommandExecutor implements CommandExecutor {

    private Properties message = SovaultServerManager.getProperties(PropertyType.MAIN);
    private Properties statMessage = SovaultServerManager.getProperties(PropertyType.STAT);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        /*
        Properties message = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            message.load(new FileInputStream(classLoader.getResource("messages/message.properties").getPath().replaceFirst("!", "")));
        } catch (IOException e) {
            SovaultServerManager.getInstance().getLogger().warning(e.getMessage());
        }
        */

        if (sender instanceof Player){
            if (args.length == 3) {
                if (args[0].equals("--uuid") && args[1].equals("-offline")) {
                    Player playerTarget = (Player) Bukkit.getOfflinePlayer(UUID.fromString(args[2]));
                    int statPlayOneTick = playerTarget.getStatistic(Statistic.PLAY_ONE_TICK);
                    sender.sendMessage(message.getProperty("channel.main.system") + "§7" + playerTarget.getDisplayName() + "のプレイ時間:" + ChatColor.GREEN + Utils.ConvertTickToTime(statPlayOneTick));
                    return true;
                }
            }else if (args.length == 1){
                Player playerTarget = Bukkit.getPlayer(args[0]);
                int statPlayOneTick = playerTarget.getStatistic(Statistic.PLAY_ONE_TICK);
                sender.sendMessage(message.getProperty("channel.main.system") + "§7" + playerTarget.getDisplayName() + "のプレイ時間:" + ChatColor.GREEN + Utils.ConvertTickToTime(statPlayOneTick));
                return true;
            }else if (args.length == 2){
                if (args[1].equals("-all")) {
                    Player playerTarget = Bukkit.getPlayer(args[0]);
                    sender.sendMessage( ChatColor.AQUA + ChatColor.BOLD.toString() + playerTarget.getDisplayName() + ChatColor.RESET + "§7の統計です。" );
                    showAllStats(sender, playerTarget);
                    return true;
                }else if ( args[0].equals("--uuid") ){
                    Player playerTarget = Bukkit.getPlayer(UUID.fromString(args[1]));
                    sender.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + playerTarget.getDisplayName() + ChatColor.RESET + "§7の統計です。");
                    showAllStats(sender, playerTarget);
                    return true;
                }
            }
            return false;
        }

        return false;
    }

    public void showAllStats(CommandSender sender, Player playerTarget){
        for (Statistic value : getAllPlayerStat(playerTarget)) {
            if (value.toString().equals("PLAY_ONE_TICK") || value.toString().equals("SNEAK_TIME") || value.toString().equals("TIME_SINCE_DEATH")) {
                sender.sendMessage(statMessage.getProperty("stat." + value.toString()) + ChatColor.GREEN + Utils.ConvertTickToTime(playerTarget.getStatistic(value)) + "(" + playerTarget.getStatistic(value) + "tick)");
            } else if (value.toString().contains("CM")) {
                sender.sendMessage(statMessage.getProperty("stat." + value.toString()) + ChatColor.GREEN + playerTarget.getStatistic(value) + "cm");
            } else {
                sender.sendMessage(statMessage.getProperty("stat." + value.toString()) + ChatColor.GREEN + playerTarget.getStatistic(value));
            }
        }
    }

    public List<Statistic> getAllPlayerStat(Player playerIn){
        List<Statistic> stats = new ArrayList<>();

        stats.add(Statistic.DAMAGE_DEALT);
        stats.add(Statistic.DAMAGE_TAKEN);
        stats.add(Statistic.DEATHS);
        stats.add(Statistic.MOB_KILLS);
        stats.add(Statistic.PLAYER_KILLS);
        stats.add(Statistic.FISH_CAUGHT);
        stats.add(Statistic.ANIMALS_BRED);
        stats.add(Statistic.LEAVE_GAME);
        stats.add(Statistic.JUMP);
        stats.add(Statistic.PLAY_ONE_TICK);
        stats.add(Statistic.WALK_ONE_CM);
        stats.add(Statistic.SWIM_ONE_CM);
        stats.add(Statistic.FALL_ONE_CM);
        stats.add(Statistic.SNEAK_TIME);
        stats.add(Statistic.CLIMB_ONE_CM);
        stats.add(Statistic.FLY_ONE_CM);
        stats.add(Statistic.DIVE_ONE_CM);
        stats.add(Statistic.MINECART_ONE_CM);
        stats.add(Statistic.BOAT_ONE_CM);
        stats.add(Statistic.PIG_ONE_CM);
        stats.add(Statistic.HORSE_ONE_CM);
        stats.add(Statistic.SPRINT_ONE_CM);
        stats.add(Statistic.CROUCH_ONE_CM);
        stats.add(Statistic.AVIATE_ONE_CM);
        stats.add(Statistic.TIME_SINCE_DEATH);
        stats.add(Statistic.TALKED_TO_VILLAGER);
        stats.add(Statistic.TRADED_WITH_VILLAGER);
        stats.add(Statistic.CAKE_SLICES_EATEN);
        stats.add(Statistic.CAULDRON_FILLED);
        stats.add(Statistic.CAULDRON_USED);
        stats.add(Statistic.ARMOR_CLEANED);
        stats.add(Statistic.BANNER_CLEANED);
        stats.add(Statistic.BREWINGSTAND_INTERACTION);
        stats.add(Statistic.BEACON_INTERACTION);
        stats.add(Statistic.DROPPER_INSPECTED);
        stats.add(Statistic.DISPENSER_INSPECTED);
        stats.add(Statistic.NOTEBLOCK_PLAYED);
        stats.add(Statistic.NOTEBLOCK_TUNED);
        stats.add(Statistic.FLOWER_POTTED);
        stats.add(Statistic.TRAPPED_CHEST_TRIGGERED);
        stats.add(Statistic.ENDERCHEST_OPENED);
        stats.add(Statistic.ITEM_ENCHANTED);
        stats.add(Statistic.RECORD_PLAYED);
        stats.add(Statistic.FURNACE_INTERACTION);
        stats.add(Statistic.CRAFTING_TABLE_INTERACTION);
        stats.add(Statistic.CHEST_OPENED);
        stats.add(Statistic.SLEEP_IN_BED);
        stats.add(Statistic.SHULKER_BOX_OPENED);

        return stats;
    }

}
