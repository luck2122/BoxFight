package bysap.boxfight.inventories;

import bysap.boxfight.kits.Kits;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class GiveInventories {
    private Inventory kitInv;

    private ItemStack tankItem, ninjaItem, hexeItem,
            bambus = new ItemStack(Material.BAMBOO),
            ninjaArmor = new ItemStack(Material.LEATHER_LEGGINGS),
            hexeArmor = new ItemStack(Material.LEATHER_CHESTPLATE);

    private ItemMeta tankItemMeta, ninjaItemMeta, hexeItemMeta, bambusMeta = bambus.getItemMeta();

    private LeatherArmorMeta ninjaArmorMeta =(LeatherArmorMeta) ninjaArmor.getItemMeta(),
            hexeArmorMeta = (LeatherArmorMeta) hexeArmor.getItemMeta();

    public static final String TANKKIT = "Tank", NINJAKIT = "Ninja", HEXEKIT = "Hexe", KIT_INVENTORY = "Kit-Inventar";

    public GiveInventories(final Player player){
        kitInv = Bukkit.createInventory(player, 9, GiveInventories.KIT_INVENTORY);
        bambusMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
        bambus.setItemMeta(bambusMeta);
        ninjaArmorMeta.setColor(Color.GREEN);
        ninjaArmor.setItemMeta(ninjaArmorMeta);
        hexeArmorMeta.setColor(Color.BLACK);
        hexeArmor.setItemMeta(hexeArmorMeta);
    }


    public Inventory openKitInv(){
        kitInv.setItem(0, new ItemStack(Material.GLASS_PANE));
        kitInv.setItem(8, new ItemStack(Material.GLASS_PANE));

        tankItem = new ItemStack(Material.IRON_CHESTPLATE);
        tankItemMeta = tankItem.getItemMeta();
        tankItemMeta.setDisplayName(ChatColor.YELLOW + TANKKIT);
        tankItem.setItemMeta(tankItemMeta);
        kitInv.setItem(3, tankItem);

        ninjaItem = new ItemStack(Material.IRON_SWORD);
        ninjaItemMeta = ninjaItem.getItemMeta();
        ninjaItemMeta.setDisplayName(ChatColor.DARK_RED + NINJAKIT);
        ninjaItem.setItemMeta(ninjaItemMeta);
        kitInv.setItem(4, ninjaItem);

        hexeItem = new ItemStack(Material.GLASS_BOTTLE);
        hexeItemMeta = hexeItem.getItemMeta();
        hexeItemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + HEXEKIT);
        hexeItem.setItemMeta(hexeItemMeta);
        kitInv.setItem(5, hexeItem);
        return kitInv;
    }

    public void fillGeneralInventory(final Player player, final boolean isFirst){
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.getInventory().clear();
        if(isFirst)
            player.getInventory().setItem(3, new ItemStack(Material.BLUE_CONCRETE, 16));
        else
            player.getInventory().setItem(3, new ItemStack(Material.RED_CONCRETE, 16));
        player.getInventory().setItem(4, new ItemStack(Material.COBWEB, 1));
        Potion healPotion = new Potion(PotionType.INSTANT_HEAL, 1);
        healPotion.setSplash(true);
        player.getInventory().setItem(5, healPotion.toItemStack(1));
        player.getInventory().setItem(6, new ItemStack(Material.SWEET_BERRIES, 16));
    }

    public void fillKitInventory(final Player player, final Kits kit){

        switch (kit) {
            case HEXE -> {
                player.getInventory().setChestplate(hexeArmor);
                player.getInventory().addItem(new ItemStack(Material.MUSHROOM_STEW, 1));
                Potion damagePotion = new Potion(PotionType.INSTANT_DAMAGE, 1);
                damagePotion.setSplash(true);
                player.getInventory().addItem(damagePotion.toItemStack(1));
            }
            case NINJA -> {
                player.getInventory().addItem(new ItemStack(Material.VINE, 6));
                player.getInventory().addItem(new ItemStack(Material.LADDER, 3));
                player.getInventory().setLeggings(ninjaArmor);
            }
            case TANK -> {
                player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
            }
        }
    }
}