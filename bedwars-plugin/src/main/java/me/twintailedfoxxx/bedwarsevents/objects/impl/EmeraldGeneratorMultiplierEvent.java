package me.twintailedfoxxx.bedwarsevents.objects.impl;

import com.andrei1058.bedwars.arena.Arena;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.enums.EventType;
import org.bukkit.entity.Player;

public class EmeraldGeneratorMultiplierEvent extends BedwarsEvent {
    private int multiplier;

    public EmeraldGeneratorMultiplierEvent() {
        super("EmeraldGeneratorMultiplierEvent_Dummy", EventType.CHANGE_EMERALD_GENERATOR_MULTIPLIER);
    }

    @Override
    public void act(Player player, Arena arena) {
        arena.setEmeraldsDroppedMultiplier(multiplier);
    }

    @Override
    public void undo(Player player, Arena arena) {
        arena.setEmeraldsDroppedMultiplier(1);
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
