package com.andrei1058.bedwars.commands.bedwars;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.commands.bedwars.subcmds.regular.*;
import com.andrei1058.bedwars.commands.bedwars.subcmds.regular.CmdGUI;
import com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.Reload;
import com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup.AutoCreateTeams;
import com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup.CreateTeam;
import com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup.SetShop;
import com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup.SetWaitingSpawn;
import com.andrei1058.bedwars.language.Messages;
import com.andrei1058.bedwars.support.citizens.JoinNPC;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.language.Language.getMsg;

public class MainCommand extends BukkitCommand implements ParentCommand {

    /* SubCommands ArenaList */
    private static List<SubCommand> subCommandList = new ArrayList<>();
    /* MainCommand instance*/
    private static MainCommand instance;
    /* Dot char */
    public static char dot = 254;

    public MainCommand(String name) {
        super(name);
        setAliases(Arrays.asList("bedwars", "bedwars1058"));
        instance = this;
        new CmdJoin(this, "join");
        new CmdLeave(this, "leave");
        new CmdLang(this, "lang");
        new CmdTeleporter(this, "teleporter");
        if (getServerType() != ServerType.BUNGEE) {
            new CmdGUI(this, "gui");
        }
        new CmdStats(this, "stats");
        new CmdStart(this, "forceStart");
        new CmdStart(this, "start");
        if (Main.getServerType() != ServerType.BUNGEE) {
            new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.SetLobby(this, "setLobby"); //priority 1
        }
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.SetupArena(this, "setupArena"); //priority 2
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.ArenaList(this, "arenaList"); //priority 3
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.DelArena(this, "delArena"); //priority 4
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.EnableArena(this, "enableArena"); //priority 5
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.DisableArena(this, "disableArena"); //priority 6
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.CloneArena(this, "cloneArena"); //priority 7
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.ArenaGroup(this, "arenaGroup"); //priority 8
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.Build(this, "build"); //priority 9
        new Reload(this, "reload"); //priority 10
        new CmdList(this, "cmds"); //priority 20

        /* Arena setup commands (in world) */
        new AutoCreateTeams(this, "autoCreateTeams");
        new SetWaitingSpawn(this, "setWaitingSpawn");
        new CreateTeam(this, "createTeam");
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup.WaitingPos(this, "waitingPos");
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup.RemoveTeam(this, "removeTeam");
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup.SetMaxInTeam(this, "setMaxInTeam");
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup.SetSpawn(this, "setSpawn");
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup.SetBed(this, "setBed");
        new SetShop(this, "setShop");
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup.SetUpgrade(this, "setUpgrade");
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup.AddGenerator(this, "addGenerator");
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup.SetType(this, "setType");
        new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup.Save(this, "save");
        if (JoinNPC.isCitizensSupport() && Main.getServerType() != ServerType.BUNGEE) {
            new com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.NPC(this, "npc");
        }
    }

    @Override
    public boolean execute(CommandSender s, String st, String[] args) {

        if (args.length == 0) {
            /* Set op commands*/
            if (s.isOp() && !safeMode) {
                if (s instanceof Player) {
                    if (SetupSession.isInSetupSession((Player) s)) {
                        Bukkit.dispatchCommand(s, getName() + " cmds");
                    } else {
                        s.sendMessage("");
                        s.sendMessage("§8§l" + dot + " §6" + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " §7- §c Admin Commands");
                        s.sendMessage("");
                        sendSubCommands((Player) s);
                    }
                } else {
                    s.sendMessage("§f   bw safemode §eenable/ disable");
                }
            } else {
                if (s instanceof ConsoleCommandSender) {
                    s.sendMessage("§f   bw safemode §eenable/ disable");
                    return true;
                }
                /* Send player commands */
                Bukkit.dispatchCommand(s, mainCmd + " cmds");
            }
            return true;
        }

        boolean commandFound = false;
        for (SubCommand sb : getSubCommands()) {
            if (sb.getSubCommandName().equalsIgnoreCase(args[0])) {
                if (sb.hasPermission(s)) {
                    commandFound = sb.execute(Arrays.copyOfRange(args, 1, args.length), s);
                }
            }
        }

        if (!commandFound) {
            if (s instanceof Player) {
                s.sendMessage(getMsg((Player) s, Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS));
            } else {
                s.sendMessage(lang.m(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS));
            }
        }
        return true;
    }

    public static boolean isArenaGroup(String var) {
        if (config.getYml().get("arenaGroups") != null) {
            return config.getYml().getStringList("arenaGroups").contains(var);
        }
        return var.equalsIgnoreCase("default");
    }

    public static TextComponent createTC(String text, String suggest, String shot_text) {
        TextComponent tx = new TextComponent(text);
        tx.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggest));
        tx.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(shot_text).create()));
        return tx;
    }

    @Override
    public void addSubCommand(SubCommand subCommand) {
        subCommandList.add(subCommand);
    }

    @Override
    public void sendSubCommands(Player p) {
        for (int i = 0; i <= 20; i++) {
            for (SubCommand sb : getSubCommands()) {
                if (sb.getPriority() == i && sb.isShow() && sb.canSee(p)) {
                    p.spigot().sendMessage(sb.getDisplayInfo());
                }
            }
        }
    }

    public List<String> tabComplete(CommandSender s, String alias, String[] args, Location location) throws IllegalArgumentException {
        if (args.length == 1) {
            List<String> sub = new ArrayList<>();
            for (SubCommand sb : getSubCommands()) {
                if (sb.canSee(s)) sub.add(sb.getSubCommandName());
            }
            return sub;
        } else if (args.length == 2) {
            if (hasSubCommand(args[0])) {
                return getSubCommand(args[0]).getTabComplete();
            }
        }
        return null;
    }


    @Override
    public List<SubCommand> getSubCommands() {
        return subCommandList;
    }

    /**
     * Get instance
     */
    public static MainCommand getInstance() {
        return instance;
    }

    /**
     * Check if lobby location is set, else send a error message to the player
     */
    public static boolean isLobbySet(Player p) {
        if (Main.getServerType() == ServerType.BUNGEE) return true;
        if (config.getLobbyWorldName().isEmpty()) {
            if (p != null) {
                p.sendMessage("§c▪ §7You have to set the lobby location first!");
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean hasSubCommand(String name) {
        for (SubCommand sc : getSubCommands()) {
            if (sc.getSubCommandName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get sub-command by name
     */
    public SubCommand getSubCommand(String name) {
        for (SubCommand sc : getSubCommands()) {
            if (sc.getSubCommandName().equalsIgnoreCase(name)) {
                return sc;
            }
        }
        return null;
    }

    /** Get sub-command by name */


    /**
     * Get a dot symbol
     */
    public static char getDot() {
        return dot;
    }
}