package me.twintailedfoxxx.bedwarsevents.objects;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.ChatColor;

public abstract class BedwarsEvent
{
    private String name;
    private String displayName;
    private double chance;
    private EventType type;

    public BedwarsEvent(String name, EventType type) {
        this.name = name;
        this.displayName = name;
        this.type = type;
        this.chance = 0.5d;
    }

    public BedwarsEvent(String name, String displayName, double chance, EventType type) {
        this.name = name;
        this.displayName = displayName;
        this.chance = chance;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getChance() {
        return chance;
    }

    public String getDisplayName() {
        return displayName;
    }

    public EventType getType() {
        return type;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setDisplayName(String newDisplayName) {
        this.displayName = ChatColor.translateAlternateColorCodes('&', newDisplayName);
    }

    public void setChance(double newChance) {
        this.chance = newChance;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public abstract void act(Arena arena);
}
