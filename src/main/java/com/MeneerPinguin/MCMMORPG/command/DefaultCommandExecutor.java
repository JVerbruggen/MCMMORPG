package com.MeneerPinguin.MCMMORPG.command;

import com.MeneerPinguin.MCMMORPG.entity.VirtualEntity;
import com.MeneerPinguin.MCMMORPG.entity.VirtualEntityManager;
import com.MeneerPinguin.MCMMORPG.entity.behaviour.BaseBehaviour;
import com.MeneerPinguin.MCMMORPG.entity.behaviour.DefaultAggression;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DefaultCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;

            if(s.equalsIgnoreCase("rpg")){
                p.sendMessage("Spawning entity..");

                BaseBehaviour behaviour = new DefaultAggression(10, 10, 10, 1);
                VirtualEntity ve = new VirtualEntity(p.getLocation().clone(), 20, behaviour);

                VirtualEntityManager.spawn(ve);

                p.sendMessage("Spawned entity");
            }
        }

        return false;
    }
}
