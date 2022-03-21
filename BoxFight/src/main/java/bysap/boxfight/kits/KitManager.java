package bysap.boxfight.kits;

import bysap.boxfight.main.Boxfight;
import org.bukkit.entity.Player;

import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;

public class KitManager {

    private final Boxfight plugin;
    private HashMap<String, Kits> playerKits;

    public KitManager(final Boxfight plugin){
        this.plugin = plugin;
        playerKits = new HashMap<>();
    }

    public void removeKit(final Player player){
        playerKits.remove(player.getName());
    }

    public void addPlayerKit(final Player player, final Kits kit){
        playerKits.put(player.getName(), kit);
    }

    public Kits getPlayerKit(final Player player){
        return playerKits.get(player.getName());
    }


}