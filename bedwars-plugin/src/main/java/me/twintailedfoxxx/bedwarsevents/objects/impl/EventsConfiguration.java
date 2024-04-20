package me.twintailedfoxxx.bedwarsevents.objects.impl;

import com.andrei1058.bedwars.BedWars;
import me.twintailedfoxxx.bedwarsevents.objects.BaseConfiguration;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.enums.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                case MODIFY_INVENTORY:
                    String modType = getConfig().getString("Events." + name + ".ModifyType");
                    String modMode = getConfig().getString("Events." + name + ".Mode");
                    ModifyInventoryEvent modifyInventoryEvent = new ModifyInventoryEvent(InvModifyType.valueOf(modType),
                            ModifyMode.valueOf(modMode));
                    setBaseParams(name, modifyInventoryEvent);

                    if(modifyInventoryEvent.getModifyType() == InvModifyType.SET) {
                        modifyInventoryEvent.setSlot(getConfig().getInt("Events." + name + ".Slot"));
                    }

                    List<ItemStack> items = new ArrayList<>();
                    for(String key : getConfig().getConfigurationSection("Events." + name + ".NewItems").getKeys(false)) {
                        ItemStack newItem = parseItem(name + ".NewItems." + key);
                        items.add(newItem);
                    }

                    modifyInventoryEvent.setItems(items.toArray(new ItemStack[0]));
                    result = modifyInventoryEvent;
                    break;
                case PARAM_CHANGE:
                case SPAWN_ENTITY:
                    String entityType = getConfig().getString("Events." + name + ".Entity");
                    String spawnLoc = getConfig().getString("Events." + name + ".Location");
                    int count = getConfig().getInt("Events." + name + ".Count");
                    SpawnEntityEvent spawnEntityEvent = new SpawnEntityEvent(EntityType.valueOf(entityType),
                            SpawnLocation.valueOf(spawnLoc), count);
                    setBaseParams(name, spawnEntityEvent);

                    spawnEntityEvent.setBaby(getConfig().getBoolean("Events." + name + ".Baby"));
                    spawnEntityEvent.setTamed(getConfig().getBoolean("Events." + name + ".Tamed"));
                    result = spawnEntityEvent;
                    break;
                case ADD_POTION_EFFECT:
                    AddPotionEffectEvent addPotionEffectEvent = new AddPotionEffectEvent();
                    setBaseParams(name, addPotionEffectEvent);

                    PotionEffect effect = new PotionEffect(PotionEffectType.getByName(getConfig().getString("Events." + name + ".PotionEffect.Type")),
                            getConfig().getInt("Events." + name + ".PotionEffect.Duration") * 20,
                            getConfig().getInt("Events." + name + ".PotionEffect.Amplifier") - 1);
                    addPotionEffectEvent.setEffect(effect);
                    result = addPotionEffectEvent;
                    break;
                case CHANGE_PLACED_BLOCKS:
                    ChangePlacedBlocksEvent changePlacedBlocksEvent = new ChangePlacedBlocksEvent();
                    setBaseParams(name, changePlacedBlocksEvent);

                    Material newBlockMaterial = Material.getMaterial(getConfig().getString("Events." + name + ".NewBlockMaterial"));
                    changePlacedBlocksEvent.setNewBlockMaterial(newBlockMaterial);
                    result = changePlacedBlocksEvent;
                    break;
                case CHANGE_DEFENCE_OBJECT:
                    break;
                case CHANGE_SHOP_MULTIPLIERS:
                    String mulType = getConfig().getString("Events." + name + ".MultiplierType").toUpperCase();
                    ChangeShopMultipliersEvent multipliersEvent = new ChangeShopMultipliersEvent(MultiplierType.valueOf(mulType));
                    setBaseParams(name, multipliersEvent);

                    multipliersEvent.setMultiplier(getConfig().getDouble("Events." + name + ".Multiplier"));
                    result = multipliersEvent;
                    break;
                case RAIN:
                    String rainType = getConfig().getString("Events." + name + ".RainType").toUpperCase();
                    RainEvent rainEvent = new RainEvent(RainType.valueOf(rainType));
                    setBaseParams(name, rainEvent);

                    switch(rainEvent.getRainType()) {
                        case ITEMS:
                            items = new ArrayList<>();
                            for(String key : getConfig().getConfigurationSection("Events." + name + ".Items").getKeys(false)) {
                                ItemStack item = parseItem(name + ".Items." + key);
                                items.add(item);
                            }
                            rainEvent.setItems(items.toArray(new ItemStack[0]));
                            break;
                        case ENTITIES:
                            rainEvent.setEntity(EntityType.valueOf(getConfig().getString("Events." + name + ".Entity")));
                            break;
                    }

                    rainEvent.setRadius(getConfig().getInt("Events." + name + ".Radius"));
                    if(getConfig().getInt("Events." + name + ".Count") != 0) {
                        rainEvent.setCount(getConfig().getInt("Events." + name + ".Count"));
                    }
                    result = rainEvent;
                    break;
                case CHANGE_PLAYER_ATTRIBUTES:
                    String attr = getConfig().getString("Events." + name + ".Attribute");
                    ChangePlayerAttributesEvent changePlayerAttributesEvent =
                        new ChangePlayerAttributesEvent(PlayerAttributes.valueOf(attr));
                    setBaseParams(name, changePlayerAttributesEvent);

                    switch (changePlayerAttributesEvent.getAttribute()) {
                        case CURRENT_HEALTH:
                            changePlayerAttributesEvent.setNewHealth(getConfig().getDouble("Events." + name + ".Value"));
                            break;
                        case MAX_HEALTH:
                            changePlayerAttributesEvent.setNewMaxHealth(getConfig().getDouble("Events." + name + ".Value"));
                            break;
                    }
                    result = changePlayerAttributesEvent;
                    break;
                case VELOCITY_MANIPULATION:
                    VelocityManipulationEvent velocityManipulationEvent = new VelocityManipulationEvent();
                    setBaseParams(name, velocityManipulationEvent);

                    velocityManipulationEvent.setConditional(getConfig().getConfigurationSection("Events." + name +
                            ".Condition") != null);
                    if(velocityManipulationEvent.isConditional()) {
                        velocityManipulationEvent.setConditionalVector(new Vector(
                                getConfig().getInt("Events." + name + ".Condition.X"),
                                getConfig().getInt("Events." + name + ".Condition.Y"),
                                getConfig().getInt("Events." + name + ".Condition.Z")
                        ));
                    }
                    velocityManipulationEvent.setVector(new Vector(
                            getConfig().getInt("Events." + name + ".Vector.X"),
                            getConfig().getInt("Events." + name + ".Vector.Y"),
                            getConfig().getInt("Events." + name + ".Vector.Z")
                    ));
                    result = velocityManipulationEvent;
                    break;
                case EXECUTE_COMMANDS:
                    ExecuteCommandsEvent event = new ExecuteCommandsEvent();
                    setBaseParams(name, event);

                    event.setCommands(getConfig().getStringList("Events." + name + ".Commands"));
                    event.setUndoCommands(getConfig().getStringList("Events." + name + ".UndoCommands"));
                    event.setMode(ModifyMode.valueOf(getConfig().getString("Events." + name + ".Mode")));

                    result = event;
                    break;
                case SET_BED_STATUS:
                    SetBedStatusEvent setBedStatusEvent = new SetBedStatusEvent();
                    setBaseParams(name, setBedStatusEvent);

                    setBedStatusEvent.setAllBeds(getConfig().getBoolean("Events." + name + ".AllBeds"));
                    setBedStatusEvent.setBroken(getConfig().getBoolean("Events." + name + ".Broken"));
                    result = setBedStatusEvent;
                    break;
                case CHANGE_EMERALD_GENERATOR_MULTIPLIER:
                    EmeraldGeneratorMultiplierEvent emeraldGeneratorMultiplierEvent = new EmeraldGeneratorMultiplierEvent();
                    setBaseParams(name, emeraldGeneratorMultiplierEvent);

                    emeraldGeneratorMultiplierEvent.setMultiplier(getConfig().getInt("Events." + name + ".Multiplier"));
                    result = emeraldGeneratorMultiplierEvent;
                    break;
                case TOGGLE_ALLOW_USE_SHOP:
                    AllowShopUsageToggleEvent allowShopUsageToggleEvent = new AllowShopUsageToggleEvent();
                    setBaseParams(name, allowShopUsageToggleEvent);

                    allowShopUsageToggleEvent.setAllowShopUsage(getConfig().getBoolean("Events." + name + ".AllowShop"));
                    result = allowShopUsageToggleEvent;
                    break;
                case SCAFFOLD:
                    ScaffoldEvent scaffoldEvent = new ScaffoldEvent();
                    setBaseParams(name, scaffoldEvent);

                    result = scaffoldEvent;
                    break;
                case INVISIBILITY:
                    InvisibilityEvent invisibilityEvent = new InvisibilityEvent();
                    setBaseParams(name, invisibilityEvent);

                    result = invisibilityEvent;
                    break;
                case TOGGLE_GENERATOR:
                    ToggleGeneratorEvent toggleGeneratorEvent = new ToggleGeneratorEvent();
                    setBaseParams(name, toggleGeneratorEvent);

                    result = toggleGeneratorEvent;
                    break;
                default:
                    break;
            }
        }

        return result;
    }

    private ItemStack parseItem(String path) {
        ItemStack item;

        if(getConfig().getString("Events." + path + ".Material").equalsIgnoreCase("RANDOM_TEAM_WOOL")) {
            item = BedWars.nms.createItemStack("BLACK_WOOL", 1, (short)0);
        } else {
            item = BedWars.nms.createItemStack(getConfig().getString("Events." + path + ".Material"), 1, (short) 0);
        }

        if(getConfig().getInt("Events." + path + ".Quantity") != 0) {
            item.setAmount(getConfig().getInt("Events." + path + ".Quantity"));
        }
        if(getConfig().getConfigurationSection("Events." + path + ".Meta") != null) {
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                    getConfig().getString("Events." + path + ".Meta.DisplayName")));
            List<String> lore = getConfig().getStringList("Events." + path + ".Meta.Lore");
            lore.replaceAll(textToTranslate -> ChatColor.translateAlternateColorCodes('&', textToTranslate));
            meta.setLore(lore);
            List<String> enchantments = getConfig().getStringList("Events." + path + ".Meta.Enchantments");
            enchantments.forEach(x -> {
                String[] split = x.split(":");
                meta.addEnchant(Enchantment.getByName(split[0]), Integer.parseInt(split[1]) + 1, true);
            });
            item.setItemMeta(meta);
        }

        return item;
    }

    public List<BedwarsEvent> getEvents() {
        List<BedwarsEvent> events = new ArrayList<>();
        for(String key : getConfig().getConfigurationSection("Events").getKeys(false)) {
            if(!Objects.equals(key, "Delay")) {
                events.add(getEvent(key));
            }
        }
        BedWars.plugin.getLogger().info("Loaded " + events.size() + " events.");
        return events;
    }

    public int getEventDelay() {
        return getConfig().getInt("Events.Delay");
    }

    public String getMessage(String id) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages." + id));
    }

    private void setBaseParams(String eventName, BedwarsEvent event) {
        event.setName(eventName);
        event.setDisplayName(getConfig().getString("Events." + eventName + ".DisplayName"));
        event.setDescription(getConfig().getString("Events." + eventName + ".Description"));
        event.setWeight(getConfig().getInt("Events." + eventName + ".Weight"));
        if(getConfig().get("Events." + eventName + ".ActIndividually") != null) {
            event.setActIndividually(getConfig().getBoolean("Events." + eventName + ".ActIndividually"));
        }
        if(getConfig().getInt("Events." + eventName + ".Duration") != 0) {
            event.setDuration(getConfig().getInt("Events." + eventName + ".Duration"));
        }
    }
}
