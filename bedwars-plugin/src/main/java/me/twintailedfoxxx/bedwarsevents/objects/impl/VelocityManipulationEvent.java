package me.twintailedfoxxx.bedwarsevents.objects.impl;

import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.listeners.DamageDeathMove;
import me.twintailedfoxxx.bedwarsevents.objects.BedwarsEvent;
import me.twintailedfoxxx.bedwarsevents.objects.enums.EventType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class VelocityManipulationEvent extends BedwarsEvent {
    private Vector vector;
    private boolean conditional = false;
    private Vector conditionalVector;

    public VelocityManipulationEvent() {
        super("VelocityManipulationEvent_Dummy", EventType.VELOCITY_MANIPULATION);
    }

    @Override
    public void act(Player p, Arena arena) {
        if(!conditional) {
            p.setVelocity(vector);
        }
    }

    @Override
    public void undo(Player p, Arena arena) {
        p.setAllowFlight(false);
        p.setFlying(false);
    }

    public boolean isConditional() {
        return conditional;
    }

    public void setConditional(boolean conditional) {
        this.conditional = conditional;
    }

    public Vector getConditionalVector() {
        return conditionalVector;
    }

    public void setConditionalVector(Vector conditionalVector) {
        this.conditionalVector = conditionalVector;
    }

    public Vector getVector() {
        return vector;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
    }
}
