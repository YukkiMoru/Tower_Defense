package org.moru.tower_defense;

import org.bukkit.plugin.java.JavaPlugin;

public final class Tower_Defense extends JavaPlugin {

    @Override
    public void onEnable() {
        // mob_motionの起動
        // リスナーの登録
        getServer().getPluginManager().registerEvents(new MobListener(this), this);

        // リスナーの登録
        // Create an instance of the building class, passing this plugin instance to the constructor
        Building buildingInstance = new Building(this);

        // Register the building instance as an event listener
        getServer().getPluginManager().registerEvents(buildingInstance, this);



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
