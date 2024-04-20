package me.twintailedfoxxx.bedwarsevents.objects.impl;

import com.andrei1058.bedwars.arena.Arena;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.enums.EventType;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ChangePlacedBlocksEvent extends BedwarsEvent
{
    private Material oldMaterial;
    private Material newBlockMaterial;

    public ChangePlacedBlocksEvent() {
        super("ChangePlacedBlocks_Dummy", EventType.CHANGE_PLACED_BLOCKS);
    }

    public Material getOldMaterial() {
        return oldMaterial;
    }

    public void setOldMaterial(Material oldMaterial) {
        this.oldMaterial = oldMaterial;
    }

    public Material getNewBlockMaterial() {
        return newBlockMaterial;
    }

    public void setNewBlockMaterial(Material newBlockMaterial) {
        this.newBlockMaterial = newBlockMaterial;
    }

    @Override
    public void act(Player player, Arena arena) {
        if(oldMaterial != null) {
            arena.getPlaced().stream().filter(x -> x.toLocation(arena.getWorld()).getBlock().getType() == oldMaterial)
                    .forEach(x -> x.toLocation(arena.getWorld()).getBlock().setType(newBlockMaterial));
            return;
        }

        if(newBlockMaterial == Material.AIR) {
            arena.getPlaced().forEach(x -> x.toLocation(arena.getWorld()).getBlock().breakNaturally());
            arena.getPlaced().clear();
        } else {
            arena.getPlaced().forEach(x -> x.toLocation(arena.getWorld()).getBlock().setType(newBlockMaterial));
            if(newBlockMaterial == Material.SAND || newBlockMaterial == Material.GRAVEL) {
                arena.getPlaced().removeIf(x -> x.toLocation(arena.getWorld()).getBlock().getType() == newBlockMaterial
                    && arena.getWorld().getBlockAt(x.toLocation(arena.getWorld()).subtract(0, 1, 0)).getType() == Material.AIR);
            }
        }
    }

    @Override
    public void undo(Player player, Arena arena) {

    }
}
