package org.sattdev.mukscoreboardv2;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.List;

public class Scoreboard implements Listener {

    // Atualiza a scoreboard para quando um player entrar
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        updateScoreboard(player);
    }

    // faz a score funcionar, e aplica no player
    public static void updateScoreboard(Player player) {
        org.bukkit.scoreboard.Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = sb.registerNewObjective("Scoreboard", "dummy");
        obj.setDisplaySlot( DisplaySlot.SIDEBAR);
        obj.setDisplayName( ChatColor.translateAlternateColorCodes('&', MukScoreboard_v2.config.getString("Scoreboard.title")));

        List<String> linhas = MukScoreboard_v2.config.getStringList("Scoreboard.linhas");
        int score = 15;

        for (String linha : linhas) {
            String formatted = ChatColor.translateAlternateColorCodes('&', linha)
                    .replace("%player_name%", player.getName());

            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                formatted = PlaceholderAPI.setPlaceholders(player, formatted);
            }

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
