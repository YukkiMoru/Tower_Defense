package org.moru.tower_defense;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Building implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() == Material.OAK_PLANKS) {
                if (isCenterOf3x3(event.getClickedBlock().getLocation())) {
                    Player player = event.getPlayer();
                    player.sendMessage("You clicked the center of a 3x3 oak wood!");
                }
            }
        }
    }

    private boolean isCenterOf3x3(Location location) {
        // Check if the blocks around the clicked block form a 3x3x1 of oak wood
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (location.clone().add(x, 0, z).getBlock().getType() != Material.OAK_PLANKS) {
                    return false;
                }
            }
        }
        return true;
    }
}