package me.twintailedfoxxx.bedwarsevents.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpawnEntityCommand extends BukkitCommand {
    public SpawnEntityCommand() {
        super("spawnentity");
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        if(commandSender instanceof ConsoleCommandSender) {
            // /spawnentity world entity x y z
            World world = Bukkit.getWorld(args[0]);
            if(world != null) {
                Location location;
                if(args.length == 3) {
                    Player player = Bukkit.getPlayer(args[2]);
                    location = player.getLocation();
                } else {
                    location = new Location(world, Double.parseDouble(args[2]), Double.parseDouble(args[3]),
                            Double.parseDouble(args[4]));
                }
                world.spawnEntity(location.add(0, 1, 0), EntityType.valueOf(args[1]));
            }
        }
        return false;
    }
}
