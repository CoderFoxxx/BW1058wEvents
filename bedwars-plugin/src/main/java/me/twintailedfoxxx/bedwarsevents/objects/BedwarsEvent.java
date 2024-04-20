package me.twintailedfoxxx.bedwarsevents.objects;

import com.andrei1058.bedwars.arena.Arena;
import me.twintailedfoxxx.bedwarsevents.objects.enums.EventType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class BedwarsEvent
{
    private String name;
    private String displayName;
    private String description;
    private int weight;
    private int duration;
    private EventType type;
    private boolean actIndividually;

    public BedwarsEvent(String name, EventType type) {
        this.name = name;
        this.displayName = name;
        this.description = "Dummy desc.";
        this.type = type;
        this.weight = 1;
        this.duration = 0;
        this.actIndividually = false;
    }

    public BedwarsEvent(String name, String displayName, int chance, EventType type) {
        this.name = name;
        this.displayName = displayName;
        this.weight = chance;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getDuration() {
        return duration;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public EventType getType() {
        return type;
    }

    public boolean actingIndividually() {
        return actIndividually;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setDisplayName(String newDisplayName) {
        this.displayName = ChatColor.translateAlternateColorCodes('&', newDisplayName);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeight(int newWeight) {
        this.weight = newWeight;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public void setActIndividually(boolean actIndividually) {
        this.actIndividually = actIndividually;
    }

    public abstract void act(Player player, Arena arena);
    public abstract void undo(Player player, Arena arena);

    public static Class<? extends BedwarsEvent> getInstance(BedwarsEvent e) { return e.getClass(); }
}
