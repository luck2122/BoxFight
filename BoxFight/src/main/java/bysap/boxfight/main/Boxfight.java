package bysap.boxfight.main;

import bysap.boxfight.commands.ClearPlayerList;
import bysap.boxfight.commands.KitCommand;
import bysap.boxfight.commands.RemovePlayerKitCommand;
import bysap.boxfight.commands.TimerCommand;
import bysap.boxfight.kits.KitManager;
import bysap.boxfight.listener.GameProtectionListener;
import bysap.boxfight.listener.PlayerConnectionListener;
import bysap.boxfight.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Boxfight extends JavaPlugin {

    private final KitManager kitManager = new KitManager(this);
    private final Timer timer = new Timer(this);
    private final PluginManager pluginManager = Bukkit.getPluginManager();
    private boolean isFirst = true;
    private List<Player> playerList;

    //TODO Timer noch als getter machen und dann abfragen mit Hitten und so machen
    @Override
    public void onEnable() {
        playerList = new ArrayList<>();
        getCommand("kit").setExecutor(new KitCommand(this));
        getCommand("timer").setExecutor(new TimerCommand(this, this.timer));
        getCommand("removekit").setExecutor(new RemovePlayerKitCommand(this));
        getCommand("clearplayers").setExecutor(new ClearPlayerList(this));
        pluginManager.registerEvents(new GameProtectionListener(this), this);
        pluginManager.registerEvents(new PlayerConnectionListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public List<Player> getPlayerList(){
        return this.playerList;
    }

    public void setPlayerList(List<Player> playerList){
        this.playerList = playerList;
    }

    public Timer getTimer(){
        return this.timer;
    }

    public boolean getIsFirst(){
        return this.isFirst;
    }

    public void setIsFirst(final boolean isFirst){
        this.isFirst = isFirst;
    }

    public KitManager  getKitManager(){
        return kitManager;
    }
}
