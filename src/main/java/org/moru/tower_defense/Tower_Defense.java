package org.moru.tower_defense;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

public final class Tower_Defense extends JavaPlugin implements CommandExecutor {
    private mob_motion mobMotionTask;

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("Tower_Defenseプラグインが有効化されました");
        getServer().getPluginManager().registerEvents(new MobListener(this), this);
        getCommand("towerdefense").setExecutor(this);

        // mob_motionタスクが実行中である場合
        if (mobMotionTask != null) {
            // mob_motionタスクをキャンセルする
            mobMotionTask.cancel();
            mobMotionTask = null;
            // メッセージを送信する("mob_motionタスクを停止しました")
            getServer().getConsoleSender().sendMessage("mob_motionタスクを停止しました");
        }
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("Tower_Defenseプラグインが無効化されました");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // コマンドの処理
        if (args.length > 1) {
            // コマンドが引数(mob_motion)を持っている場合
            if ("mob_motion".equalsIgnoreCase(args[0])) {
                // コマンドが引数(start)を持っている場合
                if ("start".equalsIgnoreCase(args[1])) {
                    // mob_motionタスクが実行中でない場合
                    if (mobMotionTask == null) {
                        // mob_motionタスクを生成し、実行する
                        mobMotionTask = new mob_motion((LivingEntity) sender);
                        mobMotionTask.runTaskTimer(this, 0L, 1L);
                        // メッセージを送信する("mob_motionタスクを開始しました")
                        sender.sendMessage("mob_motionタスクを開始しました");
                        return true;
                    } else {
                        sender.sendMessage("mob_motionタスクは既に開始されています");
                        return true;
                    }
                }

                // コマンドが引数(stop)を持っている場合
                if ("stop".equalsIgnoreCase(args[1])) {
                    // mob_motionタスクが実行中である場合
                    if (mobMotionTask != null) {
                        // mob_motionタスクをキャンセルする
                        mobMotionTask.cancel();
                        mobMotionTask = null;
                        // メッセージを送信する("mob_motionタスクを終了しました")
                        sender.sendMessage("mob_motionタスクを終了しました");
                        return true;
                    } else {
                        sender.sendMessage("mob_motionタスクは既に停止されています");
                        return true;
                    }
                }
            }
        } else {
            sender.sendMessage("引数が不足しています");
        }
        return false;
    }
}