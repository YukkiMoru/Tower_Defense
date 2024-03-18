package org.moru.tower_defense;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class TD_Command implements CommandExecutor, TabCompleter {

    private Platform_Manager platformManager = Platform_Manager.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                //コマンド
                ExecuteCommand(args, "kill", "kill @e[type=!player]"); // /td <command> → Execute_Commandが実行される

                //デバッグモード
                if (args[0].equals("debug")) {
                    if (args.length > 1) {
                        ExecuteDebug(args);
                    } else {
                        player.sendMessage("Usage: /td <debug> <true/false>");
                    }
                }

                //GUI
                if (args[0].equals("gui")) {
                    if (args[1].equals("TowerGUI")) {
                        Inventory gui = InventoryGUI.TowerGUI();
                        player.openInventory(gui);
                    } else {
                        player.sendMessage("Usage: /td <gui> <TowerGUI>");
                    }
                }

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
                list.add("gui");
                list.add("debug");
                list.add("kill");
                return list;
            }
            if (args.length == 2 && args[0].equals("debug")) {
                List<String> list = new ArrayList<>();
                list.add("true");
                list.add("false");
                return list;
            }
        }
        return null;
    }

    private void ExecuteDebug(String[] args) {
        boolean debug = false;
        if (args[1].equals("true")) {
            debug = true;
        }
        if (args[1].equals("false")) {
            debug = false;
        }
        platformManager.setDebugMode(debug);
    }
}
