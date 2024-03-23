package org.moru.tower_defense;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Tower_Defense extends JavaPlugin {
    private SQLiteManagerTower sqliteManagerTower;
    private SQLite sqlite = new SQLite();
    @Override
    public void onEnable() {
        // mob_motionの起動
        getServer().getPluginManager().registerEvents(new ListenerMob(this), this);

        // Buildingの起動
        ListenerBlock buildingInstance = new ListenerBlock();
        getServer().getPluginManager().registerEvents(buildingInstance, this);

        // TD_Commandの起動
        getCommand("td").setExecutor(new CommandTD());

        // SQLiteManagerTowerの起動
        sqliteManagerTower = SQLiteManagerTower.getInstance();

        //InventoryClickListenerの起動
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ListenerInventoryClick(), this);

        // チャットにメッセージを送信("Tower_Defenseプラグインが有効化されました")
        getServer().getConsoleSender().sendMessage("Tower_Defenseプラグインが有効化されました");
    }

    @Override
    public void onDisable() {
        // shutdown sqlite
        sqlite.shutdown();

        // チャットにメッセージを送信("Tower_Defenseプラグインが無効化されました")
        getServer().getConsoleSender().sendMessage("Tower_Defenseプラグインが無効化されました");
    }
}