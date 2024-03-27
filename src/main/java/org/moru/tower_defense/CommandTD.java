package org.moru.tower_defense;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

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
                    if (args.length > 1 && args[1].equals("show")) {
                        configInstance.ShowConfig(sender);
                        sender.sendMessage("configを表示しました");
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
        SQLite.DeleteAllData();
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                List<String> list = new ArrayList<>();
                list.add("show");
                list.add("gui");
                list.add("debug");
                list.add("kill");
                list.add("sql");
                return list;
            }
            if (args.length == 2 && args[0].equals("debug")) {
                List<String> list = new ArrayList<>();
                list.add("true");
                list.add("false");
                return list;
            } else if (args.length == 2 && args[0].equals("gui")) {
                List<String> list = new ArrayList<>();
                list.add("TowerGUI");
                return list;
            } else if (args.length == 2 && args[0].equals("sql")) {
                List<String> list = new ArrayList<>();
                list.add("delete");
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
        managerPlatform.setDebugMode(debug);
    }
}
