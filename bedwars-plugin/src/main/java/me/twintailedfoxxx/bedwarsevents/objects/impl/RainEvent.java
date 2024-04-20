package me.twintailedfoxxx.bedwarsevents.objects.impl;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.arena.Arena;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.enums.EventType;
import me.twintailedfoxxx.bedwarsevents.objects.enums.RainType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

public class RainEvent extends BedwarsEvent {
    private RainType rainType;
    private EntityType entity;
    private ItemStack[] items;
    private int count = 1;
    private int radius;
    private int taskId;

    public RainEvent(RainType type) {
        super("RainEvent_Dummy", EventType.RAIN);
        rainType = type;
    }

    @Override
    public void act(Player p, Arena arena) {
        arena.getWorld().setStorm(true);
        makeItRain(arena, entity, items, count, getDuration(), radius);
    }

    @Override
    public void undo(Player p, Arena arena) {
        Bukkit.getScheduler().cancelTask(taskId);
        arena.getWorld().setStorm(false);
    }

    public RainType getRainType() {
        return rainType;
    }

    public EntityType getEntity() {
        return entity;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public int getRainDuration() {
        return getDuration();
    }

    public int getCount() {
        return count;
    }

    public int getRadius() {
        return radius;
    }

    public void setRainType(RainType rainType) {
        this.rainType = rainType;
    }

    public void setEntity(EntityType entity) {
        this.entity = entity;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setRainDuration(int duration) {
        setDuration(duration);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    private void makeItRain(Arena a, EntityType type, ItemStack[] stacks, int amount, int duration, int rad) {
        Random random = new Random();
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWars.plugin, () -> {
            try {
                for(int i = 0; i < amount; i++) {
                    for(Player player : a.getPlayers()) {
                        Location loc = player.getLocation();
                        loc.setX(loc.getX() + random.nextInt(rad * 2) - rad);
                        loc.setY(loc.getY() + random.nextInt(loc.getWorld().getMaxHeight() - 100) + 100);
                        loc.setZ(loc.getZ() + random.nextInt(rad * 2) - rad);
                        if(type != null) {
                            Entity creature = loc.getWorld().spawnEntity(loc, type);
                            if(creature instanceof Fireball) {
                                ((Fireball)creature).setDirection(new Vector(0, -1, 0));
                            }
                            if(creature instanceof ExperienceOrb) {
                                ((ExperienceOrb)creature).setExperience(1000 + random.nextInt() * 300);
                            }
                            if(creature instanceof TNTPrimed) {
                                ((TNTPrimed)creature).setFuseTicks(150);
                            }
                        } else {
                            for (ItemStack stack : stacks) {
                                loc.getWorld().dropItem(loc, stack);
                            }

                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, duration);

    }
}
