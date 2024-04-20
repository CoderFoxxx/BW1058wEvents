package me.twintailedfoxxx.bedwarsevents.objects.impl;

import com.andrei1058.bedwars.arena.Arena;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.enums.EventType;
import me.twintailedfoxxx.bedwarsevents.objects.enums.PlayerAttributes;
import org.bukkit.entity.Player;

public class ChangePlayerAttributesEvent extends BedwarsEvent {
    private PlayerAttributes attribute;
    private double newMaxHealth;
    private double newHealth;

    public ChangePlayerAttributesEvent(PlayerAttributes attribute) {
        super("ChangePlayerAttributesEvent_Dummy", EventType.CHANGE_PLAYER_ATTRIBUTES);
        this.attribute = attribute;
    }

    @Override
    public void act(Player p, Arena arena) {
        switch (attribute) {
            case MAX_HEALTH:
                for(Player player : arena.getPlayers()){
                    player.setMaxHealth(newMaxHealth);
                    player.setHealthScale(newMaxHealth);
                    player.setHealth(newMaxHealth);
                }
                break;
            case CURRENT_HEALTH:
                for(Player player : arena.getPlayers()) {
                    player.setHealth(newHealth);
                }
                break;
            case FLIGHT:
                for(Player player : arena.getPlayers()) {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                }
                break;
        }
    }

    @Override
    public void undo(Player p, Arena arena) {
        switch (attribute) {
            case MAX_HEALTH:
                for(Player player : arena.getPlayers()){
                    player.setMaxHealth(20);
                    player.setHealthScale(20);
                    player.setHealth(20);
                }
                break;
            case CURRENT_HEALTH:
                break;
            case FLIGHT:
                for(Player player : arena.getPlayers()) {
                    player.setAllowFlight(false);
                    player.setFlying(false);
                }
                break;
        }
    }

    public PlayerAttributes getAttribute() {
        return attribute;
    }

    public double getNewHealth() {
        return newHealth;
    }

    public double getNewMaxHealth() {
        return newMaxHealth;
    }

    public void setAttribute(PlayerAttributes attributes) {
        this.attribute = attributes;
    }

    public void setNewHealth(double newHealth) {
        this.newHealth = newHealth;
    }

    public void setNewMaxHealth(double newMaxHealth) {
        this.newMaxHealth = newMaxHealth;
    }
}
