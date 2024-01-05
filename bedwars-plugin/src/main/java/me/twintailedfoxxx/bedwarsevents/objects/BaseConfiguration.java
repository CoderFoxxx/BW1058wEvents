package me.twintailedfoxxx.bedwarsevents.objects;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BaseConfiguration
{
    private final BedWars plugin = BedWars.plugin;
    private final File file;
    private final String name;
    private FileConfiguration config;

    public BaseConfiguration(String name) {
        this.name = name.toLowerCase();
        this.file = new File(plugin.getDataFolder(), this.name + ".yml");
    }

    public void load() {
        if(!file.exists()) {
            if(file.getParentFile().mkdirs()) {
                plugin.getLogger().info("Created events configuration");
                plugin.saveResource(name + ".yml", false);
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
