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

    public static int getMoney(UUID uuid){
        return file.getInt(uuid.toString());
    }

    public static void setMoney(UUID uuid, int money){;
        file.set(uuid.toString(), money);
        try {
            file.save(main.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetMoney(UUID uuid){
        file.set(uuid.toString(), 0);
        Bukkit.getPlayer(uuid).sendMessage(main.prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.succes-reset-money")));
        try {
            file.save(main.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addMoney(UUID uuid, int money){
        int moneyAfter = file.getInt(uuid.toString());
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

    public static void removeMoney(UUID uuid, int money){
        int moneyAfter = file.getInt(uuid.toString());
        int moneyE = moneyAfter - money;
        if(moneyE <= 0) {
            file.set(uuid.toString(), 0);
            return;
        }
        file.set(uuid.toString(), moneyE);

        try {
            file.save(main.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void pay(UUID uuid, UUID target, int money){
        if(getMoney(uuid) < money){
            Bukkit.getPlayer(uuid).sendMessage(main.prefix + "§cErreur §8: " + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.not-have-money")));
            return;
        }
        addMoney(target, money);
        removeMoney(uuid, money);
    }
}
