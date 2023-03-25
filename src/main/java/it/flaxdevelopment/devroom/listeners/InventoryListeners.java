package it.flaxdevelopment.devroom.listeners;

import it.flaxdevelopment.devroom.Devroom;
import it.flaxdevelopment.devroom.utils.Format;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InventoryListeners implements Listener {
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final FileConfiguration config = Devroom.getInstance().getConfig();

    @EventHandler
    public void onInteract(InventoryClickEvent e) {

        if(e.getView().getTitle().equalsIgnoreCase(Format.color("&bParticles"))){
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }

            Player player = (Player) e.getWhoClicked();
            int slot = e.getRawSlot();
            String permission = config.getString("particles." + slot + ".permission");

            if (!player.hasPermission(permission)) {
                player.sendMessage(Format.color("&4No permissions"));
                return;
            }

            player.spigot().respawn();
            player.sendMessage(Format.color("&bParticles added!"));

            Particle particle = Particle.valueOf(config.getString("particles." + slot + ".particle"));
            try {
                Devroom.getDatabase().updatePlayer(player, particle);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            if (Devroom.getDatabase().getParticles(player) != null) {
                threadPool.execute(() -> {
                    String particles;
                    while ((particles = Devroom.getDatabase().getParticles(player)) != null) {
                        player.spawnParticle(Particle.valueOf(particles), player.getLocation().subtract(0, -0.1, 0), Devroom.getInstance().getConfig().getInt("particles.quantity"));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        }

    }
}