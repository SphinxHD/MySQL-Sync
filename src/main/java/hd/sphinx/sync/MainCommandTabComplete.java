package hd.sphinx.sync;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MainCommandTabComplete implements TabCompleter {

    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdlable, String[] args) {
        ArrayList<String> arrayList = new ArrayList<String>();
        if (args.length >= 2) {
            return arrayList;
        }
        arrayList.add("help");
        arrayList.add("version");
        arrayList.add("dev");
        arrayList.add("developer");
        arrayList.add("reload");
        return arrayList;
    }
}
