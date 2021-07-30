package com.MeneerPinguin.MCMMORPG.packets;

import com.MeneerPinguin.MCMMORPG.Main;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

public class PacketSender {

    /**
     * Send a move look packet to a list of viewers
     * @param viewers
     * @param entityId
     * @param v
     * @param yaw
     * @param pitch
     */
    public static void moveLook(List<Player> viewers, int entityId, Vector v, float yaw, float pitch){
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.REL_ENTITY_MOVE_LOOK);
        packet.getIntegers()
                .write(0, entityId);
        packet.getShorts()
                .write(0, (short)((v.getX() * 32) * 128))
                .write(1, (short)((v.getY() * 32) * 128))
                .write(2, (short)((v.getZ() * 32) * 128));
        packet.getBytes()
                .write(0, (byte)(yaw))
                .write(1, (byte)(pitch));
        packet.getBooleans()
                .write(0, true);

        sendPacket(viewers, packet);
    }

    /**
     * Send a entity spawn packet to a viewer
     * @param viewer
     * @param entityId
     * @param entityType
     * @param location
     */
    public static void spawn(Player viewer, int entityId, int entityType, Location location){
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
        packet.getIntegers()
                .write(0, entityId)
                .write(1, entityType);
        packet.getDoubles()
                .write(0, location.getX())
                .write(1, location.getY())
                .write(2, location.getZ());
        packet.getBytes()
                .write(0, (byte)(location.getYaw()));
        packet.getUUIDs()
                .write(0, UUID.randomUUID());

        sendPacket(viewer, packet);
    }

    /**
     * Send a destroy entity packet to a viewer
     * @param viewer
     * @param entityId
     */
    public static void destroy(Player viewer, int entityId){
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        packet.getIntegerArrays()
                .write(0, new int[]{entityId});

        sendPacket(viewer, packet);
    }

    private static void sendPacket(List<Player> viewers, PacketContainer packet){
        ProtocolManager pm = Main.getInstance().getProtocolManager();
        try{
            for(Player viewer : viewers){
                sendPacket(viewer, packet, pm);
            }
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }
    }

    private static void sendPacket(Player viewer, PacketContainer packet){
        ProtocolManager pm = Main.getInstance().getProtocolManager();
        try{
            sendPacket(viewer, packet, pm);
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }
    }

    private static void sendPacket(Player viewer, PacketContainer packet, ProtocolManager pm) throws InvocationTargetException {
        pm.sendServerPacket(viewer, packet);
    }

}
