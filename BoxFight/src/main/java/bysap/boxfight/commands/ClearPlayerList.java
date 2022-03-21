package bysap.boxfight.commands;

import bysap.boxfight.main.Boxfight;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClearPlayerList implements CommandExecutor {

    private final Boxfight plugin;
    private List<Player> playerList;

    public ClearPlayerList(Boxfight plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        playerList = plugin.getPlayerList();
        if(playerList.isEmpty()) {
            player.sendMessage("Die Spielerliste ist leer");
            return false;
        }
        playerList.clear();
        player.sendMessage("Die Spielerliste wurde geleert");
        return false;
    }
}