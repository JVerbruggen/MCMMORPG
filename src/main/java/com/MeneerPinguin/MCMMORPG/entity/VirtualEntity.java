package com.MeneerPinguin.MCMMORPG.entity;

import com.MeneerPinguin.MCMMORPG.common.log.LocationMath;
import com.MeneerPinguin.MCMMORPG.entity.behaviour.BaseBehaviour;
import com.MeneerPinguin.MCMMORPG.packets.PacketSender;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VirtualEntity {
    protected List<Player> viewers;

    protected Sound damageSound = Sound.ENTITY_ZOMBIE_HURT;

    protected Location previousLocation;
    protected Location location;
    protected int chunkDrawDistance;
    protected int maxHealth;
    protected int health;
    protected int entityId;

    protected boolean destroyed = false;
    protected boolean alive = true;
    protected boolean chunkloaded = false;

    private int lookForNewViewersCounter = 0;

    protected BaseBehaviour behaviour;

    public VirtualEntity(Location location, int maxHealth, BaseBehaviour behaviour){
        this.location = this.previousLocation = location;
        this.chunkDrawDistance = 2;
        this.maxHealth = this.health = maxHealth;
        this.behaviour = behaviour;

        this.viewers = new ArrayList<>();
    }

    public void tick(){
        this.location = behaviour.tick(this.location);

        if(lookForNewViewersCounter >= 20){
            lookForNewViewersCounter = 0;

            setViewers(LocationMath.getNearbyPlayers(location, 2));
            Bukkit.broadcastMessage("viewers:" + viewers.size());
            Bukkit.broadcastMessage("loc:" + location);
        }else{
            lookForNewViewersCounter++;
        }


        if(viewers.size() > 0){
            Vector v = location.toVector().subtract(previousLocation.toVector());
            PacketSender.moveLook(viewers, entityId, v, location.getYaw(), location.getPitch());
        }

        previousLocation = location.clone();
    }

    public void destroy(){
        destroyed = true;
    }

    public void setYaw(float yaw){
        location.setYaw(yaw);
    }

    public void takeDamage(Entity damager, int damage){
        if(health == 0) return;

        health -= damage;
        location.getWorld().playSound(location, damageSound, 1, 1); // Change to send to viewers only

        if(health < 0){
            health = 0;
        }

        if(health == 0){
            die();
        }
    }

    public void die(){
        alive = false;

        // death animation
    }

    public int getChunkDrawDistance() {
        return chunkDrawDistance;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isChunkloaded() {
        return chunkloaded;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void addViewer(Player viewer){
        if(hasViewer(viewer)) return;
        viewers.add(viewer);

        PacketSender.spawn(viewer, entityId, 64, location);
    }

    public void removeViewer(Player viewer){
        boolean removed = viewers.remove(viewer);

        if(removed){
            PacketSender.destroy(viewer, entityId);
        }
    }

    public void setViewers(List<Player> newViewers){
        Iterator<Player> i = viewers.iterator();
        while(i.hasNext()){
            Player viewer = i.next();
            if(newViewers.contains(viewer)){
                newViewers.remove(viewer);
            }else{
                this.removeViewer(viewer);
            }
        }

        for(Player viewer : newViewers){
            addViewer(viewer);
        }
    }

    public boolean hasViewer(Player viewer){
        return viewers.contains(viewer);
    }
}
