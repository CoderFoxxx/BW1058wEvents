package me.twintailedfoxxx.bedwarsevents.objects.impl;

import com.andrei1058.bedwars.arena.Arena;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.enums.EventType;
import org.bukkit.entity.Player;

public class AllowShopUsageToggleEvent extends BedwarsEvent {
    private boolean allowShopUsage;

    public AllowShopUsageToggleEvent() {
        super("AllowShopUsageToggleEvent_Dummy", EventType.TOGGLE_ALLOW_USE_SHOP);
    }

    @Override
    public void act(Player player, Arena arena) {
        arena.setAllowUseShopNpcs(allowShopUsage);
    }

    @Override
    public void undo(Player player, Arena arena) {
        arena.setAllowUseShopNpcs(!allowShopUsage);
    }

    public void setAllowShopUsage(boolean allowShopUsage) {
        this.allowShopUsage = allowShopUsage;
    }

    public boolean allowShopUsage() {
        return allowShopUsage;
    }
}
