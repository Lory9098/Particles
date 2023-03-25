package it.flaxdevelopment.devroom.commands;

import it.flaxdevelopment.devroom.Devroom;
import it.flaxdevelopment.devroom.utils.Format;
import it.flaxdevelopment.devroom.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ParticlesCMD extends AbstractCommand {
    FileConfiguration config = Devroom.getInstance().getConfig();
    public ParticlesCMD() {
        super("particles", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(player.hasPermission("particles.use")){
            Inventory inv = Bukkit.createInventory(null, 54, Format.color("&bParticles"));
            int i = 0;
            for(String str : Devroom.getInstance().getConfig().getConfigurationSection("particles").getKeys(false)){
                String name = config.getString("particles." + str + ".name");
                Material item = Material.getMaterial(config.getString("particles." + str + ".item"));

                ItemBuilder itemBuilder = new ItemBuilder(item);
                itemBuilder.setName(name);
                try{
                    inv.setItem(Integer.parseInt(str), itemBuilder.build());
                }catch (NumberFormatException e){
                    System.out.println("Slot non valido");
                }
                i++;
            }
            player.openInventory(inv);
        }
    }
}
