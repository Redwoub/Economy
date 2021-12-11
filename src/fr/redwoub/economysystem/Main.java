package fr.redwoub.economysystem;

import fr.redwoub.economysystem.managers.RegisterManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    private static Main instance;
    public String prefix;
    private File file;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        RegisterManager.register();
        prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.prefix"));
        file = new File(getDataFolder(), "Player Money.yml");

        if(!getDataFolder().exists()) getDataFolder().mkdir();

        if(!file.exists()){
            try {
                file.createNewFile();
                System.out.println("[Economy] File creating...");
            }catch (IOException e){
                e.printStackTrace();
                getServer().getPluginManager().disablePlugin(this);
                System.out.println(prefix + "Plugin disable ! failed to crate data file");
            }
        }
    }

    public File getFile(){
        return file;
    }

    public static Main getInstance() {
        return instance;
    }
}
