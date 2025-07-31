package org.sattdev.mukscoreboardv2;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public final class MukScoreboard_v2 extends JavaPlugin implements Listener {

    public static MukScoreboard_v2 plugin;
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        plugin = this;
        config = getConfig();

        // Set default config
        List<String> linhas = new ArrayList<>();
        linhas.add("");
        linhas.add("&emukScoreboard-v2");
        linhas.add("");
        linhas.add("&7Version: 1.0");
        linhas.add("");
        linhas.add("&aAuthor: MukPlugins");
        linhas.add("");
        linhas.add("&bPlayer: %player_name%");

        config.addDefault("Scoreboard.title", "&6MukPlugins");
        config.addDefault("Scoreboard.linhas", linhas);
        config.options().copyDefaults(true);
        saveConfig();


        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = sb.registerNewObjective("Scoreboard", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("Scoreboard.title")));

        List<String> linhas = config.getStringList("Scoreboard.linhas");
        int score = 15;

        for (String linha : linhas) {
            String formatted = ChatColor.translateAlternateColorCodes('&', linha)
                    .replace("%player_name%", player.getName());


            if (formatted.trim().isEmpty()) {
                formatted = ChatColor.values()[score % ChatColor.values().length] + "" + ChatColor.RESET;
            }

            if (formatted.length() > 40) {
                formatted = formatted.substring(0, 40);
            }

            obj.getScore(formatted).setScore(score--);
        }

        player.setScoreboard(sb);
    }
}
