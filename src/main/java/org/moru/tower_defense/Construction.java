package org.moru.tower_defense;
/*
このクラスは、Tower_Defenseプラグインの建築クラスです。
構造物を生成、削除するメソッドがあります。
*/

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.util.io.Closer;

public class Construction {

    // create size class
    public class Size {
        public int x;
        public int y;
        public int z;
    }

    public void SummonStructure(Location location, String StructureName) {
        // offset the structure
        Location offset = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
        offset.setX(offset.getX() - 2);
        offset.setY(offset.getY() + 1);
        offset.setZ(offset.getZ() - 2);
        File schematic = new File("plugins/WorldEdit/schematics/" + StructureName + ".schem");
        WorldEdit worldEdit = WorldEdit.getInstance();
        ClipboardFormat format = ClipboardFormats.findByFile(schematic);
        try (Closer closer = Closer.create()) {
            FileInputStream fis = closer.register(new FileInputStream(schematic));
            BufferedInputStream bis = closer.register(new BufferedInputStream(fis));
            ClipboardReader reader = closer.register(format.getReader(bis));

            Clipboard clipboard = reader.read();

            try (EditSession editSession = worldEdit.getEditSessionFactory().getEditSession(new BukkitWorld(offset.getWorld()), -1)) {
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(BlockVector3.at(offset.getX(), offset.getY(), offset.getZ()))
                        .ignoreAirBlocks(false)
                        .build();
                Operations.complete(operation);
            } catch (WorldEditException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Size GetSizeStructure(String StructureName) {
        File schematic = new File("plugins/WorldEdit/schematics/" + StructureName + ".schem");
        WorldEdit worldEdit = WorldEdit.getInstance();
        ClipboardFormat format = ClipboardFormats.findByFile(schematic);
        Size size = new Size();
        // Get the size of the structure
        try (Closer closer = Closer.create()) {
            FileInputStream fis = closer.register(new FileInputStream(schematic));
            BufferedInputStream bis = closer.register(new BufferedInputStream(fis));
            ClipboardReader reader = closer.register(format.getReader(bis));

            Clipboard clipboard = reader.read();

            size.x = clipboard.getDimensions().getBlockX();
            size.y = clipboard.getDimensions().getBlockY();
            size.z = clipboard.getDimensions().getBlockZ();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    public void RemoveStructure(int TowerID) {
        SQLManagerTower towerManager = SQLManagerTower.getInstance();
        List<SQLManagerTower.Coordinates> coordinatesList = towerManager.GetCoordinates(TowerID);

        for (SQLManagerTower.Coordinates coordinates : coordinatesList) {
            World world = Bukkit.getWorld("TD_world"); // Replace with your world name
            Location location = new Location(world, coordinates.getX(), coordinates.getY(), coordinates.getZ());
            location.getBlock().setType(Material.AIR);
        }
    }
}