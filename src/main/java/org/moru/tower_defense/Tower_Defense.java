package org.moru.tower_defense;

import org.bukkit.plugin.java.JavaPlugin;

public final class Tower_Defense extends JavaPlugin {

    @Override
    public void onEnable() {
        // mob_motionの起動
        getServer().getPluginManager().registerEvents(new Mob_Listener(this), this);

        // Buildingの起動
        Building buildingInstance = new Building(this);
        getServer().getPluginManager().registerEvents(buildingInstance, this);

        // TD_Commandの起動
        getCommand("td").setExecutor(new TD_Command());

        // Sqliteの起動
        SQLite sqlite = new SQLite();
        sqlite.connect();

        // チャットにメッセージを送信("Tower_Defenseプラグインが有効化されました")
        getServer().getConsoleSender().sendMessage("Tower_Defenseプラグインが有効化されました");
    }

    @Override
    public void onDisable() {
        // チャットにメッセージを送信("Tower_Defenseプラグインが無効化されました")
        getServer().getConsoleSender().sendMessage("Tower_Defenseプラグインが無効化されました");
    }
}
