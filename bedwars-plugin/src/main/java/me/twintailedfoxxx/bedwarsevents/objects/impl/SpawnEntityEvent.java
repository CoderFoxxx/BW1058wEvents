package me.twintailedfoxxx.bedwarsevents.objects.impl;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.arena.Arena;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.enums.EventType;
import me.twintailedfoxxx.bedwarsevents.objects.enums.SpawnLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.List;

public class SpawnEntityEvent extends BedwarsEvent {
    private List<Entity> spawnedEntities = new ArrayList<>();
    private EntityType entity;
    private boolean tamed;
    private boolean baby;
    private SpawnLocation location;
    private int count;

    public SpawnEntityEvent(EntityType entity, SpawnLocation location, int count) {
        super("SpawnEntityEvent_Dummy", EventType.SPAWN_ENTITY);
        this.entity = entity;
        this.location = location;
        this.count = count;
    }

    @Override
    public void act(Player p, Arena arena) {
        for(int i = 0; i < count; i++) {
            switch (location) {
                case TEAM_SPAWN:
                    for(ITeam team : arena.getTeams()) {
                        Entity e;
                        if(tamed) {
                            for(Player pl : team.getMembers()) {
                                e = arena.getWorld().spawnEntity(team.getSpawn(), entity);
                                if(e instanceof Tameable) {
                                    Tameable tameable = (Tameable) e;
                                    tameable.setTamed(true);
                                    tameable.setOwner(pl);
                                    if(e.getType() == EntityType.WOLF) {
                                        Wolf wolf = (Wolf)tameable;
                                        wolf.setCollarColor(team.getColor().dye());
                                    } else if(e.getType() == EntityType.HORSE) {
                                        Horse horse = (Horse)tameable;
                                        horse.getInventory().setSaddle(BedWars.nms.createItemStack("SADDLE", 1, (short)0));
                                        horse.getInventory().setArmor(BedWars.nms.createItemStack("IRON_HORSE_ARMOR", 1, (short)0));
                                    }
                                }
                                spawnedEntities.add(e);
                            }
                        } else {
                            Location loc = new Location(arena.getWorld(),
                                    team.getBed().getX(),
                                    arena.getWorld().getHighestBlockYAt(team.getBed()) + 1,
                                    team.getBed().getZ());
                            if(entity == EntityType.GHAST || entity == EntityType.ENDER_DRAGON || entity == EntityType.BLAZE) {
                                loc.setY(loc.getY() + 15);
                            }
                            e = arena.getWorld().spawnEntity(loc, entity);
                            if(e instanceof Ageable && baby) {
                                ((Ageable) e).setBaby();
                            }
                            spawnedEntities.add(e);
                        }
                    }
                    break;
                case CENTER:
                    Entity e = arena.getWorld().spawnEntity(new Location(arena.getWorld(), 0,
                            arena.getWorld().getHighestBlockYAt(0, 0) + 1, 0), entity);
                    if(e instanceof Ageable && baby) {
                        ((Ageable) e).setBaby();
                    }
                    spawnedEntities.add(e);
                    break;
                case PLAYER:
                    for(Player player : arena.getPlayers()) {
                        if(!arena.isSpectator(player)) {
                            Entity entity1 = arena.getWorld().spawnEntity(player.getLocation().clone().add(0, 2, 0), entity);
                            spawnedEntities.add(entity1);
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void undo(Player p, Arena arena) {
        for(Entity e : spawnedEntities) {
            e.remove();
        }

        spawnedEntities.clear();
    }

    public EntityType getEntity() {
        return entity;
    }

    public void setEntity(EntityType entity) {
        this.entity = entity;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isTamed() {
        return tamed;
    }

    public void setTamed(boolean tamed) {
        this.tamed = tamed;
    }

    public boolean isBaby() {
        return baby;
    }

    public void setBaby(boolean baby) {
        this.baby = baby;
    }
}
