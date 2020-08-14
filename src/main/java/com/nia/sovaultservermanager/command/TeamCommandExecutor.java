package com.nia.sovaultservermanager.command;

import com.nia.sovaultservermanager.SovaultServerManager;
import com.nia.sovaultservermanager.permission.SSMPermissions;
import com.nia.sovaultservermanager.util.ChannelType;
import com.nia.sovaultservermanager.util.PropertyType;
import com.nia.sovaultservermanager.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.springframework.lang.NonNull;

import java.util.*;

public class TeamCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player || sender.hasPermission(SSMPermissions.teamPermission)) {
            List<Player> players = new ArrayList<>(SovaultServerManager.getInstance().getServer().getOnlinePlayers());
            if (args.length == 1) {
                if (("leaveall".equals(args[0]) || "la".equals(args[0]))) {
                    leaveAllPlayer(players);
                    SovaultServerManager.sendAllPlayer(Utils.getMessageConfig().getProperty("channel.main.system") + message.getProperty("command.team.leave"));
                    return true;
                } else if (("help".equals(args[0]) || "?".equals(args[0]))) {
                    Utils.sendMessageLine(4, "command.team.help", ChannelType.NONE, sender);
                    return true;
                }
            } else if (args.length == 3) {
                if ("shuffle".equals(args[0]) || "sh".equals(args[0])) {
                    leaveAllPlayer(players);
                    joinAllPlayerRandom(args[1], args[2], players);
                    SovaultServerManager.sendAllPlayer(Utils.getMessageConfig().getProperty("channel.main.system") + Utils.getMessageConfig().getProperty("command.team.split"));
                    SovaultServerManager.sendAllPlayer(Utils.getMessageConfig().getProperty("channel.main.system") + Utils.getMessageConfig().getProperty("command.team.info"));
                    return true;
                }
            }
            Utils.sendUtils.getMessageConfig()Line(2, "command.team.error", ChannelType.MAIN_ERROR, sender);
            return true;
        }
        Utils.sendMessageChannel(sender, ChannelType.MAIN_ERROR, String.format(Utils.getMessageConfig().getProperty("command.error.permission"), SSMPermissions.teamPermission.getName()));
        return true;
    }

    private void joinAllPlayerRandom(String team1, String team2, List<Player> playersIn){
        Collections.shuffle(playersIn);
        for (int i = 0; i < playersIn.size(); i++){
            if ((i % 2) == 1){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams join " + team1 + " " + playersIn.get(i).getDisplayName());
            }else{
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams join " + team2 + " " + playersIn.get(i).getDisplayName());
            }
        }
    }

    private void leaveAllPlayer(List<Player> playersIn){
        for (Player player : playersIn){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams leave " + player.getDisplayName());
        }
    }

    private void leave(List<Team> teamsIn, Player playerIn){
        for (Team team : teamsIn){
            team.removeEntry(playerIn.getUniqueId().toString());
        }
    }
}
