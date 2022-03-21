package bysap.boxfight.commands;

import bysap.boxfight.inventories.GiveInventories;
import bysap.boxfight.kits.Kits;
import bysap.boxfight.main.Boxfight;
import bysap.boxfight.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class TimerCommand implements CommandExecutor {

    private final Boxfight plugin;
    private final Timer timer;

    public TimerCommand(final Boxfight plugin, final Timer timer){
        this.plugin = plugin;
        this.timer = timer;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(args.length == 0){
            player.sendMessage(ChatColor.RED + "Bitte benutze" + ChatColor.GOLD + "/timer <START | STOP | RESET >");
            return false;
        }
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "start" -> {
                timer.startRunning();
                Bukkit.broadcastMessage("Der Timer wurde gestartet");
            }
            case "stop" -> {
                timer.setRunning(false);
                Bukkit.broadcastMessage("Der Timer wurde pausiert");
            }
            case "reset" -> {
                timer.resetTimer();
                Bukkit.broadcastMessage("Der Timer wurde resettet");
            }
            default -> player.sendMessage(ChatColor.RED + "Bitte benutze" + ChatColor.GOLD + "/timer <START | STOP | RESET >");
        }

        return false;
    }
}