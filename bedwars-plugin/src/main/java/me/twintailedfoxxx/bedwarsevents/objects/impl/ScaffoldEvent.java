package me.twintailedfoxxx.bedwarsevents.objects.impl;

import com.andrei1058.bedwars.arena.Arena;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.enums.EventType;
import org.bukkit.entity.Player;

public class ScaffoldEvent extends BedwarsEvent
{
    public ScaffoldEvent() {
        super("ScaffoldEvent_Dummy", EventType.SCAFFOLD);
    }

    @Override
    public void act(Player player, Arena arena) {
    }

    @Override
    public void undo(Player player, Arena arena) {
    }
}
