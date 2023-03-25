package it.flaxdevelopment.devroom.commands;

import it.flaxdevelopment.devroom.Devroom;
import it.flaxdevelopment.devroom.utils.Format;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class StopCMD extends AbstractCommand {
    public StopCMD() {
        super("stopparticles", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws SQLException {
        Player p = (Player) sender;
        if(p.hasPermission("particles.stop")){
            p.spigot().respawn();
            Devroom.getDatabase().updatePlayer(p, null);
            p.sendMessage(Format.color("&bParticles removed!"));
        }
    }
}
