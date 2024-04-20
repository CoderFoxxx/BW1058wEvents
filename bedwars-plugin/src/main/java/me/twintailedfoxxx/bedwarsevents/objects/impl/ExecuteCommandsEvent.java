package me.twintailedfoxxx.bedwarsevents.objects.impl;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.arena.Arena;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.enums.EventType;
import me.twintailedfoxxx.bedwarsevents.objects.enums.ModifyMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExecuteCommandsEvent extends BedwarsEvent {
    private ModifyMode mode;
    private int delay;
    private List<String> commands = new ArrayList<>();
    private List<String> undoCommands = new ArrayList<>();

    public ExecuteCommandsEvent() {
        super(" ExecuteCommandsEvent_Dummy", EventType.EXECUTE_COMMANDS);
    }

    @Override
    public void act(Player player, Arena arena) {
        switch (mode) {
            case ALL:
                for(String command : commands) {
                    String[] spl;
                    if(command.contains(";")) {
                        spl = command.split(";");
                        delay = Integer.parseInt(spl[0]);
                        command = spl[1];
                    }

                    if(command.contains("{player}")) {
                        for(Player pl : arena.getPlayers()) {
                            if(!arena.isSpectator(pl)) {
                                Location bedLoc = arena.getTeam(pl).getBed().clone().add(0, 4, 0);
                                String cmd = ChatColor.translateAlternateColorCodes('&',
                                        command.replace("{player}", pl.getName())
                                                .replace("{bedSpawn}", "" + bedLoc.getX() + " " + bedLoc.getY() + " " + bedLoc.getZ())
                                                .replace("{location}", "" + pl.getLocation().getX()
                                                        + " " + pl.getLocation().getY() + " " + pl.getLocation().getZ()));
                                if(delay != 0) {
                                    Bukkit.getScheduler().runTaskLater(BedWars.plugin, () ->
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd),
                                            20L * delay);
                                } else {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                                }

                            }
                        }
                    } else {
                        if(command.contains("{bedSpawn}")) {
                            for(ITeam team : arena.getTeams()) {
                                if(team.getBed() == null || team.getSize() < 1) {
                                    continue;
                                }

                                command = command.replace("{bedSpawn}", "" + team.getBed().getX()
                                        + " " + team.getBed().getY() + 4 + " " + team.getBed().getZ());
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                        ChatColor.translateAlternateColorCodes('&', command.replace("{world}", arena.getWorldName())));
                            }
                        } else {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                    ChatColor.translateAlternateColorCodes('&', command.replace("{world}", arena.getWorldName())));
                        }

                    }
                }

                break;
            case TEAM:
                for(String command : commands) {
                    if(command.contains("{player}")) {
                        for(Player pl : arena.getTeams().get(new Random().nextInt(arena.getTeams().size())).getMembers()) {
                            if(!arena.isSpectator(pl)) {
                                String cmd = ChatColor.translateAlternateColorCodes('&',
                                        command.replace("{player}", pl.getName())
                                                .replace("{location}", "" + pl.getLocation().getX()
                                                        + " " + pl.getLocation().getY() + " " + pl.getLocation().getZ()));
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                            }
                        }
                    } else {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                ChatColor.translateAlternateColorCodes('&', command.replace("{world}", arena.getWorldName())));
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void undo(Player player, Arena arena) {
        if(undoCommands != null && !undoCommands.isEmpty()) {
            switch (mode) {
                case ALL:
                    for(String command : undoCommands) {
                        if(command.contains("{player}")) {
                            for(Player pl : arena.getPlayers()) {
                                if(!arena.isSpectator(pl)) {
                                    Location bedLoc = arena.getTeam(pl).getBed().clone().add(0, 4, 0);
                                    String cmd = ChatColor.translateAlternateColorCodes('&',
                                            command.replace("{player}", pl.getName())
                                                    .replace("{bedSpawn}", "" + bedLoc.getX() + " " + bedLoc.getY() + " " + bedLoc.getZ())
                                                    .replace("{location}", "" + pl.getLocation().getX()
                                                            + " " + pl.getLocation().getY() + " " + pl.getLocation().getZ()));
                                    if(delay != 0) {
                                        Bukkit.getScheduler().runTaskLater(BedWars.plugin, () ->
                                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd),
                                                20L * delay);
                                    } else {
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                                    }

                                }
                            }
                        }
                    }
                    break;
                case TEAM:
                    for(String command : undoCommands) {
                        if (command.contains("{player}")) {
                            for (Player pl : arena.getTeams().get(new Random().nextInt(arena.getTeams().size())).getMembers()) {
                                if (!arena.isSpectator(pl)) {
                                    String cmd = ChatColor.translateAlternateColorCodes('&',
                                            command.replace("{player}", pl.getName())
                                                    .replace("{location}", "" + pl.getLocation().getX()
                                                            + " " + pl.getLocation().getY() + " " + pl.getLocation().getZ()));
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                                }
                            }
                        } else {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                    ChatColor.translateAlternateColorCodes('&', command.replace("{world}", arena.getWorldName())));
                        }
                    }
                    break;
            }
        }
    }

    public ModifyMode getMode() {
        return mode;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setMode(ModifyMode mode) {
        this.mode = mode;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public void setUndoCommands(List<String> undoCommands) {
        this.undoCommands = undoCommands;
    }
}
