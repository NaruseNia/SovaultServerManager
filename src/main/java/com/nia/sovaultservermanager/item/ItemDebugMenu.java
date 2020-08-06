package com.nia.sovaultservermanager.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemDebugMenu {

    public static ItemDebugMenu instance = new ItemDebugMenu();

    public String itemName = ChatColor.GOLD + "Debug Menu";

    public void getItem( Player playerIn ){
        ItemStack menu = new ItemStack(Material.SPECKLED_MELON);
        ItemMeta menuMeta = menu.getItemMeta();

        menuMeta.setDisplayName(itemName);

        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.AQUA + "Click to open debug menu.");

        menuMeta.setLore(lore);

        menu.setItemMeta(menuMeta);

        playerIn.getInventory().addItem(menu);
    }

    public static ItemDebugMenu getInstance() {
        return instance;
    }

}
