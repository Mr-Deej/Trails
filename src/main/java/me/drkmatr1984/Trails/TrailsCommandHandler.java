package me.drkmatr1984.Trails;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class TrailsCommandHandler implements CommandExecutor
{
	private Trails plugin;
	
	public TrailsCommandHandler(Trails plugin){
		this.plugin = plugin;
    }
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		try{
			if (cmd.getName().equalsIgnoreCase("trails") || cmd.getName().equalsIgnoreCase("paths")) {
				if(sender.hasPermission("trails.use")) {
					if(sender instanceof Player) {
						Player player = (Player)sender;
						if ((args.length == 0) || (args.equals(null)))
						{
							if(player.hasPermission("trails.toggle.self")) {
								if(this.plugin.isToggled(player)) {
									this.plugin.setToggled(player, false);
								}else {
									this.plugin.setToggled(player, true);
								}
								//Self-Toggle Message here
								return true;
							}
							//no perms message
							return true;
						}
						if(args.length == 1){
							if(args[0].equalsIgnoreCase("on")){
								//manually turn trails on for the player, by checking if they're in the list, and if not, adding them.
								return true;
							}
							if(args[0].equalsIgnoreCase("off")){
								//manually turn trails off for the player, by checking if they're in the list, and if they are, removing them.
								return true;
							}
							if(args[0].equalsIgnoreCase("toggle")) {
								if(player.hasPermission("trails.toggle.self")) {
									if(this.plugin.isToggled(player)) {
										this.plugin.setToggled(player, false);
									}else {
										this.plugin.setToggled(player, true);
									}
									//Self-Toggle Message here
									return true;
								}
								//no perms message
								return true;
							}
						}
					}
					if(args.length > 0){
						if(args[0].equalsIgnoreCase("reload")) {
							//reload the configs and shiz
							return true;
						}
						if(args[0].equalsIgnoreCase("toggle")) {
							if(args.length==2) {
								if(sender.hasPermission("trails.toggle.others")) {
									if(UUIDFetcher.getUUID(args[1])!=null && Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(args[1])).hasPlayedBefore()) {
										UUID uuid = UUIDFetcher.getUUID(args[1]);
										if(this.plugin.isToggled(uuid)) {
											this.plugin.setToggled(uuid, false);
											// toggled off message
										}else {
											this.plugin.setToggled(uuid, true);
											// toggled on message
										}
										return true;
									}else {
									// Cannot find player with that name
										return true;
									}	
								}
								//no perms message
								return true;
							}
						}
					}
					//unknown command message
					return true;
				}
				//no perms message
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
