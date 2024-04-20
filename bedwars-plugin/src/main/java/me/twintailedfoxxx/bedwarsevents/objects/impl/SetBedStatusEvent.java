package me.twintailedfoxxx.bedwarsevents.objects.impl;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.arena.Arena;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.enums.EventType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.List;

public class SetBedStatusEvent extends BedwarsEvent
{
    private boolean broken;
    private boolean allBeds = false;

    private List<ITeam> teamsWithBeds;
    private List<ITeam> teamsWithoutBeds;

    public SetBedStatusEvent() {
        super("SetBedStatusEvent_Dummy", EventType.SET_BED_STATUS);
    }

    @Override
    public void act(Player player, Arena arena) {
        teamsWithBeds = arena.getTeams().stream().filter(x -> !x.isBedDestroyed()).collect(Collectors.toList());
        teamsWithoutBeds = arena.getTeams().stream().filter(x -> x.isBedDestroyed() && x.getSize() > 0).collect(Collectors.toList());

        if(!allBeds) {
            if(broken) {
                teamsWithBeds.get(new Random().nextInt(teamsWithBeds.size())).setBedDestroyed(broken);
            } else {
                teamsWithoutBeds.get(new Random().nextInt(teamsWithBeds.size())).setBedDestroyed(broken);
            }
        } else {
            if(broken) {
                for(ITeam team : teamsWithBeds) {
                    team.setBedDestroyed(broken);
                }
            } else {
                for(ITeam team : teamsWithoutBeds) {
                    team.setBedDestroyed(broken);
                }
            }

        }
    }

    @Override
    public void undo(Player player, Arena arena) {
        if(!allBeds) {
            if(broken) {
                teamsWithoutBeds.get(new Random().nextInt(teamsWithBeds.size())).setBedDestroyed(broken);
            } else {
                teamsWithBeds.get(new Random().nextInt(teamsWithBeds.size())).setBedDestroyed(broken);
            }
        } else {
            if(broken) {
                for(ITeam team : teamsWithoutBeds) {
                    team.setBedDestroyed(broken);
                }
            } else {
                for(ITeam team : teamsWithBeds) {
                    team.setBedDestroyed(broken);
                }
            }

        }

        teamsWithBeds.clear();
        teamsWithoutBeds.clear();
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    public void setAllBeds(boolean allBeds) {
        this.allBeds = allBeds;
    }

    public boolean isBroken() {
        return broken;
    }

    public boolean isAllBeds() {
        return allBeds;
    }
}
