package org.moru.tower_defense;

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
                player.sendMessage("TD command executed with argument: " + args[0]);
            } else {
                player.sendMessage("TD command executed without arguments!");
            }
            return true;
        }
        return false;
    }
}
