package hd.sphinx.sync;

import hd.sphinx.sync.mysql.MySQL;
import hd.sphinx.sync.util.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.SQLException;

public class MainCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String cmdlable, String[] args) {
        if (ConfigManager.getBoolean("settings.use-permission") && !sender.hasPermission("sync.command")) {
            sender.sendMessage(ConfigManager.getColoredString("messages.lacking-permission").replaceAll("%permission%", "sync.command"));
            return true;
        } else if (args.length != 1) {
            sender.sendMessage(ConfigManager.getColoredString("messages.help"));
            return true;
        } else if (args[0].equalsIgnoreCase("version")) {
            sender.sendMessage(ConfigManager.getColoredString("messages.version"));
            return true;
        } else if (args[0].equalsIgnoreCase("dev") || args[0].equalsIgnoreCase("developer")) {
            sender.sendMessage(ConfigManager.getColoredString("messages.prefix") + " The Developer of this Plugin is §3Sphinx_HD§f.");
            return true;
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("sync.reload")) {
                sender.sendMessage(ConfigManager.getColoredString("messages.lacking-permission").replaceAll("%permission%", "sync.reload"));
                return true;
            }
            ConfigManager.reload();
            MySQL.disconnectMySQL();
            MySQL.connectMySQL();
            try {
                MySQL.registerMySQL();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            sender.sendMessage(ConfigManager.getColoredString("messages.reload"));
            return true;
        }
        sender.sendMessage(ConfigManager.getColoredString("messages.help"));
        return true;
    }
}
