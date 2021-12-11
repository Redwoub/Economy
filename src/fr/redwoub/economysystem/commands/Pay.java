package fr.redwoub.economysystem.commands;

import fr.redwoub.economysystem.Main;
import fr.redwoub.economysystem.managers.MoneyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Pay implements CommandExecutor {

    private static final Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(main.prefix + "§cErreur §8: " + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.not-player")));
            return false;
        }

        Player player = (Player) sender;

        if(args.length != 2) {
            player.sendMessage(main.prefix + "§cErreur §8: §e/pay <player> <money>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null){
            player.sendMessage(main.prefix + "§cErreur §8: " + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.target-not-online")));
            return false;
        }

        MoneyManager.pay(player.getUniqueId(), target.getUniqueId(), Double.parseDouble(args[1]));
        player.sendMessage(main.prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.succes-pay").replace("%s", args[1])).replace("%p", target.getDisplayName()));
        target.sendMessage(main.prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.succes-recive").replace("%s", args[1])).replace("%p", player.getDisplayName()));
        return false;
    }
}
