package bysap.boxfight.timer;

import bysap.boxfight.inventories.GiveInventories;
import bysap.boxfight.kits.Kits;
import bysap.boxfight.main.Boxfight;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer {

    private final Boxfight plugin;
    private boolean running;
    private int seconds;
    private boolean giveInvs = true;

    public Timer(final Boxfight plugin){
        this.plugin = plugin;
        this.running = false;
        this.seconds = 20;
    }

    public void startRunning(){
        setRunning(true);
        run();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(final boolean running) {
        this.running = running;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(final int time) {
        this.seconds = time;
    }

    //TODO nach dem reset nochmal das inv befüllen
    public void resetTimer(){
        setSeconds(20);
        giveInvs = true;
        setRunning(false);
        Bukkit.getScheduler().cancelTasks(plugin);
        boolean isFirst = true;
        for(Player player : Bukkit.getOnlinePlayers()){
            GiveInventories giveInventories = new GiveInventories(player);
            Kits playerKit = plugin.getKitManager().getPlayerKit(player);
            if(playerKit != null){
                giveInventories.fillGeneralInventory(player, isFirst);
                isFirst = false;
            }
        }
    }

    public void sendActionBar() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            if (!isRunning()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
                continue;
            }

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.YELLOW.toString() + getSeconds()));
        }
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {

                sendActionBar();
                if(!isRunning()){
                    if(giveInvs){
                        for(Player current : Bukkit.getOnlinePlayers()){
                            Kits currentKit = plugin.getKitManager().getPlayerKit(current);
                            GiveInventories giveInventories = new GiveInventories(current);
                            if(currentKit != null){
                                giveInventories.fillKitInventory(current, currentKit);
                                current.setHealth(current.getMaxHealth());
                            }
                        }
                        giveInvs = false;
                    }
                    return;
                }

                if(getSeconds() > 0){
                    setSeconds(getSeconds() - 1);
                }
                else{
                    setRunning(false);
                }
            }
        }.runTaskTimer(plugin, 20, 20);
    }
}