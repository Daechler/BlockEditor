package net.daechler.blockeditor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockEditor extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin activation message
        getLogger().info(ChatColor.GREEN + getName() + " has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin disable message
        getLogger().info(ChatColor.RED + getName() + " has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("replaceblock")) {
            if (args.length == 1) {
                Material material = Material.getMaterial(args[0].toUpperCase());

                if (material != null) {
                    Block targetBlock = player.getTargetBlock(null, 10);
                    targetBlock.setType(material);
                    player.sendMessage(ChatColor.GREEN + "Block replaced with " + material.name() + ".");
                } else {
                    player.sendMessage(ChatColor.RED + "Invalid material specified.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /replaceblock <material>");
            }

            return true;
        }

        if (command.getName().equalsIgnoreCase("deleteblock")) {
            Block targetBlock = player.getTargetBlock(null, 10);
            targetBlock.setType(Material.AIR);
            player.sendMessage(ChatColor.GREEN + "Block deleted.");
            return true;
        }

        if (command.getName().equalsIgnoreCase("addblock")) {
            if (args.length == 1) {
                Material material = Material.getMaterial(args[0].toUpperCase());

                if (material != null) {
                    Block targetBlock = player.getTargetBlock(null, 10);
                    Block surfaceBlock = targetBlock.getRelative(0, 1, 0);

                    if (surfaceBlock.getType() == Material.AIR) {
                        surfaceBlock.setType(material);
                        player.sendMessage(ChatColor.GREEN + material.name() + " added on the surface of the block.");
                    } else {
                        player.sendMessage(ChatColor.RED + "Cannot add block on a non-air surface.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Invalid material specified.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /addblock <material>");
            }

            return true;
        }

        return false;
    }
}
