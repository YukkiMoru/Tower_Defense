package org.moru.tower_defense;
/*
このファイルは、Tower_Defenseプラグインのコマンドクラスです。
以下のコマンドを実装しています。
/td <debug> <true/false>         → デバッグモードを設定
/td <kill>                       → 全てのエンティティを削除
/td <gui> <PlatformGUI|TowerGUI> → GUIを表示
/td <sql> <delete>               → データベースのデータを削除
/td <config> <show> <xxx.yml>    → xxx.ymlの中身を表示
*/
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class CommandTD implements CommandExecutor, TabCompleter {
    private ManagerPlatform managerPlatform = ManagerPlatform.getInstance();
    private JavaPlugin plugin;

    public CommandTD(JavaPlugin plugin) {
        this.plugin = plugin;
    }
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
                        sender.sendMessage("デバッグモードを" + args[1] + "に設定しました");
                    } else {
                        player.sendMessage("Usage: /td <debug> <true/false>");
                    }
                }
                //GUI
                if (args[0].equals("gui")) {
                    if (args.length > 1 && args[1].equals("PlatformGUI")) {
                        Inventory gui = InventoryGUI.PlatformGUI();
                        player.openInventory(gui);
                    } else if (args.length > 1 && args[1].equals("TowerGUI")) {
                        int TowerID = Integer.parseInt(args[2]);
                        Inventory gui = InventoryGUI.TowerGUI(TowerID);
                        player.openInventory(gui);
                    } else {
                        player.sendMessage("Usage: /td <gui> <PlatformGUI|TowerGUI>");
                    }
                }

                //sql
                if (args[0].equals("sql")) {
                    if (args.length > 1 && args[1].equals("delete")) {
                        ExecuteSqlDelete();
                        sender.sendMessage("データを削除しました");
                    } else {
                        player.sendMessage("Usage: /td <sql> <delete>");
                    }
                }

                //config
                if (args[0].equals("config")) {
                    //get instance
                    Config configInstance = new Config(plugin);
                    if (args.length > 2 && args[1].equals("show")) {
                        configInstance.ShowConfig(sender, args[2]);
                        sender.sendMessage(args[2] + "のconfigを表示しました");
                    } else {
                        player.sendMessage("Usage: /td <config> <show>");
                    }
                }
            }
            return true;
        }
        return false;
    }

    private Void ExecuteCommand(String[] args, String AliasCommand, String ExecuteCommand) {
        if (args[0].equals(AliasCommand)) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ExecuteCommand);
            Bukkit.broadcastMessage(AliasCommand + "が実行されました!");
        }
        return null;
    }


    private void ExecuteSqlDelete() {
        SQL.DeleteAllData();
    }

    private void ExecuteDebug(String[] args) {
        boolean debug = false;
        if (args[1].equals("true")) {
            debug = true;
        }
        if (args[1].equals("false")) {
            debug = false;
        }
        managerPlatform.setDebugMode(debug);
    }

    private static final Map<String, List<String>> COMMANDS = new HashMap<>();

    static {
        COMMANDS.put("", Arrays.asList("show", "gui", "debug", "kill", "sql", "config"));
        COMMANDS.put("debug", Arrays.asList("true", "false"));
        COMMANDS.put("gui", Arrays.asList("TowerGUI"));
        COMMANDS.put("sql", Arrays.asList("delete"));
        COMMANDS.put("config", Arrays.asList("show"));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            return COMMANDS.getOrDefault(args.length > 0 ? args[0] : "", Collections.emptyList());
        }
        return null;
    }
}
