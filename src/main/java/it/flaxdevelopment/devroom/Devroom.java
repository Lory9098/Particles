package it.flaxdevelopment.devroom;

import it.flaxdevelopment.devroom.commands.ParticlesCMD;
import it.flaxdevelopment.devroom.commands.StopCMD;
import it.flaxdevelopment.devroom.data.Database;
import it.flaxdevelopment.devroom.listeners.GeneralListeners;
import it.flaxdevelopment.devroom.listeners.InventoryListeners;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Devroom extends JavaPlugin {

    @Getter
    public static Devroom instance;
    @Getter
    public static Database database;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        String host = getConfig().getString("database.host");
        String db = getConfig().getString("database.database");
        String user = getConfig().getString("database.user");
        String password = getConfig().getString("database.password");
        database = new Database(host, db, user, password);
        try {
            database.connect();
            database.createTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        new ParticlesCMD();
        new StopCMD();
        Bukkit.getPluginManager().registerEvents(new InventoryListeners(), this);
        Bukkit.getPluginManager().registerEvents(new GeneralListeners(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
