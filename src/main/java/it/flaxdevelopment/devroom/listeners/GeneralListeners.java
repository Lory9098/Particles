package it.flaxdevelopment.devroom.listeners;

import it.flaxdevelopment.devroom.Devroom;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GeneralListeners implements Listener {
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws SQLException {
        Player player = e.getPlayer();
        Devroom.getDatabase().addPlayer(player);

        final String particles = Devroom.getDatabase().getParticles(player);
        if (particles != null) {
            int quantity = Devroom.getInstance().getConfig().getInt("particles.quantity");
            threadPool.execute(() -> {
                String particle = particles;
                while (particle != null) {
                    player.spawnParticle(Particle.valueOf(particle), player.getLocation().subtract(0, -0.1, 0), quantity);
                    particle = Devroom.getDatabase().getParticles(player);
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
