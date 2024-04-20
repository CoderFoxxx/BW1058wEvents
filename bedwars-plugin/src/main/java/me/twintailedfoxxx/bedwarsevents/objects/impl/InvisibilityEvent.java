package me.twintailedfoxxx.bedwarsevents.objects.impl;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.sidebar.SidebarService;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.enums.EventType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static com.andrei1058.bedwars.BedWars.nms;

public class InvisibilityEvent extends BedwarsEvent {

    public InvisibilityEvent() {
        super("InvisibilityEvent_Dummy", EventType.INVISIBILITY);
    }

    @Override
    public void act(Player player, Arena arena) {}

    @Override
    public void undo(Player player, Arena arena) {
        for(Player p : arena.getPlayers()) {
            if(!arena.isSpectator(p)) {
                if(arena.getShowTime().containsKey(p) || p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    toggleInvisibility(arena, p, new PotionEffect(PotionEffectType.INVISIBILITY, 1, 1), true);
                }
            }
        }

    }

    public static void toggleInvisibility(Arena a, Player player, PotionEffect effect, boolean toggle) {
        ITeam t = a.getTeam(player);
        if(!toggle) {
            a.getShowTime().remove(player);
            for(Player p : player.getWorld().getPlayers()) {
                if(a.isSpectator(p)) {
                    nms.showArmor(player, p);
                } else if(t != a.getTeam(p)) {
                    nms.showArmor(player, p);
                }
            }

            if(player.hasPotionEffect(effect.getType())) {
                player.removePotionEffect(effect.getType());
            }
        } else {
            player.addPotionEffect(effect);
            a.getShowTime().put(player, effect.getDuration() / 20);
            for (Player p1 : player.getWorld().getPlayers()) {
                if (a.isSpectator(p1)) {
                    // hide player armor to spectators
                    nms.hideArmor(player, p1);
                } else if (t != a.getTeam(p1)) {
                    // hide player armor to other teams
                    nms.hideArmor(player, p1);
                }
            }
        }

        SidebarService.getInstance().handleInvisibility(t, player, toggle);
    }
}
