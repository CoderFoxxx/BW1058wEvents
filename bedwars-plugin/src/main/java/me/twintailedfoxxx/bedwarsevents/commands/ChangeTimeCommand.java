package me.twintailedfoxxx.bedwarsevents.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class ChangeTimeCommand extends BukkitCommand {

    public ChangeTimeCommand() {
        super("settime");
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        if(commandSender instanceof ConsoleCommandSender) {
            // /settime world int
            World world = Bukkit.getWorld(args[0]);
            if(world != null) {
                world.setTime(Long.parseLong(args[1]));
            }
        }
        return false;
    }
}
