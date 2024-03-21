package org.moru.tower_defense;

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

import org.bukkit.Location;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class Construction {

    // create size class
    public class Size {
        public int x;
        public int y;
        public int z;
    }

    public void SummonStructure(Location location, String StructureName) {
        File schematic = new File("plugins/WorldEdit/schematics/" + StructureName + ".schem");
        WorldEdit worldEdit = WorldEdit.getInstance();
        ClipboardFormat format = ClipboardFormats.findByFile(schematic);
        try (Closer closer = Closer.create()) {
            FileInputStream fis = closer.register(new FileInputStream(schematic));
            BufferedInputStream bis = closer.register(new BufferedInputStream(fis));
            ClipboardReader reader = closer.register(format.getReader(bis));

            Clipboard clipboard = reader.read();

            try (EditSession editSession = worldEdit.getEditSessionFactory().getEditSession(new BukkitWorld(location.getWorld()), -1)) {
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
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
}