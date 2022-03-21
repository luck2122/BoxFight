package bysap.boxfight.commands;

import bysap.boxfight.inventories.GiveInventories;
import bysap.boxfight.kits.KitManager;
import bysap.boxfight.kits.Kits;
import bysap.boxfight.main.Boxfight;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class KitCommand implements CommandExecutor{

    private final Boxfight plugin;
    private final KitManager kitManager;

    public KitCommand(final Boxfight plugin){
        this.plugin = plugin;
        kitManager = plugin.getKitManager();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        Kits playerKit = kitManager.getPlayerKit(player);
        if(playerKit != null) {
            player.sendMessage(ChatColor.RED + "Du hast bereits ein Kit");
            return false;
        }
       GiveInventories giveInventories = new GiveInventories(player);
        player.openInventory(giveInventories.openKitInv());
        return false;
    }


}