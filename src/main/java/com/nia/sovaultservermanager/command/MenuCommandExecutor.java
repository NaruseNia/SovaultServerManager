package com.nia.sovaultservermanager.command;

import com.nia.sovaultservermanager.item.ItemDebugMenu;
import com.nia.sovaultservermanager.permission.SSMPermissions;
import com.nia.sovaultservermanager.util.ChannelType;
import com.nia.sovaultservermanager.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuCommandExecutor implements CommandExecutor {

    public static MenuCommandExecutor instance = new MenuCommandExecutor();

    private final String guiName = ChatColor.DARK_GREEN + "ゲームメニュー";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player || sender.hasPermission(SSMPermissions.menuPermission)){
            if (args.length == 0) {
                Player playerIn = (Player) sender;
                playerIn.openInventory(getInventory(playerIn));
                return true;
            }
            if (args.length == 1 && args[0].equals("give")){
                ItemDebugMenu.getInstance().getItem((Player)sender);
                return true;
            }
        }
        Utils.sendMessageChannel(sender, ChannelType.MAIN_ERROR, String.format(Utils.getMessageConfig().getProperty("command.error.permission"), SSMPermissions.menuPermission.getName()));
        return true;
    }

    public Inventory getInventory(Player playerIn){
        Inventory gui = Bukkit.createInventory(playerIn, 9, guiName);

        ItemStack start = new ItemStack(Material.SPECKLED_MELON);
        ItemStack toggleSC = new ItemStack(Material.EMERALD);
        ItemStack allAdventure = new ItemStack(Material.REDSTONE_TORCH_ON);
        ItemStack splitTeam = new ItemStack(Material.HOPPER);
        ItemStack unimplementedItem = new ItemStack(Material.REDSTONE);
        ItemStack spacer = new ItemStack(Material.STAINED_GLASS_PANE);

        ItemMeta startMeta = start.getItemMeta();
        startMeta.setDisplayName(ChatColor.GOLD + "ゲーム開始");
        start.setItemMeta(startMeta);

        ItemMeta toggleSCMeta = toggleSC.getItemMeta();
        toggleSCMeta.setDisplayName(ChatColor.GREEN + "サバイバル/クリエイティブ切り替え");
        toggleSC.setItemMeta(toggleSCMeta);

        ItemMeta allAdventureMeta = allAdventure.getItemMeta();
        allAdventureMeta.setDisplayName(ChatColor.RED + "全プレイヤーアドベンチャー");
        allAdventure.setItemMeta(allAdventureMeta);

        ItemMeta splitTeamMeta = splitTeam.getItemMeta();
        splitTeamMeta.setDisplayName(ChatColor.GREEN + "チーム振り分け");
        splitTeam.setItemMeta(splitTeamMeta);

        ItemMeta spacerMeta = spacer.getItemMeta();
        spacerMeta.setDisplayName(" ");
        spacer.setItemMeta(spacerMeta);

        ItemMeta n = unimplementedItem.getItemMeta();
        n.setDisplayName("Unimplemented item");
        unimplementedItem.setItemMeta(n);

        ItemStack[] menuItem = new ItemStack[9];
        menuItem[0] = toggleSC;
        menuItem[1] = spacer;
        menuItem[2] = splitTeam;
        menuItem[3] = spacer;
        menuItem[4] = start;
        menuItem[5] = spacer;
        menuItem[6] = unimplementedItem;
        menuItem[7] = spacer;
        menuItem[8] = allAdventure;
        gui.setContents(menuItem);

        return gui;
    }

    public String getGuiName() {
        return guiName;
    }

    public static MenuCommandExecutor getInstance() {
        return instance;
    }

}
