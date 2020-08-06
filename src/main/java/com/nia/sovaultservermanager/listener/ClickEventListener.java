package com.nia.sovaultservermanager.listener;

import com.nia.sovaultservermanager.SovaultServerManager;
import com.nia.sovaultservermanager.command.MenuCommandExecutor;
import com.nia.sovaultservermanager.item.ItemDebugMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ClickEventListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if (event.getCurrentItem() != null) {
            if (event.getWhoClicked() instanceof Player) {
                Player playerIn = (Player) event.getWhoClicked();
                if (event.getClickedInventory().getTitle().equalsIgnoreCase(MenuCommandExecutor.getInstance().getGuiName())) {
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "ゲーム開始")) {
                        playerIn.sendMessage(ChatColor.GREEN.toString() + ChatColor.ITALIC.toString() + "ゲームを開始します。");
                        playerIn.playSound(playerIn.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                        playerIn.closeInventory();
                        event.setCancelled(true);
                    } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Unimplemented item")) {
                        playerIn.playSound(playerIn.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                        event.setCancelled(true);
                    } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "サバイバル/クリエイティブ切り替え")) {
                        if (playerIn.getGameMode().equals(GameMode.CREATIVE)) {
                            playerIn.setGameMode(GameMode.SURVIVAL);
                            playerIn.sendMessage(GameMode.CREATIVE + "->" + GameMode.SURVIVAL);
                        } else if (playerIn.getGameMode().equals(GameMode.SURVIVAL) || playerIn.getGameMode().equals(GameMode.ADVENTURE)) {
                            playerIn.sendMessage(playerIn.getGameMode() + "->" + GameMode.CREATIVE);
                            playerIn.setGameMode(GameMode.CREATIVE);
                        }
                        playerIn.playSound(playerIn.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                        playerIn.closeInventory();
                        event.setCancelled(true);
                    } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "全プレイヤーアドベンチャー")) {
                        List<Player> playerList = new ArrayList<>(SovaultServerManager.getInstance().getServer().getOnlinePlayers());
                        for (Player player : playerList) {
                            player.setGameMode(GameMode.ADVENTURE);
                        }
                        playerIn.sendMessage(ChatColor.RED.toString() + ChatColor.ITALIC.toString() + "全プレイヤーをアドベンチャーモードにしました。");
                        playerIn.playSound(playerIn.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                        playerIn.closeInventory();
                        event.setCancelled(true);
                    } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "チーム振り分け")) {
                        List<Player> playerList = new ArrayList<>(SovaultServerManager.getInstance().getServer().getOnlinePlayers());
                        Collections.shuffle(playerList);
                        for (int i = 0; i < playerList.size(); i++){
                            if ((i % 2) == 1){
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams join " + "red" + " " + playerList.get(i).getDisplayName());
                            }else{
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams join " + "blue" + " " + playerList.get(i).getDisplayName());
                            }
                        }
                        playerIn.sendMessage(ChatColor.GREEN.toString() + ChatColor.ITALIC.toString() + "チームを振り分けました。");
                        playerIn.playSound(playerIn.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                        playerIn.closeInventory();
                        event.setCancelled(true);
                    } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(" ")) {
                        event.setCancelled(true);
                    } else {
                        event.setCancelled(false);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onItemClick(PlayerInteractEvent event){
        Player playerIn = event.getPlayer();
        Action actionIn = event.getAction();
        ItemMeta playerMainHand = playerIn.getInventory().getItemInMainHand().getItemMeta();
        if (playerMainHand != null) {
            if (actionIn.equals(Action.RIGHT_CLICK_AIR) || actionIn.equals(Action.RIGHT_CLICK_BLOCK)) {
                if (playerMainHand.getDisplayName().equals(ItemDebugMenu.getInstance().itemName)) {
                    playerIn.openInventory(MenuCommandExecutor.getInstance().getInventory(playerIn));
                }
            }
        }
    }

}
