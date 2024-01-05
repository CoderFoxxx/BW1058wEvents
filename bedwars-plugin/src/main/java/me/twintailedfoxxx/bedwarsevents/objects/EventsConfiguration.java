package me.twintailedfoxxx.bedwarsevents.objects;

import me.twintailedfoxxx.bedwarsevents.objects.base.SetItemEvent;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EventsConfiguration extends BaseConfiguration {
    public EventsConfiguration() {
        super("events");
        load();
    }

    public BedwarsEvent getEvent(String name) {
        BedwarsEvent result = null;
        if(getConfig().getConfigurationSection("Events." + name) != null) {
            EventType type = EventType.valueOf(getConfig().getString("Events." + name + ".Type").toUpperCase());
            switch (type) {
                case SET_ITEM:
                    SetItemEvent setItemEvent = new SetItemEvent();
                    setItemEvent.setName(name);
                    setItemEvent.setDisplayName(getConfig().getString("Events." + name + ".DisplayName"));
                    setItemEvent.setChance(getConfig().getDouble("Events." + name + ".Chance"));
                    ItemStack newItem = new ItemStack(Material.valueOf("Events." + name + ".NewItem.Material"));
                    if(getConfig().getInt("Events." + name + ".NewItem.Quantity") != 0) {
                        newItem.setAmount(getConfig().getInt("Events." + name + ".NewItem.Quantity"));
                    }
                    if(getConfig().getConfigurationSection("Events." + name + ".NewItem.Meta") != null) {
                        ItemMeta meta = newItem.getItemMeta();
                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                                getConfig().getString("Events." + name + ".NewItem.Meta.DisplayName")));
                        List<String> lore = getConfig().getStringList("Events." + name + ".NewItem.Meta.Lore");
                        lore.replaceAll(textToTranslate -> ChatColor.translateAlternateColorCodes('&', textToTranslate));
                        meta.setLore(lore);
                        List<String> enchantments = getConfig().getStringList("Events." + name + ".NewItem.Meta.Enchantments");
                        enchantments.forEach(x -> {
                            String[] split = x.split(":");
                            meta.addEnchant(Enchantment.getByName(split[0]), Integer.parseInt(split[1]), true);
                        });
                        newItem.setItemMeta(meta);
                    }
                    setItemEvent.setNewItem(newItem);
                    setItemEvent.setSlot(getConfig().getInt("Events." + name + ".Slot"));
                    result = setItemEvent;
                    break;
                case PARAM_CHANGE:
                    break;
                case SPAWN_ENTITY:

                case ADD_POTION_EFFECT:
                case CHANGE_PLACED_BLOCKS:
                case CHANGE_DEFENCE_OBJECT:
                    break;
            }
        }

        return result;
    }

    public List<BedwarsEvent> getEvents() {
        throw new NotImplementedException();
    }

    public int getEventDelay() {
        return getConfig().getInt("Events.Delay");
    }
}
