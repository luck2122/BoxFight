package bysap.boxfight.listener;

import bysap.boxfight.inventories.GiveInventories;
import bysap.boxfight.kits.KitManager;
import bysap.boxfight.kits.Kits;
import bysap.boxfight.main.Boxfight;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class GameProtectionListener implements Listener {

    private final Boxfight plugin;
    private final KitManager kitManager;
    private int rounds = 0;
    private int player1Win = 0, player2Win = 0;
    private final ArrayList<Player> playerList = new ArrayList<>();
    private Location deathLocation;
    private boolean isFirst = true;

    public GameProtectionListener(final Boxfight plugin){
        this.plugin = plugin;
        kitManager = plugin.getKitManager();
    }

    @EventHandler(priority =  EventPriority.HIGHEST)
    public void onPlayerDamage(EntityDamageEvent event){
        if(plugin.getTimer().isRunning())
           event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntitySpawn(CreatureSpawnEvent event){
            event.setCancelled(true);
    }

    @EventHandler
    public void handlePlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Kits playerKit = plugin.getKitManager().getPlayerKit(player);
        if(playerKit != null){
            if(plugin.getTimer().isRunning())
                player.teleport(event.getFrom());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFoodLevelChange(FoodLevelChangeEvent event){
        event.setCancelled(false);
    }

   @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
       if(!(event.getWhoClicked() instanceof Player)) return;
       Player player = (Player) event.getWhoClicked();
       Kits playerKit = plugin.getKitManager().getPlayerKit(player);
       if(!(event.getView().getTitle().equals(GiveInventories.KIT_INVENTORY))) return;

       ItemStack clickedItem = event.getCurrentItem();

       if(clickedItem == null) {
           return;
       }
       if(clickedItem.getItemMeta() == null) return;
       GiveInventories giveInventories = new GiveInventories(player);

       String clickedItemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName().toString().toLowerCase(Locale.ROOT));
       switch (clickedItemName) {
           case "hexe" -> {
               player.sendMessage(ChatColor.GRAY + "Du hast das Kit " + ChatColor.GREEN + clickedItem.getItemMeta().getDisplayName() + ChatColor.GRAY + " ausgewählt");
               kitManager.addPlayerKit(player, Kits.HEXE);
               playerKit = kitManager.getPlayerKit(player);
               playerList.add(player);
               giveInventories.fillGeneralInventory(player, isFirst);
               isFirst = false;
               plugin.setIsFirst(false);
               event.setCancelled(true);
           }
           case "tank" -> {
               player.sendMessage(ChatColor.GRAY + "Du hast das Kit " + ChatColor.GREEN + clickedItem.getItemMeta().getDisplayName() + ChatColor.GRAY + " ausgewählt");
               kitManager.addPlayerKit(player, Kits.TANK);
               playerKit = kitManager.getPlayerKit(player);
               playerList.add(player);
               giveInventories.fillGeneralInventory(player, isFirst);
               isFirst = false;
               plugin.setIsFirst(false);
               event.setCancelled(true);
           }
           case "ninja" -> {
               player.sendMessage(ChatColor.GRAY + "Du hast das Kit " + ChatColor.GREEN + clickedItem.getItemMeta().getDisplayName() + ChatColor.GRAY + " ausgewählt");
               kitManager.addPlayerKit(player, Kits.NINJA);
               playerKit = kitManager.getPlayerKit(player);
               playerList.add(player);
               plugin.setPlayerList(playerList);
               giveInventories.fillGeneralInventory(player, isFirst);
               isFirst = false;
               plugin.setIsFirst(false);
               event.setCancelled(true);
           }
           default -> {
               player.sendMessage("Etwas hat nicht geklappt " + clickedItem.getItemMeta().getDisplayName().toLowerCase(Locale.ROOT));
               event.setCancelled(true);
           }
       }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSoupClick(PlayerInteractEvent event){
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getAction() != Action.RIGHT_CLICK_AIR) return;
        Player player = event.getPlayer();
        event.setUseItemInHand(Event.Result.DENY);
        if(player.getInventory().getItemInMainHand().getType() == Material.MUSHROOM_STEW){
            player.setHealth(player.getHealth() + 4);
            if(player.getHealth() >= 40)
                player.setHealth(40);
            player.getInventory().removeItem(player.getInventory().getItemInMainHand());
            player.getInventory().setItemInMainHand(new ItemStack(Material.BOWL ,1));
        }else if(player.getInventory().getItemInOffHand().getType() == Material.MUSHROOM_STEW){
            player.setHealth(player.getHealth() + 4);
            if(player.getHealth() >= 40)
                player.setHealth(40);
            player.getInventory().removeItem(player.getInventory().getItemInOffHand());
            player.getInventory().setItemInMainHand(new ItemStack(Material.BOWL ,1));
        }else if(player.getInventory().getItemInMainHand().getType() == Material.BAMBOO){
            event.setUseItemInHand(Event.Result.DENY);
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemDrop(EntityDropItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        if(playerList.isEmpty()) return;

        if(playerList.get(0) == player)
            player1Win++;
        else if(playerList.get(1) == player)
            player2Win++;

        rounds++;
        if(rounds == 3){
            if(player1Win < player2Win){
                Bukkit.broadcastMessage(ChatColor.GRAY + "Der Spieler " + ChatColor.GOLD + playerList.get(0).getName() + ChatColor.GRAY + " hat gewonnen");
                rounds = 0;
                player2Win = 0;
                player1Win = 0;
                for(Player current : Bukkit.getOnlinePlayers()){
                    Kits playerKits = plugin.getKitManager().getPlayerKit(current);
                    if(playerKits == Kits.TANK){
                        current.removePotionEffect(PotionEffectType.SLOW);
                    }
                }
                playerList.clear();
            }else {
                Bukkit.broadcastMessage(ChatColor.GRAY + "Der Spieler " + ChatColor.GOLD + playerList.get(1).getName() + ChatColor.GRAY + " hat gewonnen");
                rounds = 0;
                player2Win = 0;
                player1Win = 0;
                for(Player current : Bukkit.getOnlinePlayers()){
                    Kits playerKits = plugin.getKitManager().getPlayerKit(current);
                    if(playerKits == Kits.TANK){
                        current.removePotionEffect(PotionEffectType.SLOW);
                    }
                }
                playerList.clear();
            }
        }
        deathLocation = player.getLocation();
        event.getDrops().clear();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        event.setRespawnLocation(deathLocation);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Block block = event.getBlockPlaced();
        Player player = event.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE) return;
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            public void run() {
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    public void run() {
                        block.setType(Material.AIR);
                    }
                }, 40L);
            }
        }, 40L);
    }
}