package hd.sphinx.sync;

import hd.sphinx.sync.util.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String cmdlable, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("joinvoid")) return true;
        if (ConfigManager.getBoolean("settings.use-permission") && !sender.hasPermission("sync.command")) {
            sender.sendMessage(ConfigManager.getColoredString("messages.lacking-permission").replaceAll("%permission%", "sync.command"));
            return true;
        } else if (args.length != 1) {
            sender.sendMessage(ConfigManager.getColoredString("messages.help"));
            return true;
        } else if (args[0].equalsIgnoreCase("version")) {
            sender.sendMessage(ConfigManager.getColoredString("messages.version").replaceAll("%version%", ConfigManager.getString("version")));
            return true;
        } else if (args[0].equalsIgnoreCase("dev") || args[0].equalsIgnoreCase("developer")) {
            sender.sendMessage(ConfigManager.getColoredString("messages.developerFront") + "§3Sphinx_HD" + ConfigManager.getColoredString("messages.developerBack"));
            return true;
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("sync.reload")) {
                sender.sendMessage(ConfigManager.getColoredString("messages.lacking-permission").replaceAll("%permission%", "sync.reload"));
                return true;
            }
            ConfigManager.reload();
            MainManageData.reload();
            sender.sendMessage(ConfigManager.getColoredString("messages.reload"));
            return true;
        }
        sender.sendMessage(ConfigManager.getColoredString("messages.help"));
        return true;
    }
}
