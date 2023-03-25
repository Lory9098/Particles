package it.flaxdevelopment.devroom.utils;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    ItemStack itemStack;
    ItemMeta itemMeta;

    public ItemBuilder(Material material){
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public void setName(String name){
        itemMeta.setDisplayName(Format.color(name));
    }

    public void setLore(List<String> lore){
        List<String> loreFormatted = new ArrayList<>();
        for(String str : lore){
            loreFormatted.add(Format.color(str));
        }
        itemMeta.setLore(loreFormatted);
    }

    public void setUnbreakable(boolean unbreakable){
        itemMeta.setUnbreakable(unbreakable);
    }

    public ItemBuilder setSkullOwner(OfflinePlayer owner) {
        SkullMeta skullMeta = (SkullMeta)this.itemStack.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwner(owner.getName());
        }

        this.itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        SkullMeta skullMeta = (SkullMeta)this.itemStack.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwner(owner);
        }

        this.itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemStack build(){
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
