package me.twintailedfoxxx.bedwarsevents.objects.base;

import com.andrei1058.bedwars.arena.Arena;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.EventType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetItemEvent extends BedwarsEvent {
    private ItemStack newItem;
    private int slot;

    public SetItemEvent() {
        super("Dummy_SetItem", EventType.SET_ITEM);
        this.newItem = new ItemStack(Material.AIR);
        this.slot = 0;
    }

    public SetItemEvent(String name, ItemStack newItem, int slot) {
        super(name, EventType.SET_ITEM);
        this.newItem = newItem;
        this.slot = slot;
    }

    public ItemStack getNewItem() {
        return newItem;
    }

    public int getSlot() {
        return slot;
    }

    public void setNewItem(ItemStack newItem) {
        this.newItem = newItem;
    }

    public void setSlot(int newSlot) {
        this.slot = newSlot;
    }

    @Override
    public void act(Arena arena) {
        for(Player player : arena.getPlayers()) {
            player.getInventory().setItem(getSlot(), getNewItem());
        }
    }
}
