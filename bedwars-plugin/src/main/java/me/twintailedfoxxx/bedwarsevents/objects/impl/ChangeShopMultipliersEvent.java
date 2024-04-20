package me.twintailedfoxxx.bedwarsevents.objects.impl;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.arena.Arena;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.enums.EventType;
import me.twintailedfoxxx.bedwarsevents.objects.enums.MultiplierType;
import org.bukkit.entity.Player;

public class ChangeShopMultipliersEvent extends BedwarsEvent {
    private double multiplier = 1;
    private MultiplierType type;

    public ChangeShopMultipliersEvent(MultiplierType type) {
        super("ChangeShopMulsEvent_Dummy", EventType.CHANGE_SHOP_MULTIPLIERS);
        this.type = type;
    }

    @Override
    public void act(Player p, Arena arena) {
        switch (type) {
            case COST:
                arena.setVillagerCostMultiplier(multiplier);
                BedWars.plugin.getLogger().info("costmul=" + arena.getVillagerCostMultiplier());
                break;
            case QUANTITY:
                arena.setSoldItemsMultiplier(Math.toIntExact(Math.round(multiplier)));
                break;
        }
    }

    @Override
    public void undo(Player p, Arena arena) {
        switch (type) {
            case COST:
                arena.setVillagerCostMultiplier(1);
                BedWars.plugin.getLogger().info("costmul=" + arena.getVillagerCostMultiplier());
                break;
            case QUANTITY:
                //BuyItem.setItemCountMultiplier(1);
                arena.setSoldItemsMultiplier(1);
                break;
        }
    }

    public double getMultiplier() {
        return multiplier;
    }

    public MultiplierType getMultiplierType() {
        return type;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public void getMultiplierType(MultiplierType type) {
        this.type = type;
    }
}
