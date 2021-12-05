package fr.redwoub.economysystem.managers;

import fr.redwoub.economysystem.Main;
import fr.redwoub.economysystem.commands.Balance;
import fr.redwoub.economysystem.commands.Economy;
import fr.redwoub.economysystem.commands.Pay;
import fr.redwoub.economysystem.listeners.PlayerJoin;
import org.bukkit.plugin.PluginManager;

public class RegisterManager {

    private static final Main main = Main.getInstance();
    private static final PluginManager pm = main.getServer().getPluginManager();

    public static void register(){
        main.getCommand("balance").setExecutor(new Balance());
        main.getCommand("pay").setExecutor(new Pay());
        main.getCommand("eco").setExecutor(new Economy());

        pm.registerEvents(new PlayerJoin(), main);
    }
}
