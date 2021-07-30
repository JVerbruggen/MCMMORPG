package com.MeneerPinguin.MCMMORPG.entity;

import com.MeneerPinguin.MCMMORPG.packets.PacketSender;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VirtualEntityManager {
    protected static List<VirtualEntity> entities = new ArrayList<>();


    public static void tick(){
        Iterator<VirtualEntity> i = entities.iterator();
        while(i.hasNext()){
            VirtualEntity entity = i.next();

            Location location = entity.getLocation();
            Chunk chunk = location.getChunk();

            if(!chunk.isLoaded()){
                if(!entity.isChunkloaded()){
                    entity.destroy();
                    i.remove(); // removes entity from underlying list
                    continue;
                }
            }

            entity.tick();
        }
    }

    public static void spawn(VirtualEntity entity){
        entities.add(entity);
    }

}
