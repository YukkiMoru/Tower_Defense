package org.moru.tower_defense;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class TD_Command implements CommandExecutor {

    private Platform_Manager platformManager = Platform_Manager.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                // /td <command> → Execute_Commandが実行される
                ExecuteCommand(args, "kill", "kill @e[type=!player]");

                if (args[0].equals("debug")) {
                    if (args.length > 1) {
                        ExecuteDebug(args);
                    } else {
                        player.sendMessage("Usage: /td <debug> <true/false>");
                    }
                }

//                ExecuteGUI(args, player);

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

//    private void ExecuteGUI(String[] args, Player player) {
//        if (args[0].equals("gui")) {
//            //GUIを開く
//            Tower_GUI towerGUI = new Tower_GUI();
//            towerGUI.openGUI(player);
//        }
//    }
}
