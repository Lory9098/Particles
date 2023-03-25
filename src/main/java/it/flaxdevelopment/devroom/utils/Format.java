package it.flaxdevelopment.devroom.utils;

import org.bukkit.ChatColor;

public class Format {

    public static String color(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
