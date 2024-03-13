package org.moru.tower_defense;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TD_Command implements CommandExecutor , TabCompleter{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                // /td <command> → Execute_Commandが実行される
                ExecuteCommand(args, "kill", "kill @e[type=!player]");
                if (args[0].equals("debug")) {
                    if (args.length > 1) {
                        ExecuteDebug(args, player);
                    } else {
                        player.sendMessage("Usage: /td <debug> <true/false>");
                    }
                }

            } else {
                player.sendMessage("Usage: /td <kill>");
            }
            return true;
        }
        return false;
    }

    private Void ExecuteCommand(String[] args, String command, String Execute_Command) {
        if (args[0].equals(command)) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Execute_Command);
            //chatにメッセージを送信
            Bukkit.broadcastMessage("Command " + command + " has been executed!");
        }
        return null;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                List<String> list = new ArrayList<>();
                list.add("show");
                list.add("debug");
                list.add("kill");
                return list;
            }
        }
        return null;
    }

    private void ExecuteDebug(String[] args, Player player) {
        Platform_Manager platformManager = Platform_Manager.getInstance();
        if (args[1].equalsIgnoreCase("true")) {
            // Enable debug mode
            platformManager.setDebugMode(true);
            player.sendMessage("Debug mode enabled");
        } else if (args[1].equalsIgnoreCase("false")) {
            // Disable debug mode
            platformManager.setDebugMode(false);
            player.sendMessage("Debug mode disabled");
        } else {
            player.sendMessage("Invalid argument for debug mode, use true or false");
        }
    }
}
