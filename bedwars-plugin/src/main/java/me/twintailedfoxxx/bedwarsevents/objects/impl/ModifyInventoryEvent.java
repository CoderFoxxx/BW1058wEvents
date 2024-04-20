package me.twintailedfoxxx.bedwarsevents.objects.impl;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.arena.Arena;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.enums.EventType;
import me.twintailedfoxxx.bedwarsevents.objects.enums.InvModifyType;
import me.twintailedfoxxx.bedwarsevents.objects.enums.ModifyMode;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

import java.util.Random;
import java.util.stream.Collectors;

public class ModifyInventoryEvent extends BedwarsEvent {
    private InvModifyType type;
    private ModifyMode mode;
    private ItemStack[] items;
    private int slot;
    private ITeam teamInvolved;
    private final ItemStack[] DISCS = {
            BedWars.nms.createItemStack("MUSIC_DISC_11", 1, (short)0),
            BedWars.nms.createItemStack("MUSIC_DISC_13", 1, (short)0),
            BedWars.nms.createItemStack("MUSIC_DISC_5", 1, (short)0),
            BedWars.nms.createItemStack("MUSIC_DISC_BLOCKS", 1, (short)0),
            BedWars.nms.createItemStack("MUSIC_DISC_CAT", 1, (short)0),
            BedWars.nms.createItemStack("MUSIC_DISC_CHIRP", 1, (short)0),
            BedWars.nms.createItemStack("MUSIC_DISC_FAR", 1, (short)0),
            BedWars.nms.createItemStack("MUSIC_DISC_MALL", 1, (short)0),
            BedWars.nms.createItemStack("MUSIC_DISC_MELLOHI", 1, (short)0),
            BedWars.nms.createItemStack("MUSIC_DISC_STAL", 1, (short)0),
            BedWars.nms.createItemStack("MUSIC_DISC_PIGSTEP", 1, (short)0),
            BedWars.nms.createItemStack("MUSIC_DISC_STRAD", 1, (short)0),
            BedWars.nms.createItemStack("MUSIC_DISC_WAIT", 1, (short)0),
            BedWars.nms.createItemStack("MUSIC_DISC_WARD", 1, (short)0),
    };

    public ModifyInventoryEvent(InvModifyType type, ModifyMode mode) {
        super("ModifyInventory_Dummy", EventType.MODIFY_INVENTORY);
        this.type = type;
        this.mode = mode;
    }

    @Override
    public void act(Player p, Arena arena) {
        switch (type) {
            case ADD:
                switch (mode) {
                    case ALL:
                        for(Player player : arena.getPlayers().stream().filter(x -> !arena.isSpectator(x))
                                .collect(Collectors.toList())) {
                            for(ItemStack item : items) {
                                int itemAmount = item.getAmount();
                                if(item.getType() == BedWars.nms.woolMaterial()) {
                                    item = BedWars.nms.colourItem(item, arena.getTeam(player));
                                } else if(item.getType() == BedWars.nms.woolMaterial() &&
                                        ((Wool)item.getData()).getColor() == DyeColor.BLACK) {
                                    item = BedWars.nms.colourItem(item, arena.getTeams().get(new Random().nextInt(arena.getTeams().size())));
                                } else if(item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Random Disc")) {
                                    item = DISCS[new Random().nextInt(DISCS.length)];
                                }
                                item.setAmount(itemAmount);
                                player.getInventory().addItem(item);
                            }
                        }
                        break;
                    case TEAM:
                        teamInvolved = arena.getTeams().get(new Random().nextInt(arena.getTeams().size()));
                        for(Player player : teamInvolved.getMembers().stream().filter(x -> !arena.isSpectator(x))
                                .collect(Collectors.toList())) {
                            for(ItemStack item : items) {
                                int itemAmount = item.getAmount();
                                if(item.getType() == BedWars.nms.woolMaterial()) {
                                    item = BedWars.nms.colourItem(item, arena.getTeam(player));
                                } else if(item.getType() == BedWars.nms.woolMaterial() &&
                                        ((Wool)item.getData()).getColor() == DyeColor.BLACK) {
                                    item = BedWars.nms.colourItem(item, arena.getTeams().get(new Random().nextInt(arena.getTeams().size())));
                                } else if(item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Random Disc")) {
                                    item = DISCS[new Random().nextInt(DISCS.length)];
                                }
                                item.setAmount(itemAmount);
                                player.getInventory().addItem(item);
                            }
                        }
                        break;
                }
                break;
            case SET:
                switch (mode) {
                    case ALL:
                        for(Player player : arena.getPlayers().stream().filter(x -> !arena.isSpectator(x))
                                .collect(Collectors.toList())) {
                            for(ItemStack item : items) {
                                int itemAmount = item.getAmount();
                                if(item.getType() == BedWars.nms.woolMaterial()) {
                                    item = BedWars.nms.colourItem(item, arena.getTeam(player));
                                } else if(item.getType() == BedWars.nms.woolMaterial() &&
                                        ((Wool)item.getData()).getColor() == DyeColor.BLACK) {
                                    item = BedWars.nms.colourItem(item, arena.getTeams().get(new Random().nextInt(arena.getTeams().size())));
                                } else if(item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Random Disc")) {
                                    item = DISCS[new Random().nextInt(DISCS.length)];
                                }
                                item.setAmount(itemAmount);
                                player.getInventory().setItem(slot++, item);
                            }
                        }
                        break;
                    case TEAM:
                        teamInvolved = arena.getTeams().get(new Random().nextInt(arena.getTeams().size()));
                        for(Player player : teamInvolved.getMembers().stream().filter(x -> !arena.isSpectator(x))
                                .collect(Collectors.toList())) {
                            for(ItemStack item : items) {
                                int itemAmount = item.getAmount();
                                if(item.getType() == BedWars.nms.woolMaterial()) {
                                    item = BedWars.nms.colourItem(item, arena.getTeam(player));
                                } else if(item.getType() == BedWars.nms.woolMaterial() &&
                                        ((Wool)item.getData()).getColor() == DyeColor.BLACK) {
                                    item = BedWars.nms.colourItem(item, arena.getTeams().get(new Random().nextInt(arena.getTeams().size())));
                                } else if(item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Random Disc")) {
                                    item = DISCS[new Random().nextInt(DISCS.length)];
                                }
                                item.setAmount(itemAmount);
                                player.getInventory().setItem(slot++, item);
                            }
                        }
                        break;
                }
                break;
            case REMOVE:
                switch (mode) {
                    case ALL:
                        for(Player player : arena.getPlayers()) {
                            for(ItemStack item : items) {
                                player.getInventory().remove(item);
                            }
                        }
                        break;
                    case TEAM:
                        teamInvolved = arena.getTeams().get(new Random().nextInt(arena.getTeams().size()));
                        for(Player player : teamInvolved.getMembers()) {
                            for(ItemStack item : items) {
                                player.getInventory().remove(item);
                            }
                        }
                        break;
                }
                break;
        }
    }

    @Override
    public void undo(Player p, Arena arena) {
        switch (type) {
            case ADD:
            case SET:
                switch (mode) {
                    case ALL:
                        for(Player player : arena.getPlayers()) {
                            for(ItemStack item : items) {
                                player.getInventory().remove(item);
                            }
                        }
                        break;
                    case TEAM:
                        for(Player player : teamInvolved.getMembers()) {
                            for(ItemStack item : items) {
                                player.getInventory().remove(item);
                            }
                        }
                        teamInvolved = null;
                        break;
                }
                break;
            case REMOVE:
                switch (mode) {
                    case ALL:
                        for(Player player : arena.getPlayers()) {
                            for(ItemStack item : items) {
                                int itemAmount = item.getAmount();
                                if(item.getType() == BedWars.nms.woolMaterial()) {
                                    item = BedWars.nms.colourItem(item, arena.getTeam(player));
                                } else if(item.getType() == BedWars.nms.woolMaterial() &&
                                        ((Wool)item.getData()).getColor() == DyeColor.BLACK) {
                                    item = BedWars.nms.colourItem(item, arena.getTeams().get(new Random().nextInt(arena.getTeams().size())));
                                } else if(item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Random Disc")) {
                                    item = DISCS[new Random().nextInt(DISCS.length)];
                                }
                                item.setAmount(itemAmount);
                                player.getInventory().addItem(item);
                            }
                        }
                        break;
                    case TEAM:
                        for(Player player : teamInvolved.getMembers()) {
                            for(ItemStack item : items) {
                                int itemAmount = item.getAmount();
                                if(item.getType() == BedWars.nms.woolMaterial()) {
                                    item = BedWars.nms.colourItem(item, arena.getTeam(player));
                                } else if(item.getType() == BedWars.nms.woolMaterial() &&
                                        ((Wool)item.getData()).getColor() == DyeColor.BLACK) {
                                    item = BedWars.nms.colourItem(item, arena.getTeams().get(new Random().nextInt(arena.getTeams().size())));
                                } else if(item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Random Disc")) {
                                    item = DISCS[new Random().nextInt(DISCS.length)];
                                }
                                item.setAmount(itemAmount);
                                player.getInventory().addItem(item);
                            }
                        }
                        teamInvolved = null;
                        break;
                }
                break;
        }
    }

    public InvModifyType getModifyType() {
        return type;
    }

    public ModifyMode getMode() {
        return mode;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public int getSlot() {
        return slot;
    }

    public void setModifyType(InvModifyType type) {
        this.type = type;
    }

    public void setMode(ModifyMode mode) {
        this.mode = mode;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}

