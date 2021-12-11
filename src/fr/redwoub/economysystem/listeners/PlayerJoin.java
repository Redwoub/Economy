package fr.redwoub.economysystem.listeners;

import fr.redwoub.economysystem.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

public class PlayerJoin implements Listener {

    private static final Main main = Main.getInstance();

    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        YamlConfiguration file = YamlConfiguration.loadConfiguration(main.getFile());

        if(file.getString(player.getUniqueId().toString()) == null){
            file.set(player.getUniqueId().toString(), 0.0);
            try {
                file.save(main.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
