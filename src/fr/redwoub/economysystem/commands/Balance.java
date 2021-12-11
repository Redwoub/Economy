package fr.redwoub.economysystem.commands;

import fr.redwoub.economysystem.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Balance implements CommandExecutor {
    private final Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        YamlConfiguration file = YamlConfiguration.loadConfiguration(main.getFile());

        if(!(sender instanceof Player))
            sender.sendMessage(main.prefix + "§cErreur §8: " + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.not-player")));
        Player player = (Player) sender;

        if(args.length == 0){
            player.sendMessage(main.prefix + "§aVotre solde est de : " + file.getDouble(player.getUniqueId().toString()) + "$");
        }else if(args.length == 1){
            Player target = Bukkit.getPlayer(args[0]);
            if(target == null){
                player.sendMessage(main.prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.player-not-find")));
                return false;
            }

            player.sendMessage("§aLe solde de : §f" + target.getName() + "§a est de : §e" + file.getDouble(target.getUniqueId().toString()));
        }else {
            player.sendMessage(main.prefix + "§cErreur §8: §e/balance (<player>)");
        }
        return false;
    }
}
