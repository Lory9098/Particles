package it.flaxdevelopment.devroom.commands;

import it.flaxdevelopment.devroom.Devroom;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public abstract class AbstractCommand implements CommandExecutor {

    String command;
    boolean canConsoleUse;

    public AbstractCommand(String command, boolean canConsoleUse){
        this.command = command;
        this.canConsoleUse = canConsoleUse;

        Devroom.getInstance().getCommand(command).setExecutor(this);
    }

    public abstract void execute(CommandSender sender, String[] args) throws SQLException;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!canConsoleUse && !(sender instanceof Player)) return false;

        try {
            execute(sender, args);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
