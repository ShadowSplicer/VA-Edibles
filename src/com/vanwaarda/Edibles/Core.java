package com.vanwaarda.Edibles;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class Core extends JavaPlugin implements Listener {
	public final Logger logger = Logger.getLogger("Minecraft");
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		Bukkit.getConsoleSender().sendMessage(pdfFile.getName() + " has been disabled");
	}
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + pdfFile.getName() + " version " + pdfFile.getVersion() + " has been enabled");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
		if(cmd.getName().equalsIgnoreCase("food")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.BLUE + "[Edibles] Food info not available yet. Soon, hopefully!");
				return true;
			}
		}
		return false;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onFoodEat(PlayerItemConsumeEvent event) {
		ItemStack itemEaten = event.getItem();
		final Player player = event.getPlayer();
		//ItemMeta itemEatenMeta = itemEaten.getItemMeta();
		if (itemEaten.getType().equals(Material.ROTTEN_FLESH)) {
			if (itemEaten.getDurability() == 100) { // dogFood
				player.setFoodLevel(player.getFoodLevel() + 4);
				player.setHealth(player.getHealth() - 4);
			}
			if (itemEaten.getDurability() == 101) { // cookedFlesh
				BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
				scheduler.scheduleSyncDelayedTask(this, new Runnable() {
					@Override
					public void run() {
						player.removePotionEffect(PotionEffectType.HUNGER);
					}
				}, 1L);
			}
		}
	}
// END   
}
