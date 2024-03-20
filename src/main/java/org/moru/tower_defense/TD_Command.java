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
    public Tower_Manager towerManager = new Tower_Manager();
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

                if (args[0].equals("show")) {
                    if (args.length > 1) {
                        ExecuteSqlShow(args, player);
                    } else {
                        player.sendMessage("Usage: /td <show>");
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

    private void ExecuteDebug(String[] args, Player player) {
        Platform_Manager platformManager = Platform_Manager.getInstance();
        if (args[1].equalsIgnoreCase("true")) {
            // Enable debug mode
            platformManager.setDebugMode(true);
            player.sendMessage("デバッグモードが起動しました");
        } else if (args[1].equalsIgnoreCase("false")) {
            // Disable debug mode
            platformManager.setDebugMode(false);
            player.sendMessage("デバッグモードが停止しました");
        } else {
            player.sendMessage("デバッグモードでは無効な引数です。trueまたはfalseを使ってください。");
        }
    }

    public void ExecuteSqlShow(String[] args, Player player){
        //show sql data
            Tower_Manager.TowerData towerData = towerManager.getTowerData(Integer.parseInt(args[1]));        if (towerData != null) {
            player.sendMessage("TowerID: " + args[1]);
            player.sendMessage("TowerName: " + towerData.getTowerName());
            player.sendMessage("TowerType: " + towerData.getTowerType());
            player.sendMessage("TowerLevel: " + towerData.getLevel());
        } else {
            player.sendMessage("No data found for TowerID: " + args[1]);
        }
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
            if(args.length == 2 && args[0].equals("debug")){
                if(args[0].equals("debug")){
                    List<String> list = new ArrayList<>();
                    list.add("true");
                    list.add("false");
                    return list;
                }
            }
        }
        return null;
    }
}