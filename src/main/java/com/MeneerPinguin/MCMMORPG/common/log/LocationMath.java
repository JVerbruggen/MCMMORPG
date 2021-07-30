package com.MeneerPinguin.MCMMORPG.common.log;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LocationMath {

    /**
     * ets nearby entities to a location using 1 distance dimension.
     * @param location
     * @param d
     * @return
     */
    public static List<Entity> getNearbyEntities(Location location, double d){
        return getNearbyEntities(location, d, d, d);
    }

    /**
     * Gets nearby entities to a location using 3 distance dimensions.
     * @param location
     * @param dx
     * @param dy
     * @param dz
     * @return
     */
    public static List<Entity> getNearbyEntities(Location location, double dx, double dy, double dz){
        World world = location.getWorld();
        if(world == null) return new ArrayList<>();

        Collection<Entity> nearbyEntities = world.getNearbyEntities(location, dx, dy, dz);
        return new ArrayList<>(nearbyEntities);
    }

    /**
     * Get a vector that specifies moving towards a location at a certain speed.
     * @param origin
     * @param towards
     * @param speed
     * @return
     */
    public static Vector moveTowards(Location origin, Location towards, double speed){
        Vector originVector = origin.toVector();
        Vector towardsVector = towards.toVector();

        Vector bridge = towardsVector.subtract(originVector);

        return bridge.multiply((speed/10)/bridge.length());
    }

    /**
     * Predict where a moving object will be taking their velocity into account
     * @param location
     * @param velocity
     * @return
     */
    public static Location getLocationPredictedOverTicks(Location location, Vector velocity){
        return getLocationPredictedOverTicks(location, velocity, 1);
    }

    /**
     * Predict where a moving object will be taking their velocity into account over an amount of ticks
     * @param location
     * @param velocity
     * @param ticks
     * @return
     */
    public static Location getLocationPredictedOverTicks(Location location, Vector velocity, int ticks){
        return location.add(velocity.clone().multiply(ticks));
    }

}
