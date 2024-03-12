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
                // /td <command> → Execute_Commandが実行される
                ExecuteCommand(args, "kill", "kill @e[type=!player]");

                ExecuteClass(args, "OpenGUI", player);
            } else {
                player.sendMessage("Usage: /td <kill||OpenGUI>");
            }
            return true;
        }
        return false;
    }

    private Void ExecuteCommand(String[] args, String command, String Execute_Command) {
        if (args[0].equals(command)) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Execute_Command);
        }
        return null;
    }
    private Void ExecuteClass(String[] args, String command, Player player) {
        if(args[0].equals(command)){
            Tower_GUI towerGUI = new Tower_GUI();
            towerGUI.openGUI(player);
            return null;
        }
        return null;
    }
}
