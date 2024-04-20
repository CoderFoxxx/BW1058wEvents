package me.twintailedfoxxx.bedwarsevents.objects.impl;

import com.andrei1058.bedwars.arena.Arena;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.enums.EventType;
import org.bukkit.entity.Player;

public class ToggleGeneratorEvent extends BedwarsEvent {
    public ToggleGeneratorEvent() {
        super("ToggleGeneratorEvent_Dummy", EventType.TOGGLE_GENERATOR);
    }

    @Override
    public void act(Player player, Arena arena) {
        arena.setTeamGeneratorEnabled(false);
    }

    @Override
    public void undo(Player player, Arena arena) {
        arena.setTeamGeneratorEnabled(true);
    }
}
