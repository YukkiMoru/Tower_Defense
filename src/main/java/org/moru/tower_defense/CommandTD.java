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
                String cmd = args[0];
                switch (cmd) {
                    case "kill":
                        ExecuteCommand(args, "kill", "kill @e[type=!player]");
                        break;
                    case "debug":
                        if (args.length > 1) {
                            ExecuteDebug(args);
                            sender.sendMessage("デバッグモードを" + args[1] + "に設定しました");
                        } else {
                            player.sendMessage("Usage: /td <debug> <true/false>");
                        }
                        break;
                    case "gui":
                        if (args.length > 1) {
                            switch (args[1]) {
                                case "PlatformGUI":
                                    Inventory gui = InventoryGUI.PlatformGUI();
                                    player.openInventory(gui);
                                    break;
                                case "TowerGUI":
                                    int TowerID = Integer.parseInt(args[2]);
                                    gui = InventoryGUI.TowerGUI(TowerID);
                                    player.openInventory(gui);
                                    break;
                                default:
                                    player.sendMessage("Usage: /td <gui> <PlatformGUI|TowerGUI>");
                                    break;
                            }
                        }
                        break;
                    case "sql":
                        if (args.length > 1) {
                            if (args[1].equals("delete")) {
                                ExecuteSqlDelete();
                                sender.sendMessage("データを削除しました");
                            }
                        } else {
                            player.sendMessage("Usage: /td <sql> <delete>");
                        }
                        break;
                    case "config":
                        if (args.length > 2 && args[1].equals("show")) {
                            Config configInstance = new Config(plugin);
                            configInstance.ShowConfig(sender, args[2]);
                            sender.sendMessage(args[2] + "のconfigを表示しました");
                        } else {
                            player.sendMessage("Usage: /td <config> <show>");
                        }
                        break;
                    default:
                        player.sendMessage("Unknown command: " + cmd);
                        break;
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
        boolean debug = "true".equals(args[1]);
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
