package fr.redwoub.economysystem.managers;

import fr.redwoub.economysystem.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.UUID;

public class MoneyManager {

    private static final Main main = Main.getInstance();
    private static YamlConfiguration file = YamlConfiguration.loadConfiguration(main.getFile());

    public static double getMoney(UUID uuid){
        return file.getDouble(uuid.toString());
    }

    public static void setMoney(UUID uuid, double money){;
        file.set(uuid.toString(), money);
        try {
            file.save(main.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetMoney(UUID uuid){
        file.set(uuid.toString(), 0.0);
        Bukkit.getPlayer(uuid).sendMessage(main.prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.succes-reset-money")));
        try {
            file.save(main.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addMoney(UUID uuid, double money){
        double moneyAfter = file.getDouble(uuid.toString());
        Player player = Bukkit.getPlayer(uuid);
        file.set(uuid.toString(), moneyAfter + money);
        player.sendMessage(main.prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.succes-add-money")
                .replace("%s", String.valueOf(money))));
        try {
            file.save(main.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void removeMoney(UUID uuid, double money){
        double moneyAfter = file.getDouble(uuid.toString());
        double moneyE = moneyAfter - money;
        if(moneyE <= 0.0) {
            file.set(uuid.toString(), 0.0);
            return;
        }
        file.set(uuid.toString(), moneyE);

        try {
            file.save(main.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void pay(UUID uuid, UUID target, double money){
        if(getMoney(uuid) < money){
            Bukkit.getPlayer(uuid).sendMessage(main.prefix + "§cErreur §8: " + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.not-have-money")));
            return;
        }
        addMoney(target, money);
        removeMoney(uuid, money);
    }
}
