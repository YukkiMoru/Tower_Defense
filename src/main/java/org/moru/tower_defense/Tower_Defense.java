package org.moru.tower_defense;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Tower_Defense extends JavaPlugin {
    private SQLiteManagerTower sqliteManagerTower;
    private SQLite sqlite = new SQLite();
    @Override
    public void onEnable() {
        // MobMotionの起動
        getServer().getPluginManager().registerEvents(new ListenerMob(this), this);

        // ListenerBlockの起動
        ListenerBlock listenerBlock = new ListenerBlock();
        getServer().getPluginManager().registerEvents(listenerBlock, this);

        // CommandTDの起動
        getCommand("td").setExecutor(new CommandTD());

        // SQLiteManagerTowerの起動
        sqliteManagerTower = SQLiteManagerTower.getInstance();

        //ListenerInventoryClickの起動
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ListenerInventoryClick(listenerBlock), this);

        getServer().getConsoleSender().sendMessage("Tower_Defenseプラグインが有効化されました");
    }

    @Override
    public void onDisable() {
        // SQLiteのシャットダウン
        sqlite.shutdown();

        getServer().getConsoleSender().sendMessage("Tower_Defenseプラグインが無効化されました");
    }
}