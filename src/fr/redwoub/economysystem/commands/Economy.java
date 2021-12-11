package fr.redwoub.economysystem.commands;

import fr.redwoub.economysystem.Main;
import fr.redwoub.economysystem.managers.MoneyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.nio.charset.StandardCharsets;

public class Economy implements CommandExecutor {

    private final static Main main = Main.getInstance();

    private static String sendHelpMessage(Player player){
            player.sendMessage(main.prefix + "§cErreur §8: §cInvalid arguments !\n §e/eco add <player | @a> <money>) \n §e/eco remove <player | @a> <money | all> \n §e/eco reset <player | @a>");
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(main.prefix + "§cErreur §8: " + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.not-player")));
            return false;
        }

        Player player = (Player) sender;
        if(args.length < 1){
            player.sendMessage(sendHelpMessage(player));
            return false;
        }

        switch (args[0]){
            case "remove":
                if(args.length < 3){
                    player.sendMessage(sendHelpMessage(player));
                    return false;
                }

                if(args[2].equalsIgnoreCase("all")){
                    if(args[1].equalsIgnoreCase("@a")){
                        for(Player players : Bukkit.getOnlinePlayers()){
                            MoneyManager.resetMoney(players.getUniqueId());
                        }
                        player.sendMessage(main.prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messaages.succes-remove-money-sender")
                                .replace("%s", "toute la money")
                                .replace("%p", "tout le serveur")));
                    }else if(Bukkit.getPlayer(args[1]) == null){
                        player.sendMessage(main.prefix + "§cErreur §8: " + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.player-not-find")));
                        return false;
                    }else {
                        MoneyManager.resetMoney(Bukkit.getPlayer(args[1]).getUniqueId());
                        player.sendMessage(main.prefix + "§aSucces !");
                    }
                }else {
                    if(args[1].equalsIgnoreCase("@a")){
                        for(Player players : Bukkit.getOnlinePlayers()){
                            MoneyManager.removeMoney(players.getUniqueId(), Integer.parseInt(args[2]));
                            players.sendMessage(main.prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.succes-remove-money")
                                    .replace("%p", player.getDisplayName())
                                    .replace("%s", args[2])));
                        }
                        player.sendMessage(main.prefix + "§aSucess !");
                    } else if(Bukkit.getPlayer(args[1])  == null){
                        player.sendMessage(main.prefix + "§cErreur §8: " + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.player-not-find")));
                        return false;
                    } else {
                        MoneyManager.removeMoney(Bukkit.getPlayer(args[1]).getUniqueId(), Integer.parseInt(args[2]));
                        player.sendMessage(main.prefix + "§aSucess !");
                        Bukkit.getPlayer(args[1]).sendMessage(main.prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.succes-remove-player")
                                .replace("%p", player.getDisplayName())
                                .replace("%s", args[2])));
                    }
                }
                break;
            case "add":
                if(args.length != 3){
                    player.sendMessage(sendHelpMessage(player));
                    return false;
                }

                if(args[1].equalsIgnoreCase("@a")){
                    for(Player players : Bukkit.getOnlinePlayers()){
                        MoneyManager.addMoney(players.getUniqueId(), Double.parseDouble(args[2]));
                    }
                }else if(Bukkit.getPlayer(args[1]) == null){
                    player.sendMessage(main.prefix + "§cErreur §8: " + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.player-not-find")));
                    return false;
                }else {
                    MoneyManager.addMoney(Bukkit.getPlayer(args[1]).getUniqueId(), Double.parseDouble(args[2]));
                    //Bukkit.getPlayer(args[1]).sendMessage(main.prefix + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("succes-remove-money").replace("%p", player.getDisplayName()).replace("%s", args[2])));
                }
                break;
            case "reset":
                if(args.length != 2){
                    player.sendMessage(sendHelpMessage(player));
                    return false;
                }

                if(args[1].equalsIgnoreCase("@a")){
                    for(Player players : Bukkit.getOnlinePlayers()){
                        MoneyManager.resetMoney(players.getUniqueId());
                    }
                }else if(Bukkit.getPlayer(args[1])  == null){
                    player.sendMessage(main.prefix + "§cErreur §8: " + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.player-not-find")));
                    return false;
                }else {
                    MoneyManager.resetMoney(Bukkit.getPlayer(args[1]).getUniqueId());
                }
                break;
            default: break;
        }
        return false;
    }
}
