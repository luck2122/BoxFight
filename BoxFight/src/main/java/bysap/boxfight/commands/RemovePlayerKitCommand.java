package bysap.boxfight.commands;

import bysap.boxfight.kits.KitManager;
import bysap.boxfight.kits.Kits;
import bysap.boxfight.main.Boxfight;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RemovePlayerKitCommand implements CommandExecutor {

    private final Boxfight plugin;
    private final KitManager kitManager;

    public RemovePlayerKitCommand(final Boxfight plugin){
        this.plugin = plugin;
        kitManager = plugin.getKitManager();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(args.length == 0){
            player.sendMessage(ChatColor.RED + "Bitte benutze " + ChatColor.GOLD + "/removekit <PLAYER>");
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target != null){
            Kits targetKit = kitManager.getPlayerKit(target);
            if(targetKit != null){
                kitManager.removeKit(target);
                target.getInventory().clear();
                plugin.getPlayerList().remove(target);
                player.sendMessage(ChatColor.GREEN + "Das Kit von " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + " wurde entfernt");
            }else{
                player.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " hat kein Kit ausgewählt");
                return false;
            }
        }else{
            player.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GOLD + args[0] + ChatColor.RED + " konnte nicht gefunden werden");
            return false;
        }
        return false;
    }
}