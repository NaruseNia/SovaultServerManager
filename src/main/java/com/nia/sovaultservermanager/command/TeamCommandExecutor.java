package com.nia.sovaultservermanager.command;

import com.nia.sovaultservermanager.SovaultServerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TeamCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        List<Player> players = new ArrayList<>(SovaultServerManager.getInstance().getServer().getOnlinePlayers());
        if (args.length == 3) {
            if ("shuffle".equals(args[0]) || "sh".equals(args[0])) {
                //leaveAllPlayer(players);
                joinAllPlayerRandom(args[1], args[2], players);
                SovaultServerManager.sendAllPlayer(ChatColor.GREEN.toString() + ChatColor.ITALIC.toString() + "チーム分けしました。");
                SovaultServerManager.sendAllPlayer(ChatColor.RED.toString() + ChatColor.ITALIC.toString() + "!Warning!全員がチームから正常に抜けていない可能性があります。/team leaveall　を試してみてください。");
                return true;
            }
        }else if (args.length == 1) {
            if (("leaveall".equals(args[0]) || "la".equals(args[0]))) {
                leaveAllPlayer(players);
                SovaultServerManager.sendAllPlayer(ChatColor.GREEN.toString() + ChatColor.ITALIC.toString() + "全員をチームから抜けさせました。");
                return true;
            }
        }
        return false;
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
