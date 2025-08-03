package org.sattdev.mukscoreboardv2;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class MukScoreboard_v2 extends JavaPlugin implements Listener {

    public static MukScoreboard_v2 plugin;
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        plugin = this;
        config = getConfig();

        // Config default da scoreboard
        List<String> linhas = new ArrayList<>();
        linhas.add("");
        linhas.add("&dPlayer: %player_name%");
        linhas.add("");
        linhas.add("&dPing : %ping%");
        linhas.add("");
        linhas.add("&dPlugin Author: MukPlugins");
        linhas.add("");
        linhas.add("&dmukplugins.com");

        config.addDefault("Scoreboard.title", "&5%lMukPlugins");
        config.addDefault("Scoreboard.linhas", linhas);
        config.options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(this, this);

        // Atualiza a Score
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Scoreboard.updateScoreboard(player);
            }
        }, 0L, 20L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
