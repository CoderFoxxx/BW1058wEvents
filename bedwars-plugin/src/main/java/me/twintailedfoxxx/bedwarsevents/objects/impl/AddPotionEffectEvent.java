package me.twintailedfoxxx.bedwarsevents.objects.impl;

import com.andrei1058.bedwars.arena.Arena;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.enums.EventType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class AddPotionEffectEvent extends BedwarsEvent {
    private PotionEffect effect;

    public AddPotionEffectEvent() {
        super("AddPotionEffect_Dummy", EventType.ADD_POTION_EFFECT);
    }

    public PotionEffect getEffect() {
        return effect;
    }

    public void setEffect(PotionEffect effect) {
        this.effect = effect;
    }

    @Override
    public void act(Player p, Arena arena) {
        for(Player player : arena.getPlayers()) {
            player.addPotionEffect(effect);
        }
    }

    @Override
    public void undo(Player p, Arena arena) {}
}
