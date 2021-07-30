package com.MeneerPinguin.MCMMORPG.entity.behaviour;

import com.MeneerPinguin.MCMMORPG.common.log.LocationMath;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.List;

public class DefaultAggression extends BaseBehaviour {
    private double dx, dy, dz;
    private double speed;

    private Entity targeting;

    public DefaultAggression(double dx, double dy, double dz, double speed) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        this.speed = speed;

        this.targeting = null;
    }

    @Override
    public Location tick(Location location) {
        List<Entity> entities = LocationMath.getNearbyEntities(location, dx, dy, dz);

        if(this.targeting != null){
            if(!entities.contains(targeting)){
                targeting = null;
            }
        }

        if(this.targeting == null && entities.size() > 0){
            this.targeting = entities.get(0);
        }

        if(this.targeting != null){
            return location.clone().add(LocationMath.moveTowards(location, this.targeting.getLocation(), this.speed));
        }else{
            return location;
        }
    }
}
