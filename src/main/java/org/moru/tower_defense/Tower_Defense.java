package org.moru.tower_defense;

import org.bukkit.plugin.java.JavaPlugin;

public final class Tower_Defense extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        // チャットにメッセージを送信("Tower_Defenseプラグインが有効化されました")
        getServer().getConsoleSender().sendMessage("Tower_Defenseプラグインが有効化されました");


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // チャットにメッセージを送信("Tower_Defenseプラグインが無効化されました")
        getServer().getConsoleSender().sendMessage("Tower_Defenseプラグインが無効化されました");


    }
}
