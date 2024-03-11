package org.moru.tower_defense;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class TD_Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                if (args[0].equals("kill")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=!minecraft:player]");
                }
            } else {
                player.sendMessage("Usage: /td <kill>");
            }
            return true;
        }
        return false;
    }
}
