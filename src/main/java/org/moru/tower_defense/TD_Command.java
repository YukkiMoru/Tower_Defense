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

public class TD_Command implements CommandExecutor , TabCompleter{
    public Tower_Manager towerManager = new Tower_Manager();
    private Platform_Manager platformManager = Platform_Manager.getInstance();
    private GUI_Manager guiManager = new GUI_Manager();

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
                    if (args.length > 1) {
                        if (args[1].equals("create")) {
                            if (args.length == 4) {
                                String name = args[2];
                                int size = Integer.parseInt(args[3]);
                                String title = args[4];
                                // Call the createAndSaveGui method of the GUI_Manager class
                                guiManager.createAndSaveGui(name, size, title);
                            } else {
                                player.sendMessage("Usage: /td gui create <name> <size> <title>");
                            }
                        } else if (args[1].equals("show")) {
                            if (args.length == 2) {
                                String name = args[2];
                                // Call the showGui method of the GUI_Manager class
                                guiManager.showGui(name, player);
                            } else {
                                player.sendMessage("Usage: /td gui show <name>");
                            }
                        } else if (args[1].equals("remove")) {
                            if (args.length == 2) {
                                String name = args[2];
                                // Call the removeGui method of the GUI_Manager class
                                guiManager.removeGui(name);
                            } else {
                                player.sendMessage("Usage: /td gui remove <name>");
                            }
                        }
                    } else {
                        player.sendMessage("Usage: /td gui <create/show/remove>");
                    }
                }

                //sql
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


    public void ExecuteSqlShow(String[] args, Player player){
        //show sql data
        Tower_Manager.TowerData towerData = towerManager.getTowerData(args[1]);
        if (towerData != null) {
            player.sendMessage("Tower Name: " + args[1]);
            player.sendMessage("Location: " + towerData.getLocation());
            player.sendMessage("Type: " + towerData.getType());
            player.sendMessage("Level: " + towerData.getLevel());
        } else {
            player.sendMessage("No data found for tower: " + args[1]);
        }
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
                return list;
            }
            if (args.length == 2) {
                if (args[0].equals("debug")) {
                    List<String> list = new ArrayList<>();
                    list.add("true");
                    list.add("false");
                    return list;
                } else if (args[0].equals("gui")) {
                    List<String> list = new ArrayList<>();
                    list.add("create");
                    list.add("show");
                    list.add("remove");
                    return list;
                }
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
